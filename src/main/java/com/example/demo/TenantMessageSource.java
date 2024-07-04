package com.example.demo;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class TenantMessageSource {

//    private final ConcurrentMap<String, MessageSource> messageSourceCache = new ConcurrentHashMap<>();
//
//    public MessageSource getMessageSource(String tenantId) {
//        return messageSourceCache.computeIfAbsent(tenantId, this::createMessageSource);
//    }
//
//    private MessageSource createMessageSource(String tenantId) {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename("classpath:/i18n/" + tenantId + "/messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }

    @Bean("tenant1_messageSource")
        private MessageSource firstTenant() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/i18n/tenant1/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean("tenant2_messageSource")
    private MessageSource secondTenant() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/i18n/tenant2/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
