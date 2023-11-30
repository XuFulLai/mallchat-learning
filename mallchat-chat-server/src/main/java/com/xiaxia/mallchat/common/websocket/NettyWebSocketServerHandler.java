package com.xiaxia.mallchat.common.websocket;

import cn.hutool.json.JSONUtil;
import com.xiaxia.mallchat.common.websocket.domain.enums.WSReqTypeEnum;
import com.xiaxia.mallchat.common.websocket.domain.vo.req.WSBaseReq;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            System.out.println("握手成功");
        } else if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state()== IdleState.READER_IDLE) {
                System.out.println("读空闲");
                // todo 用户下线
                ctx.channel().close();
            } else if (event.state()== IdleState.WRITER_IDLE) {
                System.out.println("写空闲");
            } else if (event.state()== IdleState.ALL_IDLE) {
                System.out.println("读写空闲");
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String text = msg.text();
        WSBaseReq wsBaseReq = JSONUtil.toBean(text, WSBaseReq.class);
        switch (WSReqTypeEnum.of(wsBaseReq.getType())) {
            case LOGIN:
                System.out.println("请求二维码");
                ctx.channel().writeAndFlush(new TextWebSocketFrame("登录成功"));
            case HEARTBEAT:
                break;
            case AUTHORIZE:
                break;
        }
        System.out.println(text);
    }

//    private WebSocketService webSocketService;
//
//    // 当web客户端连接后，触发该方法
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        this.webSocketService = getService();
//    }
//
//    // 客户端离线
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        userOffLine(ctx);
//    }
//
//    /**
//     * 取消绑定
//     *
//     * @param ctx
//     * @throws Exception
//     */
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        // 可能出现业务判断离线后再次触发 channelInactive
//        log.warn("触发 channelInactive 掉线![{}]", ctx.channel().id());
//        userOffLine(ctx);
//    }
//
//    private void userOffLine(ChannelHandlerContext ctx) {
//        this.webSocketService.removed(ctx.channel());
//        ctx.channel().close();
//    }
//
//    /**
//     * 心跳检查
//     *
//     * @param ctx
//     * @param evt
//     * @throws Exception
//     */
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
//            // 读空闲
//            if (idleStateEvent.state() == IdleState.READER_IDLE) {
//                // 关闭用户的连接
//                userOffLine(ctx);
//            }
//        } else if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
//            this.webSocketService.connect(ctx.channel());
//            String token = NettyUtil.getAttr(ctx.channel(), NettyUtil.TOKEN);
//            if (StrUtil.isNotBlank(token)) {
//                this.webSocketService.authorize(ctx.channel(), new WSAuthorize(token));
//            }
//        }
//        super.userEventTriggered(ctx, evt);
//    }
//
//    // 处理异常
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        log.warn("异常发生，异常消息 ={}", cause);
//        ctx.channel().close();
//    }
//
//    private WebSocketService getService() {
//        return SpringUtil.getBean(WebSocketService.class);
//    }
//
//    // 读取客户端发送的请求报文
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
//        WSBaseReq wsBaseReq = JSONUtil.toBean(msg.text(), WSBaseReq.class);
//        WSReqTypeEnum wsReqTypeEnum = WSReqTypeEnum.of(wsBaseReq.getType());
//        switch (wsReqTypeEnum) {
//            case LOGIN:
//                this.webSocketService.handleLoginReq(ctx.channel());
//                log.info("请求二维码 = " + msg.text());
//                break;
//            case HEARTBEAT:
//                break;
//            default:
//                log.info("未知类型");
//        }
//    }
}
