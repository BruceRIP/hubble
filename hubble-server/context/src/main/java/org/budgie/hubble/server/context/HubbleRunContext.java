package org.budgie.hubble.server.context;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class HubbleRunContext {

    private HubbleRunContext(){ }
    public static void run(Object object){
        Vertx.vertx().deployVerticle((Verticle) object);
    }
}
