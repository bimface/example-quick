package com.bimface.example.quick.service.impl;

import com.bimface.example.quick.dao.mapper.ExampleQuickFileMapper;
import com.bimface.example.quick.dao.mapper.ExampleQuickIntegrateFileMapper;
import com.bimface.example.quick.dao.mapper.ExampleQuickIntegrateMapper;
import com.bimface.example.quick.dao.model.ExampleQuickFile;
import com.bimface.example.quick.dao.model.ExampleQuickIntegrate;
import com.bimface.example.quick.dao.model.ExampleQuickIntegrateFile;
import com.bimface.example.quick.service.ClearDbService;
import com.bimface.example.quick.util.DateTimeUtils;
import com.bimface.example.quick.util.IdGenerator;
import com.bimface.sdk.BimfaceClient;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.bean.response.IntegrateBean;
import com.bimface.sdk.bean.response.TranslateBean;
import com.bimface.sdk.exception.BimfaceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service("clearDbService")
public class ClearDbServiceImpl implements ClearDbService {
    @Autowired
    private BimfaceClient bimfaceClient;
    @Autowired
    private ExampleQuickFileMapper exampleQuickFileMapper;
    @Autowired
    private ExampleQuickIntegrateMapper exampleQuickIntegrateMapper;
    @Autowired
    private ExampleQuickIntegrateFileMapper exampleQuickIntegrateFileMapper;

    @Value("${init.files}")
    private long[] initFiles;
    @Value("${init.integrates}")
    private long[] initIntegrates;
    @Value("${init.integrate.files}")
    private String initIntegrateFiles;
    private HashMap<Long, List<Long>> initIntegrateFileMap;

    private List<ExampleQuickFile> exampleQuickFiles;
    private List<ExampleQuickIntegrate> exampleQuickIntegrates;
    private List<ExampleQuickIntegrateFile> exampleQuickIntegrateFiles;

    @PostConstruct
    public void init() throws BimfaceException, ParseException {
        initIntegrateFileMap = getInitIntegrateFileMap();
        exampleQuickFiles = getInitFiles();
        exampleQuickIntegrates = getIntegrates();
        exampleQuickIntegrateFiles = getIntegrateFiles();
    }

    private HashMap<Long, List<Long>> getInitIntegrateFileMap() {
        HashMap<Long, List<Long>> map = new HashMap<>();
        for (String entryString : initIntegrateFiles.split(";")) {
            String[] entryStrings = entryString.split(":");
            Long integrateId = Long.parseLong(entryStrings[0]);
            List<Long> integrateFiles = Arrays.stream(entryStrings[1].split(",")).map(Long::parseLong).collect(Collectors.toList());
            map.put(integrateId, integrateFiles);
        }
        return map;
    }

    @Override
    @Transactional
    public void clearFile() {
        exampleQuickFileMapper.delete(null);
        exampleQuickFileMapper.insertFiles(exampleQuickFiles);
    }

    @Override
    @Transactional
    public void clearIntegrate() {
        exampleQuickIntegrateMapper.delete(null);
        exampleQuickIntegrateMapper.insertIntegrates(exampleQuickIntegrates);
    }

    @Override
    @Transactional
    public void clearIntegrateFile() {
        exampleQuickIntegrateFileMapper.delete(null);
        exampleQuickIntegrateFileMapper.insertIntegrateFiles(exampleQuickIntegrateFiles);
    }

    private List<ExampleQuickFile> getInitFiles() throws BimfaceException, ParseException {
        List<ExampleQuickFile> exampleQuickFiles = new ArrayList<>(initFiles.length);
        for (long fileId : initFiles) {
            FileBean fileBean = bimfaceClient.getFileMetadata(fileId);
            TranslateBean translateBean = bimfaceClient.getTranslate(fileId);
            ExampleQuickFile exampleQuickFile = new ExampleQuickFile();
            exampleQuickFile.setId(fileId);
            exampleQuickFile.setName(fileBean.getName());
            exampleQuickFile.setLength(fileBean.getLength());
            exampleQuickFile.setTranslateStatus(translateBean.getStatus());
            exampleQuickFile.setCreateTime(DateTimeUtils.parseBimfaceDateStr(fileBean.getCreateTime()));
            exampleQuickFiles.add(exampleQuickFile);
        }
        return exampleQuickFiles;
    }

    private List<ExampleQuickIntegrate> getIntegrates() throws BimfaceException, ParseException {
        List<ExampleQuickIntegrate> exampleQuickIntegrates = new ArrayList<>(initIntegrates.length);
        for (long integrateId : initIntegrates) {
            IntegrateBean integrateBean = bimfaceClient.getIntegrate(integrateId);
            ExampleQuickIntegrate integrate = new ExampleQuickIntegrate();
            integrate.setId(integrateBean.getIntegrateId());
            integrate.setName(integrateBean.getName());
            integrate.setFileNum(initIntegrateFileMap.get(integrateId).size());
            integrate.setIntegrateStatus(integrateBean.getStatus());
            integrate.setCreateTime(DateTimeUtils.parseBimfaceDateStr(integrateBean.getCreateTime()));
            exampleQuickIntegrates.add(integrate);
        }
        return exampleQuickIntegrates;
    }

    private List<ExampleQuickIntegrateFile> getIntegrateFiles() throws BimfaceException {
        List<ExampleQuickIntegrateFile> integrateFiles = new ArrayList<>();
        for (Map.Entry<Long, List<Long>> entry : initIntegrateFileMap.entrySet()) {
            long integrateId = entry.getKey();
            for (long fileId : entry.getValue()) {
                FileBean fileBean = bimfaceClient.getFileMetadata(fileId);
                ExampleQuickIntegrateFile integrateFile = new ExampleQuickIntegrateFile();
                integrateFile.setId(IdGenerator.nextId());
                integrateFile.setIntegrateId(integrateId);
                integrateFile.setFileId(fileId);
                integrateFile.setFileName(fileBean.getName());
                String[] specialtys = {"结构", "装修", "机电"};
                int index = new Random().nextInt(specialtys.length);
                integrateFile.setSpecialty(specialtys[index]);
                integrateFile.setFloor("" + (new Random().nextInt(5) + 1));
                integrateFiles.add(integrateFile);
            }
        }
        return integrateFiles;
    }

}
