package com.pg.helpdesk.config;

import io.springlets.format.config.SpringletsEntityFormatWebConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Configuration
public class WebMvcConfig extends SpringletsEntityFormatWebConfiguration {
    public WebMvcConfig(MessageSource messageSource, ApplicationContext applicationContext) {
        super(messageSource, applicationContext);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").
                addResourceLocations("classpath:/static/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(Date.class, new DateFormatter("dd-MM-yyyy HH:mm"));
        registry.addFormatterForFieldType(LocalDate.class, new DateFormatter("dd-MM-yyyy"));
        registry.addFormatterForFieldType(BigDecimal.class, new NumberStyleFormatter("#,###,###,###.## PLN"));
    }
}
