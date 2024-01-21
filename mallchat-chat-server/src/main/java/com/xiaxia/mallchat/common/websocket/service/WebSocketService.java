package com.xiaxia.mallchat.common.websocket.service;

import io.netty.channel.Channel;

/**
 * @author xfl
 * @date 2023/12/13 22:18
 */
public interface WebSocketService {
    void connect(Channel channel);

    void handleLoginReq(Channel channel);

    void offline(Channel channel);

    void scanLoginSuccess(Integer code, Long uid);

    void waitAuthorize(Integer code);

    void authorize(Channel channel, String token);
}
