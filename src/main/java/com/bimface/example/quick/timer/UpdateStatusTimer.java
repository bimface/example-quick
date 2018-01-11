package com.bimface.example.quick.timer;

import com.bimface.example.quick.service.UpdateStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateStatusTimer {

    @Autowired
    private UpdateStatusService updateStatusService;

    /**
     * 每五秒更新转换和集成的状态
     */
    @Scheduled(cron = "*/5 * * * * *")
    public void timer() {
        updateStatusService.uploadTranslateStatus();
        updateStatusService.uploadIntegrateStatus();
        updateStatusService.uploadFileDatabagStatus();
        updateStatusService.uploadIntegrateDatabagStatus();
    }
}
