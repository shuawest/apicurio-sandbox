package com.aregsandbox.repo;

import java.io.UncheckedIOException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.ConfigProvider;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.protostream.BaseMarshaller;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.SerializationContextInitializer;
import org.infinispan.protostream.SerializationContext.MarshallerProvider;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import io.apicurio.registry.rest.client.RegistryClient;
import io.apicurio.registry.rest.client.RegistryClientFactory;
import io.smallrye.config.SmallRyeConfig;

import com.agregsandbox.modela.FactA;
import com.agregsandbox.modela.FactB;
import com.agregsandbox.modela.FactWrapper2;
import com.agregsandbox.modela.GatewayErrorA;
import com.agregsandbox.modela.MyMessageA;
import com.agregsandbox.modela.MyMessageB;
import com.agregsandbox.modela.OneOfFactWrapper;
import com.aregsandbox.config.AppConfig;
import com.aregsandbox.config.RegistryConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryContextInitializerImpl implements RepositoryContextInitializer {
    private static final transient Logger log = LoggerFactory.getLogger(RepositoryContextInitializerImpl.class);

    private RegistryConfig regConfig;

    private RegistryConfig regConfig() {
        if (regConfig == null) {
            regConfig = ConfigProvider.getConfig().unwrap(SmallRyeConfig.class).getConfigMapping(AppConfig.class).registry(); 
        }
        return regConfig;
    }

    @Override
    public String getProtoFileName() {
        return regConfig().artifact() + ".proto";
    }

    @Override
    public String getProtoFile() throws UncheckedIOException {
        RegistryClient client = RegistryClientFactory.create(regConfig().url());
        
        InputStream schema;
        if("latest".equals(regConfig().version()))
            schema = client.getLatestArtifact(regConfig().group(), regConfig().artifact()); 
        else
            schema = client.getArtifactVersion(regConfig().group(), regConfig().artifact(), regConfig().version());

        String origProto = new BufferedReader(new InputStreamReader(schema, Charset.forName("UTF-8"))).lines().collect(Collectors.joining("\n"));

        String proto = transformForProtostream(origProto);
                    
        log.info("read proto file: \n{}", proto);

        return proto;
    }

    private String transformForProtostream(String origProto) {
        String proto = origProto + "\nmessage Timestamp {\n    int64 seconds = 1;\n    int32 nanos = 2;\n}\n";
        proto = proto.replace("\t", "    ")
                    .replace("import \"google/protobuf/timestamp.proto\";", "")
                    .replace(" int32 ",     " optional int32 ")
                    .replace(" uint32 ",    " optional uint32 ")
                    .replace(" sint32 ",    " optional sint32 ")
                    .replace(" fixed32 ",   " optional fixed32 ")
                    .replace(" sfixed32 ",  " optional sfixed32 ")
                    .replace(" int64 ",     " optional int64 ")
                    .replace(" uint64 ",    " optional uint64 ")
                    .replace(" sint64 ",    " optional sint64 ")
                    .replace(" fixed64 ",   " optional fixed64 ")
                    .replace(" sfixed64 ",  " optional sfixed64 ")
                    .replace(" float ",     " optional float ")
                    .replace(" double ",    " optional double ")
                    .replace(" bytes ",     " optional bytes ")
                    .replace(" bool ",      " optional bool ")
                    .replace(" string ",    " optional string ")
                    .replace("   FactA ",     "   optional FactA ")
                    .replace("   FactB ",     "   optional FactB ")
                    .replace(" google.protobuf.Timestamp ", " optional Timestamp ")
                    .replace(" optional string key ", " /* @Field(index = Index.YES, store = Store.YES) */\n    required string key ")
                    .replace("message ", "/* @Indexed */\nmessage ");

                    // proto = proto.replace("\t", "    ")
                    // .replace("import \"google/protobuf/timestamp.proto\";", "")
                    // .replace(" int32 ",     " \n/* @Field */    optional int32 ")
                    // .replace(" uint32 ",    " \n/* @Field */    optional uint32 ")
                    // .replace(" sint32 ",    " \n/* @Field */    optional sint32 ")
                    // .replace(" fixed32 ",   " \n/* @Field */    optional fixed32 ")
                    // .replace(" sfixed32 ",  " \n/* @Field */    optional sfixed32 ")
                    // .replace(" int64 ",     " \n/* @Field */    optional int64 ")
                    // .replace(" uint64 ",    " \n/* @Field */    optional uint64 ")
                    // .replace(" sint64 ",    " \n/* @Field */    optional sint64 ")
                    // .replace(" fixed64 ",   " \n/* @Field */    optional fixed64 ")
                    // .replace(" sfixed64 ",  " \n/* @Field */    optional sfixed64 ")
                    // .replace(" float ",     " \n/* @Field */    optional float ")
                    // .replace(" double ",    " \n/* @Field */    optional double ")
                    // .replace(" bytes ",     " \n/* @Field */    optional bytes ")
                    // .replace(" bool ",      " \n/* @Field */    optional bool ")
                    // .replace(" string ",    " \n/* @Field */    optional string ")
                    // .replace("   FactA ",     "   \n/* @Field */    optional FactA ")
                    // .replace("   FactB ",     "   \n/* @Field */    optional FactB ")
                    // .replace(" google.protobuf.Timestamp ", " \n/* @Field */    optional Timestamp ")
                    // .replace(" optional string key ", " /* @Field(index = Index.YES, store = Store.YES) */\n    required string key ")
                    // .replace("message ", "/* @Indexed */\nmessage ");

        return proto;
    }

    @Override
    public void registerSchema(SerializationContext ctx) {
        ctx.registerProtoFiles(FileDescriptorSource.fromString(getProtoFileName(), getProtoFile()));
    }

    @Override
    public void registerMarshallers(SerializationContext ctx) {
        ctx.registerMarshaller(new GenericProtobufMarshaller<>(GatewayErrorA.class, "com.agregsandbox.modela.GatewayErrorA", GatewayErrorA::parseFrom));   
        ctx.registerMarshaller(new GenericProtobufMarshaller<>(MyMessageA.class, "com.agregsandbox.modela.MyMessageA", MyMessageA::parseFrom)); 
        ctx.registerMarshaller(new GenericProtobufMarshaller<>(MyMessageB.class, "com.agregsandbox.modela.MyMessageB", MyMessageB::parseFrom)); 
        ctx.registerMarshaller(new GenericProtobufMarshaller<>(OneOfFactWrapper.class, "com.agregsandbox.modela.OneOfFactWrapper", OneOfFactWrapper::parseFrom)); 
        ctx.registerMarshaller(new GenericProtobufMarshaller<>(FactWrapper2.class, "com.agregsandbox.modela.FactWrapper2", FactWrapper2::parseFrom)); 
        ctx.registerMarshaller(new GenericProtobufMarshaller<>(FactA.class, "com.agregsandbox.modela.FactA", FactA::parseFrom)); 
        ctx.registerMarshaller(new GenericProtobufMarshaller<>(FactB.class, "com.agregsandbox.modela.FeedL2Bid", FactB::parseFrom)); 
        //ctx.registerMarshaller(new GenericProtobufMarshaller<>(Timestamp.class, "com.agregsandbox.modela.Timestamp", Timestamp::parseFrom)); 
    }
    
    public class MyProvider implements MarshallerProvider {

        public BaseMarshaller<?> getMarshaller(String typeName) {
            return null;
        }
  
        public BaseMarshaller<?> getMarshaller(Class<?> javaClass) {
            return null;
        }
     }
}
