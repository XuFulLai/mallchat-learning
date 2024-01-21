package com.xiaxia.mallchat.common.common.util;

import com.xiaxia.mallchat.common.common.domain.dto.RequestInfo;

/**
 * Description: 请求上下文
 * @author xfl
 * @date 2024/1/21 17:57
 */
public class RequestHolder {

    private static final ThreadLocal<RequestInfo> threadLocal = new ThreadLocal<>();

    public static void set(RequestInfo requestInfo) {
        threadLocal.set(requestInfo);
    }

    public static RequestInfo get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
