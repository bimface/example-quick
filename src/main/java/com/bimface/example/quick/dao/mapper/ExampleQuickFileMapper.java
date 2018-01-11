package com.bimface.example.quick.dao.mapper;

import com.bimface.example.quick.dao.model.ExampleQuickFile;
import com.bimface.example.quick.util.MyMapper;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface ExampleQuickFileMapper extends MyMapper<ExampleQuickFile> {

    /**
     * 批量插入
     *
     * @param files
     * @return
     */
    @Insert("<script>" +
            "insert into example_quick_file (id, name,length,translate_status,databag_status,create_time) values " +
            "<foreach collection=\"list\" item=\"file\" separator=\",\" >" +
            "(#{file.id}, #{file.name}, #{file.length}, #{file.translateStatus}, #{file.databagStatus}, #{file.createTime})" +
            "</foreach>" +
            "</script>")
    int insertFiles(List<ExampleQuickFile> files);
}