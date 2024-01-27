package com.xiaxia.mallchat.common.user.controller;


import com.xiaxia.mallchat.common.common.domain.dto.RequestInfo;
import com.xiaxia.mallchat.common.common.domain.vo.resp.ApiResult;
import com.xiaxia.mallchat.common.common.util.RequestHolder;
import com.xiaxia.mallchat.common.user.domain.vo.request.user.ModifyNameReq;
import com.xiaxia.mallchat.common.user.domain.vo.request.user.WearingBadgeReq;
import com.xiaxia.mallchat.common.user.domain.vo.response.user.BadgeResp;
import com.xiaxia.mallchat.common.user.domain.vo.response.user.UserInfoResp;
import com.xiaxia.mallchat.common.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xfl
 * @since 2023-11-28
 */
@RestController
@RequestMapping("/capi/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @ApiOperation(value = "获取用户信息")
    public ApiResult<UserInfoResp> getUserInfo() {
        return ApiResult.success(userService.getUserInfo(RequestHolder.get().getUid()));
    }

    @PutMapping("/name")
    @ApiOperation(value = "修改用户名")
    public ApiResult<Void> modifyName(@Validated @RequestBody ModifyNameReq req) {
        userService.modifyName(RequestHolder.get().getUid(), req);
        return ApiResult.success();
    }

    @GetMapping("badges")
    @ApiOperation(value = "可选徽章预览")
    public ApiResult<List<BadgeResp>> badges() {
        return ApiResult.success(userService.badges(RequestHolder.get().getUid()));
    }

    @PutMapping("badge")
    @ApiOperation("佩戴徽章")
    public ApiResult<Void> wearingBadges(@Valid @RequestBody WearingBadgeReq req) {
        userService.wearingBadges(RequestHolder.get().getUid(), req.getBadgeId());
        return ApiResult.success();
    }
}

