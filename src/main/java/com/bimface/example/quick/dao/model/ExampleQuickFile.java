package com.bimface.example.quick.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "example_quick_file")
public class ExampleQuickFile {
    /**
     * file id
     */
    @Id
    private Long id;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件大小
     */
    private Long length;

    /**
     * 转换状态
     */
    @Column(name = "translate_status")
    private String translateStatus;

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
     * 获取file id
     *
     * @return id - file id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置file id
     *
     * @param id file id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取文件名
     *
     * @return name - 文件名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置文件名
     *
     * @param name 文件名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取文件大小
     *
     * @return length - 文件大小
     */
    public Long getLength() {
        return length;
    }

    /**
     * 设置文件大小
     *
     * @param length 文件大小
     */
    public void setLength(Long length) {
        this.length = length;
    }

    /**
     * 获取转换状态
     *
     * @return translate_status - 转换状态
     */
    public String getTranslateStatus() {
        return translateStatus;
    }

    /**
     * 设置转换状态
     *
     * @param translateStatus 转换状态
     */
    public void setTranslateStatus(String translateStatus) {
        this.translateStatus = translateStatus;
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