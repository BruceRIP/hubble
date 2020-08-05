package org.budgie.hubble.autoconfigure;

import org.budgie.hubble.annotations.HubbleClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceDiscoveryClientRun {

    @ConditionalOnClass(HubbleClient.class)
    static class EmbeddedConfiguration{

        @Bean
        public ServiceDiscoveryClientInitializer serviceDiscoveryClientInitializer(){
            return new ServiceDiscoveryClientInitializer();
        }
    }

}
