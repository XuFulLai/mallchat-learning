package com.xiaxia.mallchat.common.common.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xfl
 * @date 2024/1/21 11:53
 */
@Slf4j
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception in thread {}:", e);
    }
}
