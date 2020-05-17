package org.budgie.hubble.server.context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.budgie.hubble.server.annotations.HubbleServer;


/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class HubbleServerInitializer {
    private static final Logger log = LogManager.getLogger();

    private HubbleServerInitializer() { }

    public static void run(final Class<?> clazz, Object object){
        if(!clazz.isAnnotationPresent(HubbleServer.class)){
            log.warn("{} annotation is not present", HubbleServer.class.getName());
            return;
        }
        HubbleRunContext.run(object);
    }
}
