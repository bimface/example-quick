package com.bimface.example.quick.service.impl;

import com.bimface.api.bean.response.FileIntegrateBean;
import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.api.bean.response.databagDerivative.DatabagDerivativeBean;
import com.bimface.example.quick.dao.mapper.ExampleQuickFileMapper;
import com.bimface.example.quick.dao.mapper.ExampleQuickIntegrateMapper;
import com.bimface.example.quick.dao.model.ExampleQuickFile;
import com.bimface.example.quick.dao.model.ExampleQuickIntegrate;
import com.bimface.example.quick.enums.DatabagStatus;
import com.bimface.example.quick.enums.IntegrateStatus;
import com.bimface.example.quick.enums.TranslateStatus;
import com.bimface.example.quick.service.UpdateStatusService;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.BimfaceClient;
import com.bimface.sdk.bean.request.OfflineDatabagRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

@Service("updateStatusService")
public class UpdateStatusServiceImpl implements UpdateStatusService {
    @Autowired
    private BimfaceClient bimfaceClient;
    @Autowired
    private ExampleQuickFileMapper exampleQuickFileMapper;
    @Autowired
    private ExampleQuickIntegrateMapper exampleQuickIntegrateMapper;

    @Override
    public void uploadTranslateStatus() {
        List<ExampleQuickFile> files = exampleQuickFileMapper.selectByExample(getToUploadTranslateExample());
        for (ExampleQuickFile exampleQuickFile : files) {
            try {
                FileTranslateBean translateBean = bimfaceClient.getTranslate(exampleQuickFile.getId());
                if(!Objects.equals(exampleQuickFile.getTranslateStatus(),translateBean.getStatus())){
                    exampleQuickFile.setTranslateStatus(translateBean.getStatus());
                    exampleQuickFileMapper.updateByPrimaryKey(exampleQuickFile);
                }
            } catch (BimfaceException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void uploadIntegrateStatus() {
        List<ExampleQuickIntegrate> integrates = exampleQuickIntegrateMapper.selectByExample(getToUploadIntegrateExample());
        for (ExampleQuickIntegrate exampleQuickIntegrate : integrates) {
            try {
                FileIntegrateBean integrateBean = bimfaceClient.getIntegrate(exampleQuickIntegrate.getId());
                if(!Objects.equals(exampleQuickIntegrate.getIntegrateStatus(),integrateBean.getStatus())){
                    exampleQuickIntegrate.setIntegrateStatus(integrateBean.getStatus());
                    exampleQuickIntegrateMapper.updateByPrimaryKey(exampleQuickIntegrate);
                }
            } catch (BimfaceException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void uploadFileDatabagStatus() {
        List<ExampleQuickFile> files = exampleQuickFileMapper.selectByExample(getToUploadFileDatabagExample());
        for (ExampleQuickFile exampleQuickFile : files) {
            try {
                OfflineDatabagRequest request = new OfflineDatabagRequest();
                request.setFileId(exampleQuickFile.getId());
                List<? extends DatabagDerivativeBean> offlineDatabagBeans = bimfaceClient.queryOfflineDatabag(request);
                DatabagDerivativeBean databagDerivativeBean = offlineDatabagBeans.get(offlineDatabagBeans.size() - 1);
                if(!Objects.equals(exampleQuickFile.getDatabagStatus(),databagDerivativeBean.getStatus())){
                    exampleQuickFile.setDatabagStatus(databagDerivativeBean.getStatus());
                    exampleQuickFileMapper.updateByPrimaryKey(exampleQuickFile);
                }
            } catch (BimfaceException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void uploadIntegrateDatabagStatus() {
        List<ExampleQuickIntegrate> integrates = exampleQuickIntegrateMapper.selectByExample(getToUploadIntegrateDatabagExample());
        for (ExampleQuickIntegrate exampleQuickIntegrate : integrates) {
            try {
                OfflineDatabagRequest request = new OfflineDatabagRequest();
                request.setIntegrateId(exampleQuickIntegrate.getId());
                List<? extends DatabagDerivativeBean> offlineDatabagBeans = bimfaceClient.queryOfflineDatabag(request);
                DatabagDerivativeBean databagDerivativeBean = offlineDatabagBeans.get(offlineDatabagBeans.size() - 1);
                if(!Objects.equals(exampleQuickIntegrate.getDatabagStatus(),databagDerivativeBean.getStatus())){
                    exampleQuickIntegrate.setDatabagStatus(databagDerivativeBean.getStatus());
                    exampleQuickIntegrateMapper.updateByPrimaryKey(exampleQuickIntegrate);
                }
            } catch (BimfaceException e) {
                e.printStackTrace();
            }
        }
    }

    private Object getToUploadIntegrateExample() {
        Example example = new Example(ExampleQuickIntegrate.class);
        example.createCriteria().andEqualTo("integrateStatus",IntegrateStatus.PREPARE.getName());
        example.or().andEqualTo("integrateStatus",IntegrateStatus.PROCESSING.getName());
        example.setOrderByClause("create_time desc");
        return example;
    }


    private Example getToUploadTranslateExample() {
        Example example = new Example(ExampleQuickFile.class);
        example.createCriteria().andEqualTo("translateStatus",TranslateStatus.PREPARE.getName());
        example.or().andEqualTo("translateStatus",TranslateStatus.PROCESSING.getName());
        example.setOrderByClause("create_time desc");
        return example;
    }

    private Example getToUploadFileDatabagExample() {
        Example example = new Example(ExampleQuickFile.class);
        example.createCriteria().andEqualTo("databagStatus",DatabagStatus.PREPARE.getName());
        example.or().andEqualTo("databagStatus", DatabagStatus.PROCESSING.getName());
        example.setOrderByClause("create_time desc");
        return example;
    }

    private Object getToUploadIntegrateDatabagExample() {
        Example example = new Example(ExampleQuickIntegrate.class);
        example.createCriteria().andEqualTo("databagStatus",DatabagStatus.PREPARE.getName());
        example.or().andEqualTo("databagStatus",DatabagStatus.PROCESSING.getName());
        example.setOrderByClause("create_time desc");
        return example;
    }
}
