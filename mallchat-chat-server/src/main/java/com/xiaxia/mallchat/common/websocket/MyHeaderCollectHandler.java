package com.xiaxia.mallchat.common.websocket;

import cn.hutool.core.net.url.UrlBuilder;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.util.Optional;

/**
 * @author xfl
 * @date 2024/1/21 13:10
 */
public class MyHeaderCollectHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 传统的HTTP接入
        if (msg instanceof FullHttpRequest) {
            //强转
            HttpRequest request = (HttpRequest) msg;
            UrlBuilder uri = UrlBuilder.ofHttp(request.uri());
            // 获取token参数
            Optional<String> tokenOptional = Optional.ofNullable(uri.getQuery())
                    .map(k -> k.get("token"))
                    .map(CharSequence::toString);

            tokenOptional.ifPresent(s -> NettyUtil.setAttr(ctx.channel(), NettyUtil.TOKEN, s));
            request.setUri(uri.getPath().toString());
        }
        // WebSocket接入
        ctx.fireChannelRead(msg);
    }
}
