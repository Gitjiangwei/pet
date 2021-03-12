package com.ttm.pet.service;

import com.ttm.pet.model.dto.VerificationCode;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.pojo.DataResult;

/**
 * <p>
 * 验证码表 服务类
 * </p>
 *
 * @author cx
 * @since 2020-04-17
 */
public interface VerificationCodeService extends IService<VerificationCode> {

    /**
     * 判断验证码是否正确
     * @param phoneNum
     * @param code
     * @return
     */
    public DataResult findVerificationCodeVos(String phoneNum, int code, int type);
}
