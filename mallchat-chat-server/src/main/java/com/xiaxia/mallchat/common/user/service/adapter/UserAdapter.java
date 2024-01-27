package com.xiaxia.mallchat.common.user.service.adapter;

import com.xiaxia.mallchat.common.common.domain.enums.YesOrNoEnum;
import com.xiaxia.mallchat.common.user.domain.entity.ItemConfig;
import com.xiaxia.mallchat.common.user.domain.entity.User;
import com.xiaxia.mallchat.common.user.domain.entity.UserBackpack;
import com.xiaxia.mallchat.common.user.domain.vo.response.user.BadgeResp;
import com.xiaxia.mallchat.common.user.domain.vo.response.user.UserInfoResp;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.springframework.beans.BeanUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static UserInfoResp buildUserInfo(User user, Integer modifyNameCount) {
        UserInfoResp vo = new UserInfoResp();
        BeanUtils.copyProperties(user, vo);
        vo.setId(user.getId());
        vo.setModifyNameChance(modifyNameCount);
        return vo;
    }

    public static List<BadgeResp> buildBadgeResp(List<ItemConfig> itemConfigs, List<UserBackpack> backpacks, User user) {
        Set<Long> obtainItemSet = backpacks.stream().map(UserBackpack::getItemId).collect(Collectors.toSet());

        return itemConfigs.stream().map(a -> {
            BadgeResp resp = new BadgeResp();
            BeanUtils.copyProperties(a, resp);
            resp.setObtain(obtainItemSet.contains(a.getId()) ? YesOrNoEnum.YES.getStatus() : YesOrNoEnum.NO.getStatus());
            resp.setWearing(Objects.equals(a.getId(), user.getItemId()) ? YesOrNoEnum.YES.getStatus() : YesOrNoEnum.NO.getStatus());
            return resp;
        }).sorted(Comparator.comparing(BadgeResp::getWearing, Comparator.reverseOrder())
                .thenComparing(BadgeResp::getObtain, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
