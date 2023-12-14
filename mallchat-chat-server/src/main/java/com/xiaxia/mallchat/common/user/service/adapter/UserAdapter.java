package com.xiaxia.mallchat.common.user.service.adapter;

import com.xiaxia.mallchat.common.user.domain.entity.User;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

/**
 * @author xfl
 * @date 2023/12/14 22:07
 */
public class UserAdapter {
    public static User buildUserSave(String openId) {
        return User.builder().openId(openId).build();
    }

    public static User buildAuthorizeUser(Long uid, WxOAuth2UserInfo userInfo) {
        User user = new User();
        user.setId(uid);
        user.setName(userInfo.getNickname());
        user.setAvatar(userInfo.getHeadImgUrl());
        user.setSex(userInfo.getSex());
        return user;
    }
}
