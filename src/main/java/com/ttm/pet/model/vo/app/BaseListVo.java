package com.ttm.pet.model.vo.app;

public class BaseListVo {
    public String customerId;

    public String portrait;

    public String name;

    public int donatedFood;

    private Integer likeTarget;

    private Integer id;

    private Integer buyFinished;
    private Integer giveFinished;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDonatedFood() {
        return donatedFood;
    }

    public void setDonatedFood(int donatedFood) {
        this.donatedFood = donatedFood;
    }

    public Integer getLikeTarget() {
        return likeTarget;
    }

    public void setLikeTarget(Integer likeTarget) {
        this.likeTarget = likeTarget;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuyFinished() {
        return buyFinished;
    }

    public void setBuyFinished(Integer buyFinished) {
        this.buyFinished = buyFinished;
    }

    public Integer getGiveFinished() {
        return giveFinished;
    }

    public void setGiveFinished(Integer giveFinished) {
        this.giveFinished = giveFinished;
    }
}
