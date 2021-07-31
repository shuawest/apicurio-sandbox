package com.aregsandbox.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.ConfigMapping.NamingStrategy;

//@ConfigMapping(prefix = "aregsandbox.app.destination", namingStrategy = NamingStrategy.KEBAB_CASE)
public interface DestinationConfig {
    String destination();
    String topicName();    
}
