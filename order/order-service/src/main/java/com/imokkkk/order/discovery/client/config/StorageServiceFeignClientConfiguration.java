package com.imokkkk.order.discovery.client.config;

import com.imokkkk.order.discovery.client.decoder.ApiResponseDecoder;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuwy
 * @date 2023-07-13 14:05
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
public class StorageServiceFeignClientConfiguration {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;
    @Bean
    public Decoder feignDecoder() {
        return new ApiResponseDecoder(new ResponseEntityDecoder(new SpringDecoder(messageConverters)));
    }
}
