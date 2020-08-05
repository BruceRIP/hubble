package org.budgie.hubble.properties;

import org.budgie.hubble.properties.vo.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class ApplicationProperties {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String APPLICATION_PROPERTIES = "application.yml";

    /**
     * A lambda expression to read file
     * @param consume
     */
    public void configFile(final Consumer<Properties> consume){
        Optional<Properties> properties = readApplicationFile(this.getClass().getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES));
        consume.accept(properties.orElse(null));
    }

    /**
     * Read a configuration file and assign to a configuration object
     * @param inputStream
     * @return
     */
    private Optional<Properties> readApplicationFile(final InputStream inputStream) {
        log.debug("Reading application properties file");
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(representer);
        return Optional.ofNullable(yaml.loadAs(inputStream, Properties.class));
    }

}
