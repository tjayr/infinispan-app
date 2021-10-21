package com.github.tjayr.config;

import com.github.tjayr.model.Module;
import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(
        includeClasses = {Module.class },
        dependsOn = {org.infinispan.protostream.types.java.CommonTypes.class, org.infinispan.protostream.types.java.CommonContainerTypes.class},
        schemaFileName = "modules.proto",
        schemaFilePath = "proto/",
        schemaPackageName = "com.github.tjayr.cache")
public interface ModuleCacheInitializer extends GeneratedSchema {
}
