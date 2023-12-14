package com.xiaxia.mallchat.common.user.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.xiaxia.mallchat.common.user.dao.UserDao;
import com.xiaxia.mallchat.common.user.domain.entity.User;
import com.xiaxia.mallchat.common.user.service.UserService;
import com.xiaxia.mallchat.common.user.service.WXMsgService;
import com.xiaxia.mallchat.common.user.service.adapter.TextBuilder;
import com.xiaxia.mallchat.common.user.service.adapter.UserAdapter;
import com.xiaxia.mallchat.common.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.channels.Channel;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xfl
 * @date 2023/12/14 21:53
 */
@Service
@Slf4j
public class WXMsgServiceImpl implements WXMsgService {

    /**
     * openid和登录code的关系map
     */
    private static final ConcurrentHashMap<String, Integer> WAIT_AUTHORIZE_MAP = new ConcurrentHashMap<>();

    @Value("${wx.mp.callback}")
    private String callback;
    private static final String URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Lazy
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WebSocketService webSocketService;

    @Override
    public WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage) {
        String openId = wxMpXmlMessage.getFromUser();
        Integer code = getEventKey(wxMpXmlMessage);

        if (Objects.isNull(code)) {
            log.error("scan error code is null fromUser:{}", openId);
            return null;
        }
        User user = userDao.getByOpenId(openId);

        boolean registered = Objects.nonNull(user);
        boolean authorized = registered && StrUtil.isNotBlank(user.getAvatar());

        //用户已经注册并授权
        if (registered && authorized) {
            //todo 走登录成功逻辑 通过code找到给channel推送消息
        }
        //用户未注册，先走注册逻辑
        if (!registered) {
            User insert = UserAdapter.buildUserSave(openId);
            userService.register(insert);
        }

        // 推送连接让用户授权
        WAIT_AUTHORIZE_MAP.put(openId, code);
        webSocketService.waitAuthorize(code);
        String authorizeUrl = String.format(URL, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback + "/wx/portal/public/callBack"));
        return TextBuilder.build("请点击链接授权：<a href=\"" + authorizeUrl + "\">登录</a>", wxMpXmlMessage);
    }

    /**
     * 用户授权
     *
     * @param userInfo
     */
    @Override
    public void authorize(WxOAuth2UserInfo userInfo) {
        String openid = userInfo.getOpenid();
        User user = userDao.getByOpenId(openid);
        //更新用户信息
        if (StrUtil.isBlank(user.getAvatar())) {
            fillUserInfo(user.getId(), userInfo);
        }

        //通过code找到用户channel，进行登录
        Integer code = WAIT_AUTHORIZE_MAP.remove(openid);
        webSocketService.scanLoginSuccess(code, user.getId());
    }

    private void fillUserInfo(Long uid, WxOAuth2UserInfo userInfo) {
        User user = UserAdapter.buildAuthorizeUser(uid, userInfo);
        userDao.updateById(user);
    }

    private Integer getEventKey(WxMpXmlMessage wxMpXmlMessage) {
        try {
            //todo 事件码 qrscene_2
            String eventKey = wxMpXmlMessage.getEventKey();
            String code = eventKey.replace("qrscene_", "");
            return Integer.parseInt(code);
        } catch (NumberFormatException e) {
            log.error("getEventKey error eventKey:{}", wxMpXmlMessage.getEventKey(), e);
            return null;
        }
    }
}
