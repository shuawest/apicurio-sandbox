package com.aregsandbox.repo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.agregsandbox.modela.FactA;
import com.agregsandbox.modela.FactB;
import com.agregsandbox.modela.FactWrapper2;
import com.agregsandbox.modela.MyMessageA;
import com.agregsandbox.modela.OneOfFactWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Message;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.client.hotrod.marshall.MarshallerUtil;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.protostream.annotations.ProtoSchemaBuilderException;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.dsl.QueryResult;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.infinispan.client.Remote;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest 
public class SandboxCacheResourceTest {
    private static final transient Logger log = LoggerFactory.getLogger(SandboxCacheResourceTest.class);

    @Inject
    ObjectMapper mapper;

    @Inject
    RemoteCacheManager cacheManager;

    @Inject
    @Remote("aregsandbox-decision-store")
    RemoteCache<String, Message> decisionStore;

    @Inject
    @Remote("aregsandbox-decision-store")
    RemoteCache<String, FactWrapper2> decisionStore2;
  

    @BeforeEach
    public void prepare() throws ProtoSchemaBuilderException, IOException {

    }
 
    @Test
    public void oneOfTest() throws InterruptedException {
        log.info("Remote cache: {}", decisionStore);
        
        Instant now = Instant.now();
        Timestamp curTime = Timestamp.newBuilder().setSeconds(now.getEpochSecond())
            .setNanos(now.getNano()).build();

        FactA.Builder fab = FactA.newBuilder();
        fab.setNameA("factA");

        OneOfFactWrapper.Builder val1 = OneOfFactWrapper.newBuilder();
        val1.setTopic("fact-topic");
        val1.setPrefix("myprefix");
        val1.setKey("key1");
        val1.setFactA(fab.build());
        OneOfFactWrapper v1 = val1.build();

        decisionStore.put("test2", v1);

        Message fetchedVal1 = decisionStore.get("test2");

        log.info("Fetched test2: {}", fetchedVal1);

        QueryFactory qf = org.infinispan.client.hotrod.Search.getQueryFactory(decisionStore);

        Query<Message> wrapq = qf.create("FROM com.agregsandbox.modela.OneOfFactWrapper");
        QueryResult<Message> res = wrapq.execute();
        log.info("Fetched via query: {}", res.list());
    }

    @Test
    public void oneOfTest2() throws InterruptedException {
        log.info("Remote cache: {}", decisionStore);
        
        Instant now = Instant.now();
        Timestamp curTime = Timestamp.newBuilder().setSeconds(now.getEpochSecond())
            .setNanos(now.getNano()).build();

        FactB.Builder fab = FactB.newBuilder();
        fab.setNameB("factB");

        OneOfFactWrapper.Builder val1 = OneOfFactWrapper.newBuilder();
        val1.setTopic("fact-topic");
        val1.setPrefix("myprefix");
        val1.setKey("key2");
        val1.setFactB(fab.build());
        OneOfFactWrapper v1 = val1.build();

        decisionStore.put("test3", v1);

        Message fetchedVal1 = decisionStore.get("test3");

        log.info("Fetched test3: {}", fetchedVal1);
    }

    @Test
    public void loadCacheTest() throws InterruptedException {        
        for(int i=0; i < 20; i++) {
            FactB.Builder fab = FactB.newBuilder();
            fab.setNameB("factB" + i);

            OneOfFactWrapper.Builder val1 = OneOfFactWrapper.newBuilder();
            val1.setTopic("fact-topic");
            val1.setPrefix("myprefix"+i);
            val1.setKey("keyMany" + i);
            
            if (i % 2 == 0) {
                FactA.Builder abldr = FactA.newBuilder(); 
                abldr.setNameA("factA " + i);
    
                FactA ma = abldr.build();              
                val1.setFactA(ma);
            } else {
                FactB.Builder bbldr = FactB.newBuilder(); 
                bbldr.setNameB("factB " + i);
    
                FactB mb = bbldr.build();                
                val1.setFactB(mb);
            }


            OneOfFactWrapper v1 = val1.build();

            decisionStore.put("testMany"+i, v1);
        }

        QueryFactory qf = Search.getQueryFactory(decisionStore2);

        Query<Message> wrapq = qf.create("FROM com.agregsandbox.modela.OneOfFactWrapper where key like 'keyMany%'");
        QueryResult<Message> res = wrapq.execute();
        log.info("Fetched via query (count {}): {}", res.list().size(), res.list());
        log.info("Query count {}", res.list().size());
    }

    @Test
    public void oneOfTestQueryTest() throws InterruptedException {
        //cacheManager.administration().reindexCache("aregsandbox-decision-store");
                
        QueryFactory qf = Search.getQueryFactory(decisionStore2);

        Query<Message> wrapq = qf.create("FROM com.agregsandbox.modela.OneOfFactWrapper where key like 'keyMany%'");
        QueryResult<Message> res = wrapq.execute();
        log.info("Fetched via query (count {}): {}", res.list().size(), res.list());
        log.info("Query count {}", res.list().size());
    }

    @Test
    public void basicCacheTest1() throws InterruptedException {
        log.info("Remote cache: {}", decisionStore);
        
        Instant now = Instant.now();
        Timestamp curTime = Timestamp.newBuilder().setSeconds(now.getEpochSecond())
            .setNanos(now.getNano()).build();

        MyMessageA.Builder val1 = MyMessageA.newBuilder();
        val1.setId(10);
        val1.setName("test");
        val1.setTimestamp(curTime);
        MyMessageA v1 = val1.build();

        decisionStore.put("test1", v1);

        Message fetchedVal1 = decisionStore.get("test1");

        log.info("Fetched test1: {}", fetchedVal1);
    }


    @Test
    public void basicCacheTest4() throws InterruptedException {
        
        FactB.Builder fab = FactB.newBuilder();
        fab.setNameB("factB");

        FactWrapper2.Builder val1 = FactWrapper2.newBuilder();
        val1.setTopic("fact-topic");
        val1.setPrefix("myprefix");
        val1.setKey("key4");
        val1.setFactB(fab.build());
        FactWrapper2 v1 = val1.build();

        decisionStore.put("test4", v1);

        Message fetchedVal1 = decisionStore.get("test4");
        
        log.info("Fetched test4: {}", fetchedVal1);
    }
    
}