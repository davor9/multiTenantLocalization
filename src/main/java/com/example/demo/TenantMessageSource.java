package com.example.demo;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class TenantMessageSource implements MessageSource {

    private static final String DEFAULT_BASENAME = "classpath:/i18n/default/messages";
    private static final String TENANT_BASENAME_PREFIX = "classpath:/i18n/";

    private final ReloadableResourceBundleMessageSource defaultMessageSource;

    public TenantMessageSource() {
        this.defaultMessageSource = new ReloadableResourceBundleMessageSource();
        this.defaultMessageSource.setBasename(DEFAULT_BASENAME);
        this.defaultMessageSource.setDefaultEncoding("UTF-8");
        this.defaultMessageSource.setCacheSeconds(-1);
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        String tenantId = TenantContext.getCurrentTenant();
        if (tenantId != null) {
            String tenantMessage = resolveTenantMessage(tenantId, code, args, locale);
            if (tenantMessage != null) {
                return tenantMessage;
            }
        }
        // Fallback to default message source
        return defaultMessageSource.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return getMessage(code, args, null, locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        String tenantId = TenantContext.getCurrentTenant();
        if (tenantId != null) {
            for (String code : resolvable.getCodes()) {
                String tenantMessage = resolveTenantMessage(tenantId, code, resolvable.getArguments(), locale);
                if (tenantMessage != null) {
                    return tenantMessage;
                }
            }
        }
        // Fallback to default message source
        return defaultMessageSource.getMessage(resolvable, locale);
    }

    private String resolveTenantMessage(String tenantId, String code, Object[] args, Locale locale) {
        ReloadableResourceBundleMessageSource tenantMessageSource = new ReloadableResourceBundleMessageSource();
        tenantMessageSource.setBasename(TENANT_BASENAME_PREFIX + tenantId + "/messages");
        tenantMessageSource.setDefaultEncoding("UTF-8");
        tenantMessageSource.setCacheSeconds(3600);

        try {
            return tenantMessageSource.getMessage(code, args, locale);
        } catch (NoSuchMessageException e) {
            // If no message is found, return null to allow fallback to default messages
            return null;
        }
    }
}

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
//        messageSource.setCacheSeconds(30);
//        return messageSource;
//    }

//    @Bean("tenant1_messageSource")
//        private MessageSource firstTenant() {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename("classpath:/i18n/tenant1/messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }
//
//    @Bean("tenant2_messageSource")
//    private MessageSource secondTenant() {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename("classpath:/i18n/tenant2/messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }
