package com.ttm.pet.enums;

public enum PathEnum {
    ALIYUN_HEAD_URL("https://oss.zglele.com/"),
    DEFAULT_HEAD("https://oss.zglele.com/defaultHead.jpg"),
    ADMIN_UPLOAD_PATH("pet_admin"),
    CUSTOMER_UPLOAD_PATH("pet_app");
//    ADMIN_UPLOAD_PATH("test"),
//    CUSTOMER_UPLOAD_PATH("test");

    private String path;

    private PathEnum(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
