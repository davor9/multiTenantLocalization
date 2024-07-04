package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class GreetingController {

    private final TenantMessageSource tenantMessageSource;

    @GetMapping("/greet")
    public String greet(@RequestHeader("X-Tenant-ID") String tenantId) {
        MessageSource messageSource = tenantMessageSource.getMessageSource(tenantId);
        return messageSource.getMessage("greeting", null, Locale.ENGLISH);
    }
}
