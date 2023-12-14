package com.xiaxia.mallchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: ws的基本返回信息体
 * Author: <a href="https://github.com/zongzibinbin">xiaxia</a>
 * Date: 2023-03-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSBaseResp<T> {
    /**
     * ws推送给前端的消息
     *
     * @see WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
