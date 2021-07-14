package io.apicurio.registry.serde.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyArtifactResolverStrategy<T> extends TopicIdStrategy<T> {
    private static final transient Logger log = LoggerFactory.getLogger(MyProtobufKafkaSerializer.class);

    @Override
    public ArtifactReference artifactReference(String topic, boolean isKey, T schema) {
        ArtifactReference ref = super.artifactReference(topic, isKey, schema);
        log.info("MyArtifactResolverStrategy artifactReference\ntopic: {}\nisKey: {}\nschema: {}\ninstance: {}", topic, isKey, schema, this);
        return ref;
    }

    @Override
    public boolean loadSchema() {
        boolean loaded = super.loadSchema();
        log.info("MyArtifactResolverStrategy loadSchema: {}\ninstance: {}", loaded, this);
        return loaded;
    }
    
}
