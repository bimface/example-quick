package com.bimface.example.quick.timer;

import com.bimface.example.quick.service.ClearDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "clear.open.scheduled")
public class ClearDbTimer {

    @Autowired
    private ClearDbService clearDbService;

    @Scheduled(cron = "0 0 1 * * ?") // 01:00:00 every day
    public void clearDb(){
        clearDbService.clearFile();
        clearDbService.clearIntegrate();
        clearDbService.clearIntegrateFile();
    }
}
