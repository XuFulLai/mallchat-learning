package com.xiaxia.mallchat.common.common.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import com.xiaxia.mallchat.common.common.domain.dto.RequestInfo;
import com.xiaxia.mallchat.common.common.util.RequestHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author xfl
 * @date 2024/1/21 17:51
 */

@Component
public class CollectorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long uid = Optional.ofNullable(request.getAttribute(TokenInterceptor.UID))
                .map(Object::toString)
                .map(Long::parseLong).orElse(null);
        String ip = ServletUtil.getClientIP(request);

        RequestHolder.set(RequestInfo.builder().uid(uid).ip(ip).build());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
    }
}
