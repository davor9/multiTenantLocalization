package com.example.demo;

import jakarta.validation.MessageInterpolator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

@Component
@Slf4j
public class TenantAwareMessageInterpolator implements MessageInterpolator {

    private final MessageSource messageSource;
    private final ResourceBundleMessageInterpolator defaultInterpolator;


    @Autowired
    public TenantAwareMessageInterpolator(TenantMessageSource messageSource) {
        this.messageSource = messageSource;
        this.defaultInterpolator = new ResourceBundleMessageInterpolator();
    }

    @Override
    public String interpolate(String messageTemplate, Context context) {
        return interpolate(messageTemplate, context, Locale.getDefault());
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        log.info("Interpolating message for template: " + messageTemplate + " and locale: " + locale);
        String tenantId = TenantContext.getCurrentTenant();
        log.info("Tenant ID: " + tenantId);
        try {
            // Attempt to resolve the message using the tenant-specific message source
            String tenantMessage = messageSource.getMessage(messageTemplate, context.getConstraintDescriptor().getAttributes().values().toArray(), locale);
            log.info("Resolved tenant message: " + tenantMessage);
            return tenantMessage;
        } catch (Exception e) {
            log.warn("Failed to resolve tenant message, falling back to default. Error: " + e.getMessage());
            // Fallback to the default interpolator if any error occurs
            return defaultInterpolator.interpolate(messageTemplate, context, locale);
        }
    }
}
