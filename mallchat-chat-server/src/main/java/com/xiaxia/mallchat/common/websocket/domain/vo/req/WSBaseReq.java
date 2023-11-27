package com.xiaxia.mallchat.common.websocket.domain.vo.req;

import lombok.Data;

/**
 * @author xfl
 * @date 2023/11/27 22:46
 */
@Data
public class WSBaseReq {
    /**
     * @see com.xiaxia.mallchat.common.websocket.domain.enums.WSReqTypeEnum
     */
    private Integer type;
    private String data;
}
