package com.ttm.pet.model.vo.app;

public class BaseListH5Vo {
    public String customerId;
    public String portrait;
    public String name;
    public double ratio;
    private Integer target;
    private Integer subsidy;
    private Integer id;

    private Integer buyFinished;

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

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(Integer subsidy) {
        this.subsidy = subsidy;
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
}
