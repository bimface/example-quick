package com.bimface.example.quick.listener;

import com.bimface.example.quick.service.ClearDbService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ClearDbService clearDbService = event.getApplicationContext().getBean(ClearDbService.class);
        clearDbService.clearFile();
        clearDbService.clearIntegrate();
        clearDbService.clearIntegrateFile();
    }
}