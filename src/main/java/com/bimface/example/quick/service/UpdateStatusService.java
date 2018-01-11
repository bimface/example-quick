package com.bimface.example.quick.service;

public interface UpdateStatusService {
    /**
     * 更新文件转换状态
     */
    void uploadTranslateStatus();

    /**
     * 更新集成状态
     */
    void uploadIntegrateStatus();

    /**
     * 更新文件转换离线包状态
     */
    void uploadFileDatabagStatus();

    /**
     * 更新文件集成离线包状态
     */
    void uploadIntegrateDatabagStatus();
}
