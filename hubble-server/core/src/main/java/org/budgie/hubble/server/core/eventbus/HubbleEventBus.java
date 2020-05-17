package org.budgie.hubble.server.core.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.budgie.hubble.server.core.constants.HubbleConstant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class HubbleEventBus extends AbstractVerticle {

    private static final Logger log = LogManager.getLogger();

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer(HubbleConstant.EB_SERVICE_DISCOVERY, message -> {
            Map<String, JsonArray> services = new LinkedHashMap<>();
            if (message != null && message.body() != null) {
                services = build(new JsonArray(message.body().toString()));
            }
            vertx.eventBus().publish(HubbleConstant.EB_SERVICE_DISCOVERY_CONNECT, JsonObject.mapFrom(services));
            message.reply(JsonObject.mapFrom(services));
        });
    }

    public Map<String, JsonArray> build(final JsonArray jsonArray) {
        Map<String, JsonArray> services = new LinkedHashMap<>();
        if (jsonArray != null) {
            jsonArray.forEach(source -> {
                JsonObject json = (JsonObject) source;
                log.info("Registered instance {}://{}:{} [{}/{}:{}] with status UP (replication={})", json.getString("schema"), json.getString("ip"), json.getInteger("port"), json.getString("service").toUpperCase(), json.getString("service").toLowerCase(), json.getInteger("port"), false);
                if (!services.containsKey(json.getString("service"))) {
                    services.put(json.getString("service"), new JsonArray());
                }
                services.get(json.getString("service")).add(json);
            });
        }
        return services;
    }
}
