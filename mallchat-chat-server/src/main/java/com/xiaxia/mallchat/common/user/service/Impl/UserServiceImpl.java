package com.xiaxia.mallchat.common.user.service.Impl;

import com.xiaxia.mallchat.common.user.dao.UserDao;
import com.xiaxia.mallchat.common.user.domain.dto.ItemInfoDTO;
import com.xiaxia.mallchat.common.user.domain.dto.SummeryInfoDTO;
import com.xiaxia.mallchat.common.user.domain.entity.User;
import com.xiaxia.mallchat.common.user.domain.vo.request.user.*;
import com.xiaxia.mallchat.common.user.domain.vo.response.user.BadgeResp;
import com.xiaxia.mallchat.common.user.domain.vo.response.user.UserInfoResp;
import com.xiaxia.mallchat.common.user.service.IUserService;
import com.xiaxia.mallchat.common.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xfl
 * @date 2023/12/14 22:11
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserInfoResp getUserInfo(Long uid) {
        return null;
    }

    @Override
    public void modifyName(Long uid, ModifyNameReq req) {

    }

    @Override
    public List<BadgeResp> badges(Long uid) {
        return null;
    }

    @Override
    public void wearingBadge(Long uid, WearingBadgeReq req) {

    }

    @Override
    @Transactional
    public Long register(User user) {
        userDao.save(user);
        //todo 用户注册事件
        return user.getId();

    }

    @Override
    public void black(BlackReq req) {

    }

    @Override
    public List<SummeryInfoDTO> getSummeryUserInfo(SummeryInfoReq req) {
        return null;
    }

    @Override
    public List<ItemInfoDTO> getItemInfo(ItemInfoReq req) {
        return null;
    }
}
