package com.bimface.example.quick.dao.mapper;

import com.bimface.example.quick.dao.model.ExampleQuickIntegrateFile;
import com.bimface.example.quick.util.MyMapper;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface ExampleQuickIntegrateFileMapper extends MyMapper<ExampleQuickIntegrateFile> {
    /**
     * 批量插入
     *
     * @param integrateFiles
     * @return
     */
    @Insert("<script>" +
            "insert into example_quick_integrate_file (id,integrate_id,file_id,file_name,specialty,floor) values " +
            "<foreach collection=\"list\" item=\"integrateFile\" separator=\",\" >" +
            "(#{integrateFile.id}, #{integrateFile.integrateId}, #{integrateFile.fileId}, #{integrateFile.fileName}, #{integrateFile.specialty}, #{integrateFile.floor})" +
            "</foreach>" +
            "</script>")
    int insertIntegrateFiles(List<ExampleQuickIntegrateFile> integrateFiles);
}