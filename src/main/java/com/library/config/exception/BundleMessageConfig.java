package com.library.config.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class BundleMessageConfig {

    @Value("i18n/messages")
    private String baseName;

    @Value("en")
    private String localDefault;

    @Bean("messages")
    public ResourceBundleMessageSource resourceBundleMessageSource(){
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename(baseName);
        source.setDefaultEncoding("UTF-8");
        source.setDefaultLocale(new Locale(localDefault));
        return  source;
    }
}
