package com.ttm.pet.dao;

import com.ttm.pet.model.dto.VerificationCode;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttm.pet.model.vo.app.VerificationCodeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 验证码表 Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-04-17
 */
public interface VerificationCodeMapper extends BaseMapper<VerificationCode> {

    public List<VerificationCodeVo> queryVerificationCodes(@Param("phoneNum") String phoneNum,@Param("type") Integer type);

}
