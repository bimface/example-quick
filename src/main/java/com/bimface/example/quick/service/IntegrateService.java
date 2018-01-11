package com.bimface.example.quick.service;

import com.bimface.example.quick.dao.model.ExampleQuickIntegrate;
import com.bimface.example.quick.dao.model.ExampleQuickIntegrateFile;
import com.bimface.sdk.bean.request.integrate.IntegrateRequest;
import com.bimface.sdk.bean.response.OfflineDatabagBean;
import com.bimface.sdk.exception.BimfaceException;

import java.text.ParseException;
import java.util.List;

public interface IntegrateService {
    /**
     * 查询分页列表
     *
     * @return
     */
    List<ExampleQuickIntegrate> getAll();

    /**
     * 获取集成的文件列表
     *
     * @param integrateId
     * @return
     */
    List<ExampleQuickIntegrateFile> getIntegrateFiles(Long integrateId);

    /**
     * 发起集成模型
     *
     * @param integrateRequest
     * @return
     * @throws BimfaceException
     * @throws ParseException
     */
    ExampleQuickIntegrate integrate(IntegrateRequest integrateRequest) throws BimfaceException, ParseException;

    /**
     * 生成离线包
     *
     * @param integrateId
     * @return
     */
    OfflineDatabagBean databag(Long integrateId) throws BimfaceException;

    /**
     * 删除模型
     *
     * @param integrateId
     */
    void delete(Long integrateId);
}
