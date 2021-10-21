# IPROTO-219

This is a sample spring boot application which demonstrates a bug in Infinispan when unmarshalling Protostream collections when using clustered caches.

https://issues.redhat.com/projects/IPROTO/issues/IPROTO-219?filter=allopenissues

The application contains a rest endpoint that generates 100k `Moudule` elements in an ArrayList. The `Module` class has fields annotated with `@ProtoField`.
The controller method is annotated with Spring `@Cacheable`, so after the first invocation of the controller endpoint the cache should be populated.

Infinispan is deployed in embedded mode and is configured to use TCPPING. 

The docker-compose file in the repository will build the application and launch 2 application nodes running on ports 9001 and 9002, a gossip server is also launched to use for discovery. 


## Usage:

`mvn clean package`

`docker-compose build`

`docker-compose up`

If all is successful two nodes will be running with a replicated cache

The first call to either node should succeed - data being added to the cache will succeed:

`curl http://localhost:9001/modules`

The bug manifests as an exception when unmarshalling the data on a read from the cache. This can be triggered by making a subsequent call to either node. `curl http://localhost:9002/modules`   

```shell
Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.infinispan.commons.dataconversion.EncodingException: ISPN000495: ProtostreamTranscoder encountered error transcoding content] with root cause
node1_1   | 
node1_1   | java.io.IOException: Invalid WrappedMessage encoding.
node1_1   |     at org.infinispan.protostream.WrappedMessage.readMessage(WrappedMessage.java:529) ~[protostream-4.4.1.Final.jar!/:4.4.1.Final]
node1_1   |     at org.infinispan.protostream.WrappedMessage.readContainer(WrappedMessage.java:616) ~[protostream-4.4.1.Final.jar!/:4.4.1.Final]
node1_1   |     at org.infinispan.protostream.WrappedMessage.readMessage(WrappedMessage.java:373) ~[protostream-4.4.1.Final.jar!/:4.4.1.Final]
node1_1   |     at org.infinispan.protostream.WrappedMessage.read(WrappedMessage.java:351) ~[protostream-4.4.1.Final.jar!/:4.4.1.Final]
node1_1   |     at org.infinispan.protostream.ProtobufUtil.fromWrappedByteArray(ProtobufUtil.java:129) ~[protostream-4.4.1.Final.jar!/:4.4.1.Final]
node1_1   |     at org.infinispan.protostream.ProtobufUtil.fromWrappedByteArray(ProtobufUtil.java:125) ~[protostream-4.4.1.Final.jar!/:4.4.1.Final]
node1_1   |     at org.infinispan.encoding.ProtostreamTranscoder.unmarshallCascading(ProtostreamTranscoder.java:149) ~[infinispan-core-12.1.7.Final.jar!/:12.1.7.Final]
node1_1   |     at org.infinispan.encoding.ProtostreamTranscoder.unmarshall(ProtostreamTranscoder.java:136) ~[infinispan-core-12.1.7.Final.jar!/:12.1.7.Final]
node1_1   |     at org.infinispan.encoding.ProtostreamTranscoder.transcode(ProtostreamTranscoder.java:99) ~[infinispan-core-12.1.7.Final.jar!/:12.1.7.Final]

```
