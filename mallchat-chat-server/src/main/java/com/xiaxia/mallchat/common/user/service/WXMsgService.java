package com.xiaxia.mallchat.common.user.service;

import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * Description: 处理与微信api的交互逻辑
 * Author: <a href="https://github.com/zongzibinbin">xiaxia</a>
 * Date: 2023-03-19
 */

public interface WXMsgService {

    WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage);

    void authorize(WxOAuth2UserInfo userInfo);
}
