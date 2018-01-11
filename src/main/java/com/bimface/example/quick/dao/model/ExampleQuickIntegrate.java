package com.bimface.example.quick.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "example_quick_integrate")
public class ExampleQuickIntegrate {
    /**
     * integrate id
     */
    @Id
    private Long id;

    /**
     * 集成项目名
     */
    private String name;

    /**
     * 集成文件数目
     */
    @Column(name = "file_num")
    private Integer fileNum;

    /**
     * 集成状态
     */
    @Column(name = "integrate_status")
    private String integrateStatus;

    /**
     * 离线包状态
     */
    @Column(name = "databag_status")
    private String databagStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取integrate id
     *
     * @return id - integrate id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置integrate id
     *
     * @param id integrate id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取集成项目名
     *
     * @return name - 集成项目名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置集成项目名
     *
     * @param name 集成项目名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取集成文件数目
     *
     * @return file_num - 集成文件数目
     */
    public Integer getFileNum() {
        return fileNum;
    }

    /**
     * 设置集成文件数目
     *
     * @param fileNum 集成文件数目
     */
    public void setFileNum(Integer fileNum) {
        this.fileNum = fileNum;
    }

    /**
     * 获取集成状态
     *
     * @return integrate_status - 集成状态
     */
    public String getIntegrateStatus() {
        return integrateStatus;
    }

    /**
     * 设置集成状态
     *
     * @param integrateStatus 集成状态
     */
    public void setIntegrateStatus(String integrateStatus) {
        this.integrateStatus = integrateStatus;
    }

    /**
     * 获取离线包状态
     *
     * @return databag_status - 离线包状态
     */
    public String getDatabagStatus() {
        return databagStatus;
    }

    /**
     * 设置离线包状态
     *
     * @param databagStatus 离线包状态
     */
    public void setDatabagStatus(String databagStatus) {
        this.databagStatus = databagStatus;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}