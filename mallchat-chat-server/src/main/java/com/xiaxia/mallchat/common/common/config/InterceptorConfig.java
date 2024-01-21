package com.xiaxia.mallchat.common.common.config;

import com.xiaxia.mallchat.common.common.interceptor.CollectorInterceptor;
import com.xiaxia.mallchat.common.common.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xfl
 * @date 2024/1/21 17:41
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Autowired
    private CollectorInterceptor collectorInterceptor;

    /**
     * 拦截器配置，注意有顺序
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/capi/**");

        registry.addInterceptor(collectorInterceptor)
                .addPathPatterns("/capi/**");
    }
}
