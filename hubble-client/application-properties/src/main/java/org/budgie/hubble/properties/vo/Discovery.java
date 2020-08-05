package org.budgie.hubble.properties.vo;

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
    private Connection connection;
    private Service service;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
