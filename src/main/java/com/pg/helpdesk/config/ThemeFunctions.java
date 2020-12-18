package com.pg.helpdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.function.BiFunction;
import java.util.function.Function;

@Configuration
public class ThemeFunctions {

    @Bean
    public Function<String, String> currentUrlWithoutParam() {
        return param -> ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replaceQueryParam(param)
                .toUriString();
    }

    @Bean
    public BiFunction<String, String, String> currentUrlWithoutParams2() {
        return (param, param2) -> ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replaceQueryParam(param)
                .replaceQueryParam(param2)
                .toUriString();
    }

}
