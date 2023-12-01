package com.slackcollection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slackcollection.config.aop.HTTPLoggingAspect;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class AopConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public HTTPLoggingAspect logAspect(){
        return new HTTPLoggingAspect(objectMapper);
    }

}
