package com.example.entity;

/**
 * 省份实体类
 */
public class Provinces {

    /** 省份ID */
    private Integer provincesId;
    /** 省份名称 */
    private String provincesName;

    // 生成所有字段的getter和setter方法

    public Integer getProvincesId() {
        return provincesId;
    }

    public void setProvincesId(Integer provincesId) {
        this.provincesId = provincesId;
    }

    public String getProvincesName() {
        return provincesName;
    }

    public void setProvincesName(String provincesName) {
        this.provincesName = provincesName;
    }
}
