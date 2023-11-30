package com.xiaxia.mallchat.common.user.dao;

import com.xiaxia.mallchat.common.user.domain.entity.User;
import com.xiaxia.mallchat.common.user.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xfl
 * @since 2023-11-28
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> {

}
