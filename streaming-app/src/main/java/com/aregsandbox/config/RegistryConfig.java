package com.aregsandbox.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.ConfigMapping.NamingStrategy;

//@ConfigMapping(prefix = "aregsandbox.registry", namingStrategy = NamingStrategy.KEBAB_CASE)
public interface RegistryConfig {
    String url();
    String group();  
    String artifact();
    String version();  
}
