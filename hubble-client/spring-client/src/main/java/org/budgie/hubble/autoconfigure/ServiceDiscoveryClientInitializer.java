package org.budgie.hubble.autoconfigure;


import org.budgie.hubble.context.registry.RegistryLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class ServiceDiscoveryClientInitializer implements ServletContextInitializer{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${server.port}")
    private int port;
    private int serverPort;

    @PostConstruct
    public void postConstruct(){
        this.serverPort = this.port;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        log.info("Starting service discovery client");
        executeImplementation(servletContext);
    }

    private void executeImplementation(final ServletContext servletContext) {
        RegistryLayer.context(servletContext).registerService(this.serverPort);
    }

}
