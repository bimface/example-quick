package com.bimface.example.quick.dao.mapper;

import com.bimface.example.quick.dao.model.ExampleQuickIntegrate;
import com.bimface.example.quick.util.MyMapper;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface ExampleQuickIntegrateMapper extends MyMapper<ExampleQuickIntegrate> {

    /**
     * 批量插入
     *
     * @param integrates
     * @return
     */
    @Insert("<script>" +
            "insert into example_quick_integrate (id, name,file_num,integrate_status,databag_status,create_time) values " +
            "<foreach collection=\"list\" item=\"integrate\" separator=\",\" >" +
            "(#{integrate.id}, #{integrate.name}, #{integrate.fileNum}, #{integrate.integrateStatus}, #{integrate.databagStatus}, #{integrate.createTime})" +
            "</foreach>" +
            "</script>")
    int insertIntegrates(List<ExampleQuickIntegrate> integrates);
}