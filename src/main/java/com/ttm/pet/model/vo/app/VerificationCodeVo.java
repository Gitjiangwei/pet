package com.ttm.pet.model.vo.app;

import com.ttm.pet.model.dto.VerificationCode;

public class VerificationCodeVo extends VerificationCode {
    private Integer ct;

    public Integer getCt() {
        return ct;
    }

    public void setCt(Integer ct) {
        this.ct = ct;
    }
}
