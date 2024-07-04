package com.example.demo;


import at.porscheinformatik.weblate.spring.AllPropertiesReloadableResourceBundleMessageSource;
import at.porscheinformatik.weblate.spring.WeblateMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


import java.nio.charset.StandardCharsets;

@Configuration
public class Test {

    @Bean
    @Primary
    public WeblateMessageSource messageSource() {
        var weblateMessageSource = new WeblateMessageSource();
        weblateMessageSource.setBaseUrl("https://hosted.weblate.org");
        weblateMessageSource.setProject("my-project");
        weblateMessageSource.setComponent("my-component");
        weblateMessageSource.useAuthentication("api-key");
        weblateMessageSource.setAsync(true); // <- set this for asynchronous loading
        weblateMessageSource.setParentMessageSource(localMessageSource());
        return weblateMessageSource;
    }

    @Bean
    MessageSource localMessageSource() {
        var localMessageSource = new AllPropertiesReloadableResourceBundleMessageSource();
        localMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        localMessageSource.setBasename("messages");
        localMessageSource.setFallbackToSystemLocale(false);
        return localMessageSource;
    }

}

