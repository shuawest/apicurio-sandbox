package com.aregsandbox.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.aregsandbox.config.AppConfig;

import org.apache.camel.Exchange;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.apicurio.registry.serde.SerdeConfig;
import io.apicurio.registry.serde.protobuf.ProtobufKafkaDeserializer;

@ApplicationScoped
public class ConsumerService extends EndpointRouteBuilder {
    private static final transient Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @Inject
    AppConfig config;

    @Override
    public void configure() throws Exception {
        
        boolean value = true;
        if(value) return;  
        
        String ktopicA = KafkaUriBuilder.create(ProducerService.TOPIC_A)
            .appendProperty("brokers", "{{aregsandbox.kafka.brokers}}")
            .appendProperty("valueDeserializer", ProtobufKafkaDeserializer.class.getName())
            .appendProperty("maxRequestSize", "5242880")
            .appendAdditional(SerdeConfig.REGISTRY_URL, "{{registryurl}}")
            //.appendAdditional(SerdeConfig.FIND_LATEST_ARTIFACT, "true")
            //.appendAdditional(SerdeConfig.CHECK_PERIOD_MS, "60000")
            .value();
         
        String ktopicB = KafkaUriBuilder.create(ProducerService.TOPIC_B)
            .appendProperty("brokers", "{{aregsandbox.kafka.brokers}}")
            .appendProperty("valueDeserializer", ProtobufKafkaDeserializer.class.getName())
            .appendProperty("maxRequestSize", "5242880")
            .appendAdditional(SerdeConfig.REGISTRY_URL, "{{registryurl}}")
            //.appendAdditional(SerdeConfig.FIND_LATEST_ARTIFACT, "true")
            //.appendAdditional(SerdeConfig.CHECK_PERIOD_MS, "60000")
            .value();           
        
        from(ktopicA)
            .bean(this, "receive")
            .log("consumer timerA fired\nheaders: ${headers}\nbody: ${body}");

        from(ktopicB)
            .bean(this, "receive")
            .log("consumer timerB fired\nheaders: ${headers}\nbody: ${body}");

    }
    public void receive(Exchange exchange) {   
        Object body = exchange.getIn().getBody();
        log.info("Received type: {}", body.getClass());        
    }
 
}
