package org.budgie.hubble.server.configurations.beans;

import java.io.Serializable;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class Discovery implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Server server;

    public Discovery() {
    }

    public Discovery(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

}
