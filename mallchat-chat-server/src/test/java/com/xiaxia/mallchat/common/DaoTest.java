package com.xiaxia.mallchat.common;

import com.xiaxia.mallchat.common.user.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
}
