package com.bimface.example.quick.controller;

import com.bimface.example.quick.dao.model.ExampleQuickFile;
import com.bimface.example.quick.service.FileService;
import com.bimface.sdk.BimfaceClient;
import com.bimface.sdk.bean.request.OfflineDatabagRequest;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.bean.response.OfflineDatabagBean;
import com.bimface.sdk.bean.response.TranslateBean;
import com.bimface.sdk.bean.response.UploadPolicyBean;
import com.bimface.sdk.exception.BimfaceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dup, 2017-12-20
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private BimfaceClient bimfaceClient;

    @GetMapping("/list")
    public List<ExampleQuickFile> getFileList(@RequestParam(required = false) String suffix,
                                              @RequestParam(required = false) String translateStatus) {
        return fileService.getFileList(suffix, translateStatus);
    }

    @GetMapping("/policy")
    public UploadPolicyBean getOssPolicy(@RequestParam String fileName) throws BimfaceException {
        return bimfaceClient.getPolicy(fileName);
    }

    @GetMapping("/download_url")
    public String getDownloadUrl(@RequestParam Long fileId) throws BimfaceException {
        return bimfaceClient.getDownloadUrl(fileId);
    }

    @PostMapping("")
    public ExampleQuickFile storeFile(FileBean fileBean) throws ParseException {
        return fileService.store(fileBean);
    }

    @GetMapping("/view_token")
    public String getViewToken(@RequestParam Long fileId) throws BimfaceException {
        return bimfaceClient.getViewTokenByFileId(fileId);
    }

    @PutMapping("translate")
    public TranslateBean translate(@RequestParam Long fileId) throws BimfaceException {
        return fileService.translate(fileId);
    }

    @PutMapping("translate/list")
    public List<TranslateBean> translateFiles(@RequestParam Long[] fileIds) throws BimfaceException {
        List<TranslateBean> translateBeans = new ArrayList<>();
        for (Long fileId : fileIds) {
            translateBeans.add(translate(fileId));
        }
        return translateBeans;
    }

    @PutMapping("/databag")
    public OfflineDatabagBean databag(@RequestParam Long fileId) throws BimfaceException {
        return fileService.databag(fileId);
    }

    @PutMapping("/databag/list")
    public List<OfflineDatabagBean> databags(@RequestParam Long[] fileIds) throws BimfaceException {
        List<OfflineDatabagBean> offlineDatabagBeans = new ArrayList<>();
        for (Long fileId : fileIds) {
            offlineDatabagBeans.add(databag(fileId));
        }
        return offlineDatabagBeans;
    }

    @GetMapping("/databag_url")
    public String databagUrl(@RequestParam String fileId) throws BimfaceException {
        OfflineDatabagRequest request = new OfflineDatabagRequest();
        request.setFileId(fileId);
        return bimfaceClient.getOfflineDatabagUrl(request);
    }

    @DeleteMapping("")
    public String delete(@RequestParam Long fileId) throws BimfaceException {
        fileService.delete(fileId);
        return "success";
    }

    @DeleteMapping("/list")
    public String deletes(@RequestParam Long[] fileIds) throws BimfaceException {
        for (Long fileId:fileIds){
            fileService.delete(fileId);
        }
        return "success";
    }
}
