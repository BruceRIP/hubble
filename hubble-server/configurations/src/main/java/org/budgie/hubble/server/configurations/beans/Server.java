package org.budgie.hubble.server.configurations.beans;

import java.io.Serializable;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class Server implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int port;

    public Server() {
    }

    public Server(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
