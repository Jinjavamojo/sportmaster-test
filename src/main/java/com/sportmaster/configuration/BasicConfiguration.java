package com.sportmaster.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportmaster.SportMasterEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = {
        "com.sportmaster.dto",
        "com.sportmaster.formatter",
        "com.sportmaster.reader",
        "com.sportmaster.validation",
        "com.sportmaster.handler"}
        )
public class BasicConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
