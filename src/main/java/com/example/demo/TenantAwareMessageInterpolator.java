//package com.example.demo;
//
//import jakarta.validation.MessageInterpolator;
//import lombok.RequiredArgsConstructor;
//import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.context.NoSuchMessageException;
//import org.springframework.stereotype.Component;
//
//import java.util.Locale;
//import java.util.Map;
//
//@Component
//public class TenantAwareMessageInterpolator implements MessageInterpolator {
//
//    private final TenantMessageSource messageSource;
//    private final ResourceBundleMessageInterpolator defaultInterpolator;
//
//    @Autowired
//    public TenantAwareMessageInterpolator(TenantMessageSource messageSource) {
//        this.messageSource = messageSource;
//        this.defaultInterpolator = new ResourceBundleMessageInterpolator();
//    }
//
//    @Override
//    public String interpolate(String messageTemplate, Context context) {
//        return interpolate(messageTemplate, context, Locale.getDefault());
//    }
//
//    @Override
//    public String interpolate(String messageTemplate, Context context, Locale locale) {
//        String tenantId = TenantContext.getCurrentTenant();
//        if (tenantId != null) {
//            String tenantMessage = messageSource.getMessage(messageTemplate, context.getConstraintDescriptor().getAttributes().values().toArray(), locale);
//            if (tenantMessage != null) {
//                return tenantMessage;
//            }
//        }
//        return defaultInterpolator.interpolate(messageTemplate, context, locale);
//    }
//}
