package org.budgie.hubble.context.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class ServletContainerContext {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public String getIpAddress() throws UnknownHostException {
        String address = InetAddress.getLocalHost().getHostAddress();
        log.debug("Server is in {}" , address);
        return address;
    }

    public String getPort()  {
        String port  = null;
        try {
            MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
            Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
            port = objectNames.iterator().next().getKeyProperty("port");
            log.debug("Server is up on {} port ", port);
        }catch(NoSuchElementException | MalformedObjectNameException ex){
            log.warn("cannot get port from server, service was registered on port {}", port);
        }
        return port;
    }
}
