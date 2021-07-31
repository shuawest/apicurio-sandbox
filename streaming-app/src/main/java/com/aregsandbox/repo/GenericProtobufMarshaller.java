package com.aregsandbox.repo;

import java.io.IOException;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;

import org.infinispan.protostream.ProtobufTagMarshaller;
import org.infinispan.protostream.TagReader;
import org.infinispan.protostream.TagWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GenericProtobufMarshaller<T extends AbstractMessage> implements ProtobufTagMarshaller<T> {
    private static final transient Logger log = LoggerFactory.getLogger(GenericProtobufMarshaller.class);

    private final Class<? extends T> javaClass;
    private final String typeName;
    private final ByteArrayParseFunction<byte[], T> parseFromMethod;

    public GenericProtobufMarshaller(
        final Class<? extends T> javaClass, 
        final String typeName, 
        ByteArrayParseFunction<byte[], T> parseFromMethod) 
    {
        this.javaClass = javaClass;
        this.typeName = typeName;
        this.parseFromMethod = parseFromMethod;
    }

    @Override
    public Class<? extends T> getJavaClass() {
        return javaClass;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public T read(ReadContext ctx) throws IOException {
        TagReader reader = ctx.getReader();
                       
        byte[] rawBytes = reader.fullBufferArray();
        T t = parseFromMethod.apply(rawBytes);
        
        return t;
    }

    @Override
    public void write(WriteContext ctx, T t) throws IOException {
        byte[] tBytes = t.toByteArray();
        log.info("writing bytes: {}", tBytes);

        TagWriter out = ctx.getWriter();
        out.writeRawBytes(tBytes, 0, tBytes.length);
    }

    @FunctionalInterface
    public interface ByteArrayParseFunction<T, R> {
        R apply(T rawBytes) throws InvalidProtocolBufferException;
    }

}
