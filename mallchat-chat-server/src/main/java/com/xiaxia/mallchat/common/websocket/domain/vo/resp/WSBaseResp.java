package com.xiaxia.mallchat.common.websocket.domain.vo.resp;

/**
 * @author xfl
 * @date 2023/11/27 22:52
 */
public class WSBaseResp<T> {
    /**
     * @see com.xiaxia.mallchat.common.websocket.domain.enums.WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
