package com.github.tjayr.config;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.spring.embedded.provider.SpringEmbeddedCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@EnableCaching
@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        var cacheManager = new SpringEmbeddedCacheManager(infinispanCacheManager());
        return cacheManager;
    }


    private EmbeddedCacheManager infinispanCacheManager() {
        EmbeddedCacheManager  manager = null;
        try {
            manager = new DefaultCacheManager("infinispan-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return manager;
    }

    @Bean(name = "modulesKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new ModuleCacheKey();
    }

}
