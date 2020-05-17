package org.budgie.hubble.server.core;

import org.budgie.hubble.server.context.HubbleServerInitializer;
import org.budgie.hubble.server.core.server.HubbleServer;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class HubbleApplication {

    public static void run(final Class clazz) {
        HubbleServerInitializer.run(clazz, new HubbleServer());
    }
}
