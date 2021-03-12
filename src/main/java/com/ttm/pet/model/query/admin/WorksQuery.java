package com.ttm.pet.model.query.admin;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

public class WorksQuery {
    @ApiModelProperty(value = "用户uuid", required = true)
    private String customerId;
    @ApiModelProperty(value = "作品标题", required = false)
    private String title;
    @ApiModelProperty(value = "首图地址", required = false)
    private String firstImg;
    @ApiModelProperty(value = "作品内容地址", required = false)
    private String content;
    @ApiModelProperty(value = "作品内容文字描述", required = false)
    private String describe;
    @ApiModelProperty(value = "纬度", required = false)
    private BigDecimal pX;
    @ApiModelProperty(value = "经度", required = false)
    private BigDecimal pY;
    @ApiModelProperty(value = "是否参加比赛，1-参加", required = false , example = "1")
    private int isMatch;
    @ApiModelProperty(value = "城市名字", required = false )
    private String location;
    @ApiModelProperty(value = "作品类型，1-图片类型 2-视频类型", required = true )
    private Integer type;
    @ApiModelProperty(value = "城市id", required = false )
    private Integer cityId;


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public BigDecimal getpX() {
        return pX;
    }

    public void setpX(BigDecimal pX) {
        this.pX = pX;
    }

    public BigDecimal getpY() {
        return pY;
    }

    public void setpY(BigDecimal pY) {
        this.pY = pY;
    }

    public int getIsMatch() {
        return isMatch;
    }

    public void setIsMatch(int isMatch) {
        this.isMatch = isMatch;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
