package com.xiaxia.mallchat.common.user.dao;

import com.xiaxia.mallchat.common.user.domain.entity.User;
import com.xiaxia.mallchat.common.user.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xfl
 * @since 2023-11-28
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> {

    public User getByOpenId(String openId) {
        return lambdaQuery().eq(User::getOpenId, openId).one();
    }

    public User getName(String name) {
        return lambdaQuery().eq(User::getName, name).one();
    }

    public void modifyName(Long uid, String name) {
        lambdaUpdate().eq(User::getId, uid).set(User::getName, name).update();
    }

    public void wearingBadges(Long uid, Long badgeId) {
        lambdaUpdate().eq(User::getId, uid).set(User::getItemId, badgeId).update();
    }
}
