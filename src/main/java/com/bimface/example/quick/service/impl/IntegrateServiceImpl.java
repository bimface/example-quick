package com.bimface.example.quick.service.impl;

import com.bimface.example.quick.dao.mapper.ExampleQuickIntegrateFileMapper;
import com.bimface.example.quick.dao.mapper.ExampleQuickIntegrateMapper;
import com.bimface.example.quick.dao.model.ExampleQuickIntegrate;
import com.bimface.example.quick.dao.model.ExampleQuickIntegrateFile;
import com.bimface.example.quick.service.IntegrateService;
import com.bimface.example.quick.util.DateTimeUtils;
import com.bimface.example.quick.util.IdGenerator;
import com.bimface.sdk.BimfaceClient;
import com.bimface.sdk.bean.request.OfflineDatabagRequest;
import com.bimface.sdk.bean.request.integrate.IntegrateRequest;
import com.bimface.sdk.bean.request.integrate.IntegrateSource;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.bean.response.IntegrateBean;
import com.bimface.sdk.bean.response.OfflineDatabagBean;
import com.bimface.sdk.exception.BimfaceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("integrateService")
public class IntegrateServiceImpl implements IntegrateService{
    @Autowired
    private BimfaceClient bimfaceClient;
    @Autowired
    private ExampleQuickIntegrateMapper exampleQuickIntegrateMapper;
    @Autowired
    private ExampleQuickIntegrateFileMapper exampleQuickIntegrateFileMapper;

    @Override
    public List<ExampleQuickIntegrate> getAll() {
        Example example = new Example(ExampleQuickIntegrate.class);
        example.orderBy("createTime").desc();
        return exampleQuickIntegrateMapper.selectByExample(example);
    }

    @Override
    public List<ExampleQuickIntegrateFile> getIntegrateFiles(Long integrateId) {
        Example example = new Example(ExampleQuickIntegrateFile.class);
        example.createCriteria().andEqualTo("integrateId", integrateId);
        return exampleQuickIntegrateFileMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public ExampleQuickIntegrate integrate(IntegrateRequest integrateRequest) throws BimfaceException, ParseException {
        IntegrateBean integrateBean = bimfaceClient.integrate(integrateRequest);

        ExampleQuickIntegrate integrate = new ExampleQuickIntegrate();
        integrate.setId(integrateBean.getIntegrateId());
        integrate.setName(integrateBean.getName());
        integrate.setFileNum(integrateRequest.getSources().size());
        integrate.setIntegrateStatus(integrateBean.getStatus());
        integrate.setCreateTime(DateTimeUtils.parseBimfaceDateStr(integrateBean.getCreateTime()));

        List<ExampleQuickIntegrateFile> integrateFiles = new ArrayList<>();
        for (IntegrateSource integrateSource : integrateRequest.getSources()){
            ExampleQuickIntegrateFile integrateFile = new ExampleQuickIntegrateFile();
            integrateFile.setId(IdGenerator.nextId());
            integrateFile.setIntegrateId(integrate.getId());
            integrateFile.setFileId(integrateSource.getFileId());
            integrateFile.setSpecialty(integrateSource.getSpecialty());
            integrateFile.setSpecialtySort(integrateSource.getSpecialtySort());
            integrateFile.setFloor(integrateSource.getFloor());
            integrateFile.setFloorSort(integrateSource.getFloorSort());

            FileBean fileBean = bimfaceClient.getFileMetadata(integrateFile.getFileId());
            integrateFile.setFileName(fileBean.getName());
            integrateFiles.add(integrateFile);
        }
        exampleQuickIntegrateMapper.insert(integrate);
        exampleQuickIntegrateFileMapper.insertIntegrateFiles(integrateFiles);
        return integrate;
    }

    @Override
    public OfflineDatabagBean databag(Long integrateId) throws BimfaceException {
        OfflineDatabagRequest request = new OfflineDatabagRequest();
        request.setIntegrateId(integrateId.toString());
        OfflineDatabagBean offlineDatabagBean = bimfaceClient.generateOfflineDatabag(request);

        ExampleQuickIntegrate exampleQuickIntegrate = exampleQuickIntegrateMapper.selectByPrimaryKey(integrateId);
        if(!Objects.equals(exampleQuickIntegrate.getDatabagStatus(),offlineDatabagBean.getStatus())){
            exampleQuickIntegrate.setDatabagStatus(offlineDatabagBean.getStatus());
            exampleQuickIntegrateMapper.updateByPrimaryKey(exampleQuickIntegrate);
        }

        return offlineDatabagBean;
    }

    @Override
    public void delete(Long integrateId) {
        exampleQuickIntegrateMapper.deleteByPrimaryKey(integrateId);
    }
}
