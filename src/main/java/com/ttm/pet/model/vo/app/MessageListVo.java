package com.ttm.pet.model.vo.app;

public class MessageListVo {
    private Integer id;
    private String portrait;
    private String title;
    private String content;
    private Long createTime;
    private Integer type;
    private String drawImg;
    private String drawUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDrawImg() {
        return drawImg;
    }

    public void setDrawImg(String drawImg) {
        this.drawImg = drawImg;
    }

    public String getDrawUrl() {
        return drawUrl;
    }

    public void setDrawUrl(String drawUrl) {
        this.drawUrl = drawUrl;
    }
}
