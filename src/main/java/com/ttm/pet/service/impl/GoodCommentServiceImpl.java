package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.dao.GoodCommentMapper;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.model.query.app.GoodCommentQuery;
import com.ttm.pet.model.query.app.GoodCommentSave;
import com.ttm.pet.model.vo.app.CustomerCommentVo;
import com.ttm.pet.model.vo.app.GoodCommentVo;
import com.ttm.pet.service.GoodCommentService;
import com.ttm.pet.util.BadWordUtil2;
import com.ttm.pet.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 订单评论业务实现
 *
 * @author J
 * @date 2021/3/4
 */
@Service
public class GoodCommentServiceImpl implements GoodCommentService {
    /**
     * 订单评论持久层
     */
    @Autowired
    private GoodCommentMapper goodCommentMapper;

    @Override
    public DataResult saveGoodComment(GoodCommentSave goodCommentSave) {
        String content = goodCommentSave.getRemark();
        Set< String > set = BadWordUtil2.getBadWord(content, 2);
        if (set.size() > 0) {
            return new DataResult(ReturnStatusEnum.ILLEGAL_PARAM_ERROR.getValue(), "您发布的内容包含敏感词，请检查后发布!", null);
        }

        int count = goodCommentMapper.saveGoodComment(goodCommentSave);
        DataResult dataResult = new DataResult();
        if (count > 0) {
            Map< String, Object > map = new HashedMap(2);
            map.put("id", goodCommentSave.getId());
            map.put("createTime", DateUtil.date2long(new Date()));
            dataResult.setData(map);
        }
        else {
            dataResult.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            dataResult.setMsg("评论失败");
        }
        return dataResult;
    }

    @Override
    public ListDataResult listGoodComment(GoodCommentQuery goodCommentQuery) {
        Page< GoodCommentVo > commentVoPage = new Page<>(goodCommentQuery.getPageNo(), goodCommentQuery.getPageSize());
        if (goodCommentQuery.getParentId() == null){
            goodCommentQuery.setParentId(0L);
        }
        commentVoPage.setRecords(goodCommentMapper.listGoodComment(commentVoPage, goodCommentQuery));
        ListDataResult result = new ListDataResult();
        result.setCurrent(commentVoPage.getCurrent());
        result.setTotal(commentVoPage.getTotal());
        result.setData(commentVoPage.getRecords());
        return result;
    }

    @Override
    public DataResult deleteGoodComment(Long id) {
        goodCommentMapper.deleteGoodComment(id);
        DataResult dataResult = new DataResult();
        dataResult.setCode(ReturnStatusEnum.SUCCESS.getValue());
        dataResult.setMsg("删除成功");
        return dataResult;
    }
}
