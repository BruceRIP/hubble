package org.budgie.hubble.context.servlets;

import org.budgie.hubble.context.registry.RegistryLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class HealthCheckServlet extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final PrintWriter printWriter = resp.getWriter();
        printWriter.println("<html>");
        printWriter.println("<body>");
        printWriter.println("<h3> You are in the Health Check Servlet</h3>");
        printWriter.println("</body>");
        printWriter.println("</html>");
    }

    @Override
    public void destroy() {
        log.info("unregistering service instance {}", this.getServletContext().getContextPath());
        RegistryLayer.context(this.getServletContext()).unregisterService();
    }
}
