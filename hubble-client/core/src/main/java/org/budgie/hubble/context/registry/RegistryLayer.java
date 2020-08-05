package org.budgie.hubble.context.registry;


import org.apache.commons.lang3.StringUtils;
import org.budgie.hubble.context.container.ServletContainerContext;
import org.budgie.hubble.context.exceptions.RegistryException;
import org.budgie.hubble.context.exceptions.ResponseException;
import org.budgie.hubble.context.rest.RestInvoker;
import org.budgie.hubble.context.handlers.HealthServletHandlerType;
import org.budgie.hubble.properties.ApplicationProperties;
import org.budgie.hubble.properties.InstanceProperties;
import org.budgie.hubble.properties.vo.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class RegistryLayer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static RegistryLayer registryLayer;
    private static ServletContext servletContext;
    private int serverPort;

    private RegistryLayer(){}

    public static RegistryLayer context(final ServletContext context){
        if(registryLayer == null){
            registryLayer = new RegistryLayer();
        }
        servletContext = context;
        return registryLayer;
    }

    public void registerService(final int port) {
        this.serverPort = port;
        callRest(getContextPath(), "POST");
    }

    public void unregisterService() {
        callRest(getContextPath(), "DELETE");
    }

    private String getContextPath(){
        String contextPath = "";
        try{
            contextPath = servletContext.getContextPath();
        } catch (Throwable throwable) {
            log.warn("an error occurred while executing process {0}", throwable);
        }
        return contextPath;
    }

    private void callRest(final String contextPath, final String method){
        try {
            log.debug(" communicating with server discovery ");
            ServletContainerContext servletContainerContext = new ServletContainerContext();
            final String address = servletContainerContext.getIpAddress();
            final String port = servletContainerContext.getPort();
            new ApplicationProperties().configFile(properties -> {
               try {
                   new RestInvoker().register(properties.getDiscovery().getConnection(), method, buildInstance(address, StringUtils.isBlank(port) ? String.valueOf(serverPort) : port, properties, contextPath));
                   new HealthServletHandlerType().execute(servletContext);
               } catch (RegistryException | ResponseException e) {
                   log.warn(" an error occurred while register service {0}", e);
               }
            });
        } catch (IOException e) {
            log.warn("an error occurred while executing process {0}", e);
        }
    }

    private InstanceProperties buildInstance(final String address, final String port, final Properties configuration, final String contextPath) {
        InstanceProperties instanceProperties = new InstanceProperties();
        instanceProperties.setInstanceName(configuration.getDiscovery().getService().getName());
        instanceProperties.setUri(String.format("http://%s:%s/%s", address, port, contextPath));
        instanceProperties.setSchema("http");
        instanceProperties.setHost(address);
        instanceProperties.setPort(Integer.parseInt(port));
        instanceProperties.setPath(String.format("%shealth", contextPath.length() > 1 ? (contextPath.substring(1) + "/") : ""));
        return instanceProperties;
    }

}
