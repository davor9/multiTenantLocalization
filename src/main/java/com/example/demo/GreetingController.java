package com.example.demo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class GreetingController {

    private final TenantMessageSource wtf;
    private final ApplicationContext applicationContext;

    @GetMapping("/greet")
    public String greet(@RequestBody @Validated Request test) {
        var locale = LocaleContextHolder.getLocale();
        var tenant = TenantContext.getCurrentTenant();
        System.out.println("LOOOOOL");
        return wtf.getMessage("greeting", null, locale);
    }


//    @GetMapping("/greet/new")
//    public String greetNew(@RequestHeader("X-Tenant-ID") String tenantId) {
//        MessageSource messageSource = (MessageSource) applicationContext.getBean(tenantId + "_messageSource");
//        return messageSource.getMessage("greeting", null, Locale.ENGLISH);
//    }
}
