package org.budgie.hubble.server.core.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.budgie.hubble.server.configurations.ApplicationProperties;
import org.budgie.hubble.server.configurations.beans.Properties;
import org.budgie.hubble.server.context.beans.InstanceProperties;
import org.budgie.hubble.server.core.constants.HubbleConstant;
import org.budgie.hubble.server.core.eventbus.HubbleEventBus;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class HubbleServer extends AbstractVerticle {

    private static final Logger log = LogManager.getLogger();
    private LocalMap<String, JsonArray> instancesMap;
    private final JsonArray jsonArrayRequest = new JsonArray();
    Properties propertiesFile = null;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        new ApplicationProperties().configFile(properties -> this.propertiesFile = properties );
        Router router = Router.router(vertx);
        // Usado para mapear los recursos estaticos como son: .js .css .html
        router.route("/static/*").handler(StaticHandler.create().setCachingEnabled(false));
        // Creamos un mapa local para guardar los servicios que se van ir registrando en el servidor
        instancesMap = vertx.sharedData().getLocalMap("instancesMap");
        // manejador de la peticion HTTP de l pagina
        router.get("/").handler(this::handleShowingPage);
        // Manejador del body de la peticion
        router.route().handler(BodyHandler.create());
        // manejadores de la peticion HTTP GET para mostrar las configruaciones del archivo
        router.get(HubbleConstant.CONFIGURATION_PATH).handler(this::handleConfigurations);
        // manejadores de la peticion HTTP GET, POST, DELETE y poder consultar, guardar y eliminar microservicios
        router.get(HubbleConstant.REGISTER_PATH).handler(this::handleGetRequest);
        router.post(HubbleConstant.REGISTER_PATH).handler(this::handlePostRequest);
        router.delete(HubbleConstant.REGISTER_PATH).handler(this::handleDeleteRequest);
        // manejador de mensajes que entran y salen en el eventbus
        router.route(HubbleConstant.REGISTER_INPUT_OUTPUT_PATH).handler(configEventBusBridge());
        // Construimos el servidor HTTP que escucha por default en el puerto 7001
        int PORT = getPort(propertiesFile);
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(PORT, listenHandler -> {
                    if(listenHandler.succeeded()) {
                        log.info("---------------------------------------------------");
                        log.info(" Hubble Server is running on port {}", PORT);
                        log.info("---------------------------------------------------");
                        startFuture.complete();
                        vertx.deployVerticle(new HubbleEventBus());
                    }else {
                        log.error("+++++++++++++++++++++++++++++++++++++++++++++++++++");
                        log.error(" Hubble Server was not run on port {}", PORT);
                        log.error("+++++++++++++++++++++++++++++++++++++++++++++++++++");
                        startFuture.fail(listenHandler.cause());
                    }
                });
    }

    /**
     * Metodo que es usado por el handler para manipular la informacion de la peticion
     * @param routingContext
     */
    private void handleShowingPage(final RoutingContext routingContext) {
        routingContext.response().putHeader("content-type", "text/html").sendFile("console.html");
    }

    /**
     * Metodo que es usado por el handler para manipular la informacion de la peticion GET
     * @param routingContext
     */
    private void handleConfigurations(final RoutingContext routingContext) {
        routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(propertiesFile));
    }

    /**
     * Metodo que es usado por el handler para manipular la informacion de la peticion GET
     * @param routingContext
     */
    private void handleGetRequest(final RoutingContext routingContext) {
        routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(new HubbleEventBus().build(instancesMap.get(HubbleConstant.REQUEST_OBJ))));
    }

    /**
     * Metodo que es usado por el handler para manipular la informacion de la peticion POST
     * @param routingContext
     */
    private void handlePostRequest(final RoutingContext routingContext) {
        InstanceProperties instanceProperties;
        try {
            instanceProperties = new ObjectMapper().readValue(routingContext.getBodyAsString(), InstanceProperties.class);
            jsonArrayRequest.add(JsonObject.mapFrom(instanceProperties));
            instancesMap.put(HubbleConstant.REQUEST_OBJ, jsonArrayRequest);
            handleEvents(instancesMap.get(HubbleConstant.REQUEST_OBJ));
            routingContext.response().end();
        } catch (IOException e) {
            log.error("Error occurred in read instance {0}", e);
        }
    }

    /**
     * Metodo que es usado por el handler para manipular la informacion de la peticion DELETE
     * @param routingContext
     */
    private void handleDeleteRequest(final RoutingContext routingContext) {
        InstanceProperties instanceProperties;
        try {
            instanceProperties = new ObjectMapper().readValue(routingContext.getBodyAsString(), InstanceProperties.class);
            Optional<Object> instanceSource = jsonArrayRequest.stream().filter(predicate -> ((JsonObject)predicate).getInteger("port").equals(instanceProperties.getPort())).findFirst();
            instanceSource.ifPresent(o -> jsonArrayRequest.remove((JsonObject) o));
            instancesMap.put(HubbleConstant.REQUEST_OBJ, jsonArrayRequest);
            handleEvents(instancesMap.get(HubbleConstant.REQUEST_OBJ));
            routingContext.response().end();
        } catch (IOException e) {
            log.error("Error occurred in read instance {0}", e);
        }
    }

    /**
     * Metodo para enviar flujos de datos al event bus
     * @param routingContext
     * @param requestInfo
     */
    private void handleEvents(final JsonArray requestInfo) {
        try {
            vertx.eventBus().send(HubbleConstant.EB_SERVICE_DISCOVERY, requestInfo);
        }catch(Exception ex) {
            log.error("Error occurred in handle event {0}", ex);
        }
    }

    /**
     * EventBus configuration
     * @return
     */
    private SockJSHandler configEventBusBridge() {
        return SockJSHandler.create(vertx)
                .bridge(new BridgeOptions()
                                //Add option for actual incoming
                                .addInboundPermitted(new PermittedOptions().setAddress(HubbleConstant.EB_SERVICE_DISCOVERY_START))
                                //Add option for actual outgoing
                                .addOutboundPermitted(new PermittedOptions().setAddress(HubbleConstant.EB_SERVICE_DISCOVERY_CONNECT))
                        , bridgeEvent -> {
                            if (bridgeEvent.type() == BridgeEventType.SOCKET_CREATED) {
                                log.debug("Bridge event is {}", bridgeEvent.type());
                            }else {
                                log.debug("Service Discovery Server is alive");
                            }
                            bridgeEvent.complete(true);
                        });
    }

    private int getPort(final Properties configuration){
        if(configuration == null || configuration.getDiscovery() == null || configuration.getDiscovery().getServer() == null || configuration.getDiscovery().getServer().getPort() < 0){
            return HubbleConstant.DEFAULT_PORT;
        }
        return configuration.getDiscovery().getServer().getPort();
    }

}
