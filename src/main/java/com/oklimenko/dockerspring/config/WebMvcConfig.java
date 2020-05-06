package com.oklimenko.dockerspring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig
implements WebMvcConfigurer {

    private final RequestCounterInterceptor requestCounterInterceptor ;
    private final RequestTimingInterceptor requestTimingInterceptor ;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(requestCounterInterceptor);//.addPathPatterns("/*");
        registry.addInterceptor(requestTimingInterceptor);//.addPathPatterns("/*");
    }
}
