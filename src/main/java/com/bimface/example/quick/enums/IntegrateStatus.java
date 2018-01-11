package com.bimface.example.quick.enums;

/**
 * 模型合并状态
 *
 * @author dup, 2017-12-27
 */
public enum IntegrateStatus {
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

    IntegrateStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
