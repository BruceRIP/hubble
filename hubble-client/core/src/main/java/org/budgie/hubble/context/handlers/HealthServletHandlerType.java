package org.budgie.hubble.context.handlers;

import org.budgie.hubble.context.servlets.HealthCheckServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class HealthServletHandlerType implements IHandlerType {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(ServletContext servletContext) {
        log.debug("registering health check service");
        final ServletRegistration.Dynamic registration = servletContext.addServlet("health", HealthCheckServlet.class);
        registration.setLoadOnStartup(1);
        registration.addMapping("/health");
        registration.setAsyncSupported(true);
    }
}
