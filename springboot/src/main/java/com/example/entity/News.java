package com.example.entity;

/**
 * 文章News实体类
 */
public class News {

    /** 文章ID */
    private Integer id;
    /** 文章标题 */
    private String title;
    /** 文章内容 */
    private String content;
    /** 发布时间 */
    private String time;
    /** 所属省份Id */
    private Integer provincesId;
    /** 所属省份名称 */
    private String provincesName;

    // 生成所有字段的getter和setter方法

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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
