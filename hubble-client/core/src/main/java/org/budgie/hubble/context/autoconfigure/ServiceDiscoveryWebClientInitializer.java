package org.budgie.hubble.context.autoconfigure;


import org.budgie.hubble.context.handlers.IHandlerType;
import org.budgie.hubble.context.registry.RegistryLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
@HandlesTypes({IHandlerType.class})
public class ServiceDiscoveryWebClientInitializer implements ServletContainerInitializer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onStartup(final Set<Class<?>> handlerTypeSet, final ServletContext servletContext) throws ServletException {
        log.info("Starting service discovery client");
        callImplementationHandlerType(handlerTypeSet, servletContext);
    }

    private void callImplementationHandlerType(final Set<Class<?>> handlerTypeSet, final ServletContext servletContext) {
        log.debug("Validating if handler type is present");
        if (handlerTypeSet != null) {
            handlerTypeSet.forEach(aClass -> {
                log.info("implementing handler instance for {}", aClass);
                executeImplementationIHandlerTypeInstance(servletContext);
            });
        }
    }

    private void executeImplementationIHandlerTypeInstance(final ServletContext servletContext) {
        RegistryLayer.context(servletContext).registerService(-1);
    }

}
