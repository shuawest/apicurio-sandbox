package com.aregsandbox.services;

import java.time.Instant;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.agregsandbox.modela.MyMessageA;
import com.agregsandbox.modela.MyMessageB;
import com.agregsandbox.modelb.MyMessageC;
import com.agregsandbox.modelb.MyMessageD;
import com.aregsandbox.config.AppConfig;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;

import org.apache.camel.Exchange;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.apicurio.registry.serde.SerdeConfig;
import io.apicurio.registry.serde.strategy.MyArtifactResolverStrategy;
import io.apicurio.registry.serde.strategy.MyProtobufKafkaSerializer;
import io.apicurio.registry.serde.strategy.MySchemaResolver;

@ApplicationScoped
public class ProducerService extends EndpointRouteBuilder {
    private static final transient Logger log = LoggerFactory.getLogger(ProducerService.class);

    public static final String TOPIC_A = "samplea-topic";
    public static final String TOPIC_B = "sampleb-topic";

    @Inject
    AppConfig config;

    private Integer genCountA = 0;
    private Integer genCountB = 10000;

    @Override
    public void configure() throws Exception {
       
        String kdestA = KafkaUriBuilder.create(ProducerService.TOPIC_A)
            .appendProperty("brokers", "{{aregsandbox.kafka.brokers}}")
            .appendProperty("clientId", "producerA")
            .appendProperty("valueSerializer", MyProtobufKafkaSerializer.class.getName())
            .appendProperty("maxRequestSize", "5242880")
            .appendAdditional(SerdeConfig.REGISTRY_URL, "{{registryurl}}")
            .appendAdditional(SerdeConfig.SCHEMA_RESOLVER, MySchemaResolver.class.getName())
            .appendAdditional(SerdeConfig.ARTIFACT_RESOLVER_STRATEGY, MyArtifactResolverStrategy.class.getName())
            .appendAdditional(SerdeConfig.EXPLICIT_ARTIFACT_GROUP_ID, "aregsandbox")
            .appendAdditional(SerdeConfig.EXPLICIT_ARTIFACT_ID, "samplea")
            .appendAdditional(SerdeConfig.FIND_LATEST_ARTIFACT, "true")
            //.appendAdditional(SerdeConfig.CHECK_PERIOD_MS, "60000")
            .value();
    
        String kdestB = KafkaUriBuilder.create(ProducerService.TOPIC_B)
            .appendProperty("brokers", "{{aregsandbox.kafka.brokers}}")
            .appendProperty("clientId", "producerB")
            .appendProperty("valueSerializer", MyProtobufKafkaSerializer.class.getName())
            .appendProperty("maxRequestSize", "5242880")
            .appendAdditional(SerdeConfig.REGISTRY_URL, "{{registryurl}}")
            .appendAdditional(SerdeConfig.SCHEMA_RESOLVER, MySchemaResolver.class.getName())
            .appendAdditional(SerdeConfig.ARTIFACT_RESOLVER_STRATEGY, MyArtifactResolverStrategy.class.getName())
            .appendAdditional(SerdeConfig.EXPLICIT_ARTIFACT_GROUP_ID, "aregsandbox")
            .appendAdditional(SerdeConfig.EXPLICIT_ARTIFACT_ID, "sampleb")
            .appendAdditional(SerdeConfig.FIND_LATEST_ARTIFACT, "true")
            //.appendAdditional(SerdeConfig.CHECK_PERIOD_MS, "60000")
            .value();
    

        log.info("Kafka connection A: {}\n\n\n", kdestA);
        log.info("Kafka connection B: {}\n\n\n", kdestB);

        from("timer:producerTimerA?repeatCount=1000&delay=1s&period=1s")
            .bean(this, "genA")
            //.log("producer timer fired ${headers.genCount}:\n${body}");
            .to(kdestA);

        from("timer:producerTimerB?repeatCount=1000&delay=1s&period=1s")
            .bean(this, "genB")
            //.log("producer timer fired ${headers.genCount}:\n${body}");
            .to(kdestB);
    }

    public void genA(Exchange exchange) {   
        exchange.getOut().setHeader("genCount", genCountA);

        if (genCountA % 2 == 0) {
            MyMessageA.Builder abldr = MyMessageA.newBuilder();      
            abldr.setId(genCountA);
            abldr.setName("message a " + genCountA);
            abldr.setTimestamp(tsFrom(Instant.now()));                    

            MyMessageA ma = abldr.build();
            exchange.getOut().setBody(ma);
        } else {
            MyMessageB.Builder bbldr = MyMessageB.newBuilder();      
            bbldr.setId(genCountA);
            bbldr.setName("message b " + genCountA);
            bbldr.setTimestamp(tsFrom(Instant.now()));
            
            MyMessageB mb = bbldr.build();
            exchange.getOut().setBody(mb);
        }

        genCountA++;
    }

    public void genB(Exchange exchange) {   
        exchange.getOut().setHeader("genCount", genCountB);

        if (genCountB % 2 == 0) {
            MyMessageC.Builder abldr = MyMessageC.newBuilder();      
            abldr.setId(genCountB);
            abldr.setName("message c " + genCountB);
            abldr.setTimestamp(tsFrom(Instant.now()));                    

            MyMessageC ma = abldr.build();
            exchange.getOut().setBody(ma);
        } else {
            MyMessageD.Builder bbldr = MyMessageD.newBuilder();      
            bbldr.setId(genCountB);
            bbldr.setName("message d " + genCountB);
            bbldr.setTimestamp(tsFrom(Instant.now()));
            
            MyMessageD mb = bbldr.build();
            exchange.getOut().setBody(mb);
        }

        genCountB++;
    }

    public void genJson(Exchange exchange) throws InvalidProtocolBufferException {   
        exchange.getOut().setHeader("genCount", genCountA);

        String msga = "{\"id\":" + genCountA + ",\"name\":\"JSON Message A " + genCountA + "\",\"timestamp\":\"2021-07-07T21:01:29.588313Z\",\"reason\":1}";
        String msgb1 = "{\"id\":" + genCountA + ",\"name\":\"JSON Message B " + genCountA + "\",\"timestamp\":\"2021-07-07T21:01:29.588313Z\",\"side\":\"BuySide\",\"reason\":1}";
        String msgb2 = "{\"id\":" + genCountA + ",\"name\":\"JSON Message B " + genCountA + "\",\"timestamp\":\"2021-07-07T21:01:29.588313Z\",\"side\":\"SellSide\",\"reason\":2}";

        if (genCountA % 2 == 0) {
            MyMessageA.Builder abldr = MyMessageA.newBuilder();  
            JsonFormat.parser().merge(msga, abldr); 
            MyMessageA ma = abldr.build();
            exchange.getOut().setBody(ma);
        } else if ((genCountA/2)%2 == 0) {
            MyMessageB.Builder bbldr = MyMessageB.newBuilder();      
            JsonFormat.parser().merge(msgb1, bbldr); 
            MyMessageB mb = bbldr.build();
            exchange.getOut().setBody(mb);
        } else {
            MyMessageB.Builder bbldr = MyMessageB.newBuilder();      
            JsonFormat.parser().merge(msgb2, bbldr); 
            MyMessageB mb = bbldr.build();
            exchange.getOut().setBody(mb);
        }

        genCountA++;
    }

    private static Timestamp tsFrom(Instant instant) {
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
 
}
