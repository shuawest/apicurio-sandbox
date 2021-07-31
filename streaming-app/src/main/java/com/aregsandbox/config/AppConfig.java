package com.aregsandbox.config;

import java.util.List;

import com.aregsandbox.config.DestinationConfig;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.ConfigMapping.NamingStrategy;

@ConfigMapping(prefix = "aregsandbox.app", namingStrategy = NamingStrategy.KEBAB_CASE)
public interface AppConfig {
    String name();
    DestinationConfig destination();
    RegistryConfig registry();
}
