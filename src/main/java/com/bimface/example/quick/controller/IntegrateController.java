package com.bimface.example.quick.controller;

import com.bimface.example.quick.dao.model.ExampleQuickIntegrate;
import com.bimface.example.quick.dao.model.ExampleQuickIntegrateFile;
import com.bimface.example.quick.service.IntegrateService;
import com.bimface.sdk.BimfaceClient;
import com.bimface.sdk.bean.request.OfflineDatabagRequest;
import com.bimface.sdk.bean.request.integrate.IntegrateRequest;
import com.bimface.sdk.bean.response.OfflineDatabagBean;
import com.bimface.sdk.exception.BimfaceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("integrate")
public class IntegrateController {

    @Autowired
    private IntegrateService integrateService;
    @Autowired
    private BimfaceClient bimfaceClient;

    @GetMapping("/list")
    public List<ExampleQuickIntegrate> getIntegrateList() {
        return integrateService.getAll();
    }

    @GetMapping("/files")
    public List<ExampleQuickIntegrateFile> getIntegrateFileList(@RequestParam Long integrateId) {
        return integrateService.getIntegrateFiles(integrateId);
    }

    @PostMapping("")
    public ExampleQuickIntegrate integrate(@RequestBody IntegrateRequest integrateRequest) throws BimfaceException, ParseException {
        return integrateService.integrate(integrateRequest);
    }

    @GetMapping("/view_token")
    public String getViewToken(@RequestParam Long integrateId) throws BimfaceException {
        return bimfaceClient.getViewTokenByIntegrateId(integrateId);
    }

    @PutMapping("/databag")
    public OfflineDatabagBean databag(@RequestParam Long integrateId) throws BimfaceException {
        return integrateService.databag(integrateId);
    }

    @PutMapping("/databag/list")
    public List<OfflineDatabagBean> databags(@RequestParam Long[] integrateIds) throws BimfaceException {
        List<OfflineDatabagBean> offlineDatabagBeans = new ArrayList<>();
        for (Long integrateId : integrateIds) {
            offlineDatabagBeans.add(databag(integrateId));
        }
        return offlineDatabagBeans;
    }

    @GetMapping("/databag_url")
    public String databagUrl(@RequestParam String integrateId) throws BimfaceException {
        OfflineDatabagRequest request = new OfflineDatabagRequest();
        request.setIntegrateId(integrateId);
        return bimfaceClient.getOfflineDatabagUrl(request);
    }

    @DeleteMapping("")
    public String delete(@RequestParam Long integrateId) throws BimfaceException {
        integrateService.delete(integrateId);
        return "success";
    }

    @DeleteMapping("/list")
    public String deletes(@RequestParam Long[] integrateIds) throws BimfaceException {
        for (Long integrateId:integrateIds){
            integrateService.delete(integrateId);
        }
        return "success";
    }
}
