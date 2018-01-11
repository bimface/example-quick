package com.bimface.example.quick.service;

import com.bimface.example.quick.dao.model.ExampleQuickFile;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.bean.response.OfflineDatabagBean;
import com.bimface.sdk.bean.response.TranslateBean;
import com.bimface.sdk.exception.BimfaceException;

import java.text.ParseException;
import java.util.List;

public interface FileService {
    /**
     * 查询列表
     *
     * @return
     * @param suffix
     * @param translateStatus
     */
    List<ExampleQuickFile> getFileList(String suffix, String translateStatus);

    /**
     * file入库
     *
     * @param fileBean
     * @return
     * @throws ParseException
     */
    ExampleQuickFile store(FileBean fileBean) throws ParseException;

    /**
     * 发起转换
     *
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    TranslateBean translate(Long fileId) throws BimfaceException;

    /**
     * 生成离线数据包
     *
     * @param fileId
     * @return
     * @throws BimfaceException
     */
    OfflineDatabagBean databag(Long fileId) throws BimfaceException;

    /**
     * delete
     *
     * @param fileId
     */
    void delete(Long fileId);
}
