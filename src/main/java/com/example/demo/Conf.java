package com.example.demo;

import jakarta.validation.MessageInterpolator;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class Conf {

    private final MessageSource messageSource;
    private final MessageInterpolator tenantAwareMessageInterpolator;


//    @Bean
//    public MessageSource messageSource() {
//        return tenantMessageSource;
//    }
//
//        return new MessageSource() {
//            @Override
//            public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
//                String tenant = TenantContext.getCurrentTenant();
//                return tenantMessageSource.getMessageSource(tenant).getMessage(code, args, defaultMessage, locale);
//            }
//
//            @Override
//            public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
//                try {
//                    String tenant = TenantContext.getCurrentTenant();
//                    return tenantMessageSource.getMessageSource(tenant).getMessage(code, args, locale);
//                }
//                catch (NoSuchMessageException e) {
//                    return tenantMessageSource.getMessageSource("default").getMessage(code, args, locale);
//                }
//            }
//
//            @Override
//            public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
//                String tenant = TenantContext.getCurrentTenant();
//                return tenantMessageSource.getMessageSource(tenant).getMessage(resolvable, locale);
//            }
//        };
//    }

//    @Component
//    class TenantAwareMessageInterpolator implements MessageInterpolator {
//
//        @Override
//    public String interpolate(String messageTemplate, Context context) {
//        return interpolate(messageTemplate, context, Locale.getDefault());
//    }
//
//    @Override
//    public String interpolate(String messageTemplate, Context context, Locale locale) {
//        String tenantId = TenantContext.getCurrentTenant();
//        Map<String, Object> attributes = context.getConstraintDescriptor().getAttributes();
//        Object[] messageParameters = attributes.values().toArray();
//        try {
//            if (tenantId != null) {
//                // Fetch the message from the tenant-specific bundle
//
//                String tenantMessage = messageSource().getMessage(messageTemplate, messageParameters, locale);
//                if (tenantMessage != null) {
//                    return tenantMessage;
//                }
//            }
//        }
//        catch (NoSuchMessageException e) {
//            // Fallback to the default message
//            return messageSource().getMessage(messageTemplate, messageParameters, locale);
//        }
//        return "";
//    }
//    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH); // Set default locale
        return slr;
    }

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setMessageInterpolator(tenantAwareMessageInterpolator);
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }



}
