package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

public class RewardQuery {
    @ApiModelProperty(value = "用户id", required = true)
    private String customerId;
    @ApiModelProperty(value = "被打赏用户id", required = true)
    private String rewardedCustomerId;
    @ApiModelProperty(value = "作品id", required = true)
    private Long worksId;
    @ApiModelProperty(value = "打赏宠币个数", required = true)
    private Integer coin;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRewardedCustomerId() {
        return rewardedCustomerId;
    }

    public void setRewardedCustomerId(String rewardedCustomerId) {
        this.rewardedCustomerId = rewardedCustomerId;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }
}
