package com.xiaxia.mallchat.common.common.thread;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadFactory;

/**
 * @author xfl
 * @date 2024/1/21 12:00
 *
 * 装饰器模式！！！
 */

@AllArgsConstructor
public class MyThreadFactory implements ThreadFactory {

    private final static MyUncaughtExceptionHandler MY_UNCAUGHT_EXCEPTION_HANDLER = new MyUncaughtExceptionHandler();

    private ThreadFactory originThreadFactory;

    @Override
    public Thread newThread(Runnable r) {
        //执行spring线程自己创建的线程 逻辑
        Thread thread = originThreadFactory.newThread(r);
        // 增加自己的逻辑
        thread.setUncaughtExceptionHandler(MY_UNCAUGHT_EXCEPTION_HANDLER);
        return thread;
    }
}
