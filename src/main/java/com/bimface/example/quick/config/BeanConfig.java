package com.bimface.example.quick.config;

import com.bimface.sdk.BimfaceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * @author dup, 2017-11-22
 */
@Configuration
public class BeanConfig {

    @Value("${app.key}")
    private String appKey;
    @Value("${app.secret}")
    private String appSecret;

    @Bean
    public BimfaceClient bimfaceClient(){
        return new BimfaceClient(appKey, appSecret);
    }

}
