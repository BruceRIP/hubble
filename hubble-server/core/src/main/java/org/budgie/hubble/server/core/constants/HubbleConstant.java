package org.budgie.hubble.server.core.constants;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class HubbleConstant {
    private HubbleConstant(){}
    public static final String EB_SERVICE_DISCOVERY = "eb.service.discovery.registry";
    public static final String EB_SERVICE_DISCOVERY_START = "eb.service.discovery.start";
    public static final String EB_SERVICE_DISCOVERY_CONNECT = "eb.service.discovery.connect";

    public static final String REQUEST_OBJ = "request";

    public static final String CONFIGURATION_PATH = "/configuration";
    public static final String REGISTER_PATH = "/register";
    public static final String REGISTER_INPUT_OUTPUT_PATH = "/registry/*";

    public static final int DEFAULT_PORT = 7001;
}
