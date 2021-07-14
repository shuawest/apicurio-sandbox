package com.aregsandbox.mgmt;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.aregsandbox.mgmt.config.HawtioConfig;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.hawt.embedded.Main;

/**
 * Hawtio embedded
 * https://hawt.io/docs/configuration/
 */
@Singleton
public class HawtioService extends EndpointRouteBuilder {
    private static final transient Logger log = LoggerFactory.getLogger(HawtioService.class);
      
    @Inject
    HawtioConfig hawtioConfig;

    @Override
    public void configure() throws Exception {   

        if(hawtioConfig.isEnabled()) {
            log.info("Starting Hawtio on port %s (auth enabled: %s)", hawtioConfig.getPort(), hawtioConfig.isAuthEnabled().toString());

            System.setProperty("hawtio.authenticationEnabled", hawtioConfig.isAuthEnabled().toString());

            Main hawtio = new Main();
            hawtio.setWar(hawtioConfig.getWarLocation());
            hawtio.setPort(hawtioConfig.getPort());
            hawtio.run();
        } else {
            log.info("Hawtio is not enabled");
        }
    }
       
}