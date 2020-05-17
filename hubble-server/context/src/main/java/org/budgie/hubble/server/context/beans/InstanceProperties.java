package org.budgie.hubble.server.context.beans;

import java.io.Serializable;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class InstanceProperties implements Serializable {

    private static final long serialVersionUID = 1L;
    private String schema;
    private String ip;
    private int port;
    private String service;
    private String url;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }
}
