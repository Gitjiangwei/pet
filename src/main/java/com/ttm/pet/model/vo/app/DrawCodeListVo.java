package com.ttm.pet.model.vo.app;

public class DrawCodeListVo {
    private Integer id;
    private String name;
    private String portrait;
    private String giftSimpleName;
    private Integer drawCode;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getGiftSimpleName() {
        return giftSimpleName;
    }

    public void setGiftSimpleName(String giftSimpleName) {
        this.giftSimpleName = giftSimpleName;
    }

    public Integer getDrawCode() {
        return drawCode;
    }

    public void setDrawCode(Integer drawCode) {
        this.drawCode = drawCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
