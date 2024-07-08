//package com.example.demo;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.MessageSource;
//import org.springframework.context.MessageSourceResolvable;
//import org.springframework.context.NoSuchMessageException;
//import org.springframework.context.annotation.Bean;
//
//import java.util.Locale;
//
//@RequiredArgsConstructor
//public class Jala {
//
//    private final TenantMessageSource tenantMessageSource;
//
//    @Bean
//    public MessageSource messageSource() {
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
//}
