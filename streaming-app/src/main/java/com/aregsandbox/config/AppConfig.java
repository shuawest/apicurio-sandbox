package com.aregsandbox.config;

import java.util.List;

import com.aregsandbox.config.DestinationConfig;

import io.quarkus.arc.config.ConfigProperties;
import io.quarkus.arc.config.ConfigProperties.NamingStrategy;

@ConfigProperties(prefix = "aregsandbox.app", namingStrategy = NamingStrategy.KEBAB_CASE)
public class AppConfig {
    private String name;
    private DestinationConfig destination;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public DestinationConfig getDestination() {
        return destination;
    }
    public void setDestination(DestinationConfig destination) {
        this.destination = destination;
    }
    

}
