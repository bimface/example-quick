package com.bimface.example.quick.enums;

/**
 * 转换状态
 * 
 * @author dup, 2017-12-27
 */
public enum DatabagStatus {

    /**
     * 准备中
     */
    PREPARE("prepare"),

    /**
     * 合并中
     */
    PROCESSING("processing"),

    /**
     * 合并成功
     */
    SUCCESS("success"),

    /**
     * 合并失败
     */
    FAILED("failed");

    private String name;

    DatabagStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
