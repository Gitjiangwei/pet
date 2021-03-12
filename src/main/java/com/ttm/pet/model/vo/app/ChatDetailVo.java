package com.ttm.pet.model.vo.app;

public class ChatDetailVo {

    private Long id;
    private String fromCustomerId;
    private String content;
    private String name;
    private String portrait;
    private Long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromCustomerId() {
        return fromCustomerId;
    }

    public void setFromCustomerId(String fromCustomerId) {
        this.fromCustomerId = fromCustomerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
