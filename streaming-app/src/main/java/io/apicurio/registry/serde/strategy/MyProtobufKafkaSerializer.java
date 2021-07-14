package io.apicurio.registry.serde.strategy;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Message;

import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.apicurio.registry.rest.client.RegistryClient;
import io.apicurio.registry.serde.ParsedSchema;
import io.apicurio.registry.serde.SchemaResolver;
import io.apicurio.registry.serde.protobuf.ProtobufKafkaSerializer;
import io.apicurio.registry.serde.protobuf.schema.ProtobufSchema;

public class MyProtobufKafkaSerializer<U extends Message> extends ProtobufKafkaSerializer<U> {
    private static final transient Logger log = LoggerFactory.getLogger(MyProtobufKafkaSerializer.class);
    
    public MyProtobufKafkaSerializer() {
        super();
        log.info("MyProtobufKafkaSerializer constructor - no args\ninstance: {}", this);
    }

    public MyProtobufKafkaSerializer(RegistryClient client,
            ArtifactResolverStrategy<ProtobufSchema> artifactResolverStrategy,
            SchemaResolver<ProtobufSchema, U> schemaResolver) {
        super(client, artifactResolverStrategy, schemaResolver);
        log.info("MyProtobufKafkaSerializer constructor client: {}\nartifactResolverStrategy: {}\nschemaResolver: {}\ninstance: {}", client, artifactResolverStrategy, schemaResolver, this);
    }

    public MyProtobufKafkaSerializer(RegistryClient client) {
        super(client);
        log.info("MyProtobufKafkaSerializer constructor client: {}", client);
    }

    public MyProtobufKafkaSerializer(SchemaResolver<ProtobufSchema, U> schemaResolver) {
        super(schemaResolver);
        log.info("MyProtobufKafkaSerializer constructor schemaResolver: {}", schemaResolver);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        log.info("ProtobufKafkaSerializer instance: {}", this);
        log.info("ProtobufKafkaSerializer configs (isKey {}): {}", isKey, configs);    
        super.configure(configs, isKey);
    }

    @Override
    protected void serializeData(Headers headers, ParsedSchema<ProtobufSchema> schema, U data, OutputStream out) throws IOException {
        log.info("ProtobufKafkaSerializer instance: {}", this);
        log.info("ProtobufKafkaSerializer headers: {}", headers);
        log.info("ProtobufKafkaSerializer schema: {}", schema.getParsedSchema().getFileDescriptor().getName());        
        log.info("ProtobufKafkaSerializer data: {}", data);
        int i = 0;
        for(Descriptor desc : schema.getParsedSchema().getFileDescriptor().getMessageTypes()) {
            log.info("ProtobufKafkaSerializer descriptor ({}): {}", i++, desc.getFullName());
        }
        super.serializeData(headers, schema, data, out);
    }
}
