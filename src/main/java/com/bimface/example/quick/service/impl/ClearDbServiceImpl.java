package com.bimface.example.quick.service.impl;

import com.bimface.api.bean.response.FileIntegrateBean;
import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.example.quick.dao.mapper.ExampleQuickFileMapper;
import com.bimface.example.quick.dao.mapper.ExampleQuickIntegrateFileMapper;
import com.bimface.example.quick.dao.mapper.ExampleQuickIntegrateMapper;
import com.bimface.example.quick.dao.model.ExampleQuickFile;
import com.bimface.example.quick.dao.model.ExampleQuickIntegrate;
import com.bimface.example.quick.dao.model.ExampleQuickIntegrateFile;
import com.bimface.example.quick.service.ClearDbService;
import com.bimface.example.quick.util.DateTimeUtils;
import com.bimface.example.quick.util.IdGenerator;
import com.bimface.exception.BimfaceException;
import com.bimface.file.bean.FileBean;
import com.bimface.sdk.BimfaceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

    @Value("${init.files:}")
    private long[] initFiles;
    @Value("${init.integrates:}")
    private long[] initIntegrates;
    @Value("${init.integrate.files:}")
    private String initIntegrateFiles;
    private Map<Long, List<Long>> initIntegrateFileMap;

    private List<ExampleQuickFile> exampleQuickFiles;
    private List<ExampleQuickIntegrate> exampleQuickIntegrates;
    private List<ExampleQuickIntegrateFile> exampleQuickIntegrateFiles;

    @PostConstruct
    public void init() throws BimfaceException, ParseException {
        exampleQuickFiles = getInitFiles();
        initIntegrateFileMap = getInitIntegrateFileMap();
        exampleQuickIntegrates = getIntegrates();
        exampleQuickIntegrateFiles = getIntegrateFiles();
    }

    private Map<Long, List<Long>> getInitIntegrateFileMap() {
        if (StringUtils.isEmpty(initIntegrateFiles)) {
            return Collections.emptyMap();
        }
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
    @Transactional(rollbackFor = DataAccessException.class)
    public void clearFile() {
        exampleQuickFileMapper.delete(null);
        if (!CollectionUtils.isEmpty(exampleQuickFiles)) {
            exampleQuickFileMapper.insertFiles(exampleQuickFiles);
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public void clearIntegrate() {
        exampleQuickIntegrateMapper.delete(null);
        if (!CollectionUtils.isEmpty(exampleQuickIntegrates)) {
            exampleQuickIntegrateMapper.insertIntegrates(exampleQuickIntegrates);
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public void clearIntegrateFile() {
        exampleQuickIntegrateFileMapper.delete(null);
        if (!CollectionUtils.isEmpty(exampleQuickIntegrateFiles)) {
            exampleQuickIntegrateFileMapper.insertIntegrateFiles(exampleQuickIntegrateFiles);
        }
    }

    private List<ExampleQuickFile> getInitFiles() throws ParseException, BimfaceException {
        if (initFiles.length == 0) {
            return Collections.emptyList();
        }
        List<ExampleQuickFile> exampleQuickFiles = new ArrayList<>(initFiles.length);
        for (long fileId : initFiles) {
            FileBean fileBean = bimfaceClient.getFile(fileId);
            FileTranslateBean translateBean = bimfaceClient.getTranslate(fileId);
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
        if (initIntegrates.length == 0) {
            return Collections.emptyList();
        }
        List<ExampleQuickIntegrate> exampleQuickIntegrates = new ArrayList<>(initIntegrates.length);
        for (long integrateId : initIntegrates) {
            FileIntegrateBean integrateBean = bimfaceClient.getIntegrate(integrateId);
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
        if (CollectionUtils.isEmpty(initIntegrateFileMap)) {
            return Collections.emptyList();
        }
        List<ExampleQuickIntegrateFile> integrateFiles = new ArrayList<>();
        for (Map.Entry<Long, List<Long>> entry : initIntegrateFileMap.entrySet()) {
            long integrateId = entry.getKey();
            for (long fileId : entry.getValue()) {
                FileBean fileBean = bimfaceClient.getFile(fileId);
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
