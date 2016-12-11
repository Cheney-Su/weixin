package com.doubles.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2016/7/12.
 * USER: Suhuaqiang
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 根据配置文件配置的simpleClientHttpRequestFactory配置超时参数
     * @return
     */
    @Bean(name = "simpleClientHttpRequestFactory")
    @Qualifier("simpleClientHttpRequestFactory")
    @ConfigurationProperties(prefix="simpleClientHttpRequestFactory")
    public SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
        return new SimpleClientHttpRequestFactory();
    }

    /**
     * 超时参数
     * @param factory
     * @return
     */
    @Bean(name="restTemplate")
    public RestTemplate getRestTemplateBean(@Qualifier("simpleClientHttpRequestFactory")SimpleClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }
}
