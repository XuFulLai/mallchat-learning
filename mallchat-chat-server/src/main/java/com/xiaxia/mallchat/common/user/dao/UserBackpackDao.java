package com.xiaxia.mallchat.common.user.dao;

import com.xiaxia.mallchat.common.common.domain.enums.YesOrNoEnum;
import com.xiaxia.mallchat.common.user.domain.entity.UserBackpack;
import com.xiaxia.mallchat.common.user.mapper.UserBackpackMapper;
import com.xiaxia.mallchat.common.user.service.IUserBackpackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *
 * @author xfl
 * @since 2024-01-21
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> {

    public Integer getCountByValidItemId(Long uid, Long itemId) {
        return lambdaQuery().eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId).
                eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus()).count();
    }

    public UserBackpack getFirstValidItem(Long uid, Long itemId) {
        return lambdaQuery().eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId)
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .orderByAsc(UserBackpack::getCreateTime).last("limit 1")
                .one();
    }

    public boolean useItem(UserBackpack modifyNameItem) {
        return lambdaUpdate().eq(UserBackpack::getId, modifyNameItem.getId())
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .set(UserBackpack::getStatus, YesOrNoEnum.YES.getStatus())
                .update();
    }

    public List<UserBackpack> getByItemId(Long uid, List<Long> itemId) {
        return lambdaQuery().eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .in(UserBackpack::getId, itemId).list();
    }
}
