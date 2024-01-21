package com.xiaxia.mallchat.common.user.service.Impl;

import com.xiaxia.mallchat.common.common.exception.BusinessException;
import com.xiaxia.mallchat.common.common.util.AssertUtil;
import com.xiaxia.mallchat.common.user.dao.UserBackpackDao;
import com.xiaxia.mallchat.common.user.dao.UserDao;
import com.xiaxia.mallchat.common.user.domain.dto.ItemInfoDTO;
import com.xiaxia.mallchat.common.user.domain.dto.SummeryInfoDTO;
import com.xiaxia.mallchat.common.user.domain.entity.User;
import com.xiaxia.mallchat.common.user.domain.entity.UserBackpack;
import com.xiaxia.mallchat.common.user.domain.enums.ItemEnum;
import com.xiaxia.mallchat.common.user.domain.vo.request.user.*;
import com.xiaxia.mallchat.common.user.domain.vo.response.user.BadgeResp;
import com.xiaxia.mallchat.common.user.domain.vo.response.user.UserInfoResp;
import com.xiaxia.mallchat.common.user.service.IUserService;
import com.xiaxia.mallchat.common.user.service.UserService;
import com.xiaxia.mallchat.common.user.service.adapter.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author xfl
 * @date 2023/12/14 22:11
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserBackpackDao userBackpackDao;

    @Override
    public UserInfoResp getUserInfo(Long uid) {
        User user = userDao.getById(uid);
        Integer modifyNameCount = userBackpackDao.getCountByValidItemId(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        return UserAdapter.buildUserInfo(user, modifyNameCount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyName(Long uid, ModifyNameReq req) {
        //先校验是否还有改名卡
        UserBackpack modifyNameItem = userBackpackDao.getFirstValidItem(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        AssertUtil.isNotEmpty(modifyNameItem, "改名卡不足！");
        //校验是否重名
        User oldUser = userDao.getName(req.getName());
        AssertUtil.isEmpty(oldUser, "用户名已存在");
        boolean success = userBackpackDao.useItem(modifyNameItem);
        if (success) {
            // 用户改名事件
            userDao.modifyName(uid, req.getName());
        }
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
