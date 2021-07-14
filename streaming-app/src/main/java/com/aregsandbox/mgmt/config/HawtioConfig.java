package com.aregsandbox.mgmt.config;

import java.util.List;

import io.quarkus.arc.config.ConfigProperties;
import io.quarkus.arc.config.ConfigProperties.NamingStrategy;

@ConfigProperties(prefix = "aregsandbox.hawtio", namingStrategy = NamingStrategy.KEBAB_CASE)
public class HawtioConfig {
    private Boolean enabled = false;
    private Boolean authEnabled = true;
    private String warLocation;
    private Integer port;

    public Boolean isAuthEnabled() {
        return authEnabled;
    }
    public void setAuthEnabled(Boolean authEnabled) {
        this.authEnabled = authEnabled;
    }
    public String getWarLocation() {
        return warLocation;
    }
    public void setWarLocation(String warLocation) {
        this.warLocation = warLocation;
    }
    public Boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
    
}
