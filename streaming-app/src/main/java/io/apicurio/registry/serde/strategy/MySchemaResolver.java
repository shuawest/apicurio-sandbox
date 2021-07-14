package io.apicurio.registry.serde.strategy;

import io.apicurio.registry.serde.DefaultSchemaResolver;
import io.apicurio.registry.serde.ParsedSchema;
import io.apicurio.registry.serde.SchemaLookupResult;
import io.apicurio.registry.serde.SchemaParser;

import java.util.Map;

import org.apache.kafka.common.header.Headers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySchemaResolver<S, T> extends DefaultSchemaResolver<S, T> {
    private static final transient Logger log = LoggerFactory.getLogger(MySchemaResolver.class);
    
    @Override
    public void reset() {
        super.reset();
        log.info("MySchemaResolver reset\ninstance: {}", this);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey, SchemaParser<S> schemaParser) {
        log.info("MySchemaResolver configure\nconfigs: {}\nisKey: {}\nschemaParser: {}\ninstance: {}", configs, isKey, schemaParser, this);
        super.configure(configs, isKey, schemaParser);
    }

    @Override
    public SchemaLookupResult<S> resolveSchema(String topic, Headers headers, T data, ParsedSchema<S> parsedSchema) {
        log.info("MySchemaResolver resolveSchema\ntopic: {}\nheaders: {}\ndata: {}\nparsedSchema: {}\ninstance: {}", topic, headers, data, parsedSchema, this);
        return super.resolveSchema(topic, headers, data, parsedSchema);
    }

    @Override
    public SchemaLookupResult<S> resolveSchemaByArtifactReference(ArtifactReference reference) {
        log.info("MySchemaResolver resolveSchema\nreference: {}\ninstance: {}", reference, this);
        return super.resolveSchemaByArtifactReference(reference);
    }

}
