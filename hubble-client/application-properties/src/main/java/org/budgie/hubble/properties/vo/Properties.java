package org.budgie.hubble.properties.vo;

import java.io.Serializable;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class Properties implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Discovery discovery;

    public Discovery getDiscovery() {
        return discovery;
    }

    public void setDiscovery(Discovery discovery) {
        this.discovery = discovery;
    }
}
