package com.bank.config;

import com.bank.entity.vo.TransactionVo;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class CacheConfig {
    @Value("${service.cache.caffeine.maximumSize}")
    private int maximumSize;

    @Value("${service.cache.caffeine.expireAfterWrite}")
    private long expireAfterWrite;

    @Bean(name = "transanction")
    public Cache<String, TransactionVo> transanctionCache() {
        log.info("[加载transanctionCache]");
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(maximumSize)
                // 设置过期时间
                .expireAfterWrite(expireAfterWrite, TimeUnit.MINUTES)
                .build();
    }
}
