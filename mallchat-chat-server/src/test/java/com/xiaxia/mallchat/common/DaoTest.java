package com.xiaxia.mallchat.common;

import com.xiaxia.mallchat.common.common.thread.MyUncaughtExceptionHandler;
import com.xiaxia.mallchat.common.common.util.JwtUtils;
import com.xiaxia.mallchat.common.user.dao.UserDao;
import com.xiaxia.mallchat.common.user.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Description:
 * Author: <a href="https://github.com/zongzibinbin">xiaxia</a>
 * Date: 2023-08-27
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private JwtUtils jwtUtils;
    @Test
    public void jwt() {
        System.out.println(jwtUtils.createToken(1L));
        System.out.println(jwtUtils.createToken(1L));
        System.out.println(jwtUtils.createToken(1L));
    }

    @Test
    public void test() {
        System.out.println(userDao.getById(1));
    }

    @Test
    public void test2() throws WxErrorException {
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(1, 10000);
        String url = wxMpQrCodeTicket.getUrl();
        System.out.println(url);
    }

    @Autowired
    private RedissonClient redissonClient;
    @Test
    public void test3() {
        RLock lock = redissonClient.getLock("123");
        lock.lock();
        try {
            System.out.println("123");
        } finally {
            lock.unlock();
        }
    }

    @Autowired
    private LoginService loginService;

    @Test
    public void test4() {
        String s = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjIwMDAwLCJjcmVhdGVUaW1lIjoxNzA1ODA2MjIwfQ.OGMqW7Ild7uh0opTZGR1vF6Hww-nKNHSEGzYqJLhuRc";
        Long validUid = loginService.getValidUid(s);
        System.out.println(validUid);
    }

    /**
     * 测试线程池不捕获异常
     */

    @Autowired
    private ThreadPoolTaskExecutor threadPoolExecutor;
    @Test
    public void test5() throws InterruptedException {
        threadPoolExecutor.execute(()->{
            if (1 == 1) {
                log.error("123");
                throw new RuntimeException("1243");
            }
        });
        Thread.sleep(200);
    }

    @Test
    public void test6() {
        String token = loginService.login(20000L);
        System.out.println(token);
    }
}
