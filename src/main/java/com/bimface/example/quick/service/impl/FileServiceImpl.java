package com.bimface.example.quick.service.impl;


import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.example.quick.dao.mapper.ExampleQuickFileMapper;
import com.bimface.example.quick.dao.model.ExampleQuickFile;
import com.bimface.example.quick.service.FileService;
import com.bimface.example.quick.util.DateTimeUtils;
import com.bimface.exception.BimfaceException;
import com.bimface.file.bean.FileBean;
import com.bimface.sdk.BimfaceClient;
import com.bimface.sdk.bean.request.OfflineDatabagRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@Service("fileService")
public class FileServiceImpl implements FileService{
    @Autowired
    private BimfaceClient bimfaceClient;
    @Autowired
    private ExampleQuickFileMapper exampleQuickFileMapper;

    @Override
    public List<ExampleQuickFile> getFileList(String suffix, String translateStatus) {
        Example example = new Example(ExampleQuickFile.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.hasText(suffix)){
            criteria.andLike("name", "%." + suffix);
        }
        if (StringUtils.hasText(translateStatus)){
            criteria.andEqualTo("translateStatus", translateStatus);
        }
        example.orderBy("createTime").desc();
        return exampleQuickFileMapper.selectByExample(example);
    }

    @Override
    public ExampleQuickFile store(FileBean fileBean) throws ParseException {
        if (fileBean.getStatus().equals("success")) {
            ExampleQuickFile exampleQuickFile = new ExampleQuickFile();
            exampleQuickFile.setId(fileBean.getFileId());
            exampleQuickFile.setName(fileBean.getName());
            exampleQuickFile.setLength(fileBean.getLength());
            exampleQuickFile.setCreateTime(DateTimeUtils.parseBimfaceDateStr(fileBean.getCreateTime()));
            exampleQuickFileMapper.insert(exampleQuickFile);
            return exampleQuickFile;
        } else {
            throw new RuntimeException("file has not upload success");
        }
    }

    @Override
    public FileTranslateBean translate(Long fileId) throws BimfaceException {
        FileTranslateBean translateBean = bimfaceClient.translate(fileId);

        ExampleQuickFile exampleQuickFile = exampleQuickFileMapper.selectByPrimaryKey(fileId);
        if(!Objects.equals(exampleQuickFile.getTranslateStatus(),translateBean.getStatus())){
            exampleQuickFile.setTranslateStatus(translateBean.getStatus());
            exampleQuickFileMapper.updateByPrimaryKey(exampleQuickFile);
        }
        return translateBean;
    }

    @Override
    public DatabagDerivativeBean databag(Long fileId) throws BimfaceException {
        OfflineDatabagRequest request = new OfflineDatabagRequest();
        request.setFileId(fileId);
        DatabagDerivativeBean offlineDatabagBean = bimfaceClient.generateOfflineDatabag(request);
        ExampleQuickFile exampleQuickFile = exampleQuickFileMapper.selectByPrimaryKey(fileId);
        if(!Objects.equals(exampleQuickFile.getDatabagStatus(),offlineDatabagBean.getStatus())){
            exampleQuickFile.setDatabagStatus(offlineDatabagBean.getStatus());
            exampleQuickFileMapper.updateByPrimaryKey(exampleQuickFile);
        }
        return offlineDatabagBean;
    }

    @Override
    public void delete(Long fileId) {
        exampleQuickFileMapper.deleteByPrimaryKey(fileId);
    }
}
