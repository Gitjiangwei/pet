package com.ttm.pet.service.impl;

import com.ttm.pet.model.dto.VerificationCode;
import com.ttm.pet.dao.VerificationCodeMapper;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.vo.app.VerificationCodeVo;
import com.ttm.pet.service.VerificationCodeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 验证码表 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-04-17
 */
@Service
public class VerificationCodeServiceImpl extends ServiceImpl<VerificationCodeMapper, VerificationCode> implements VerificationCodeService {

    @Autowired
    private VerificationCodeMapper verificationCodeMapper;

    @Override
    public DataResult findVerificationCodeVos(String phoneNum, int code, int type) {
        List<VerificationCodeVo> verificationCodeVos = verificationCodeMapper.queryVerificationCodes(phoneNum,type);

        if(verificationCodeVos.size() == 0){
            return new DataResult(201, "请先发送验证码",null);
        }
        VerificationCodeVo verificationCodeVo = verificationCodeVos.get(0);
        if(code != verificationCodeVo.getCode()){
            return new DataResult(201, "验证码错误",null);
        }
        if(0 == verificationCodeVo.getCt()){
            return new DataResult(201, "验证码过期",null);
        }
        return null;
    }
}
