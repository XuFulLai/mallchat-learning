package com.xiaxia.mallchat.common.common.constant;

/**
 * @author xfl
 * @date 2024/1/20 15:41
 */
public class RedisKey {
    private static final String BASE_KEY = "mallchat:chat";

    /**
     * 用户token的key
     */
    public static final String USER_TOKEN_STRING = "userToken:uid_%d";

    public static String getKey(String key, Object... args) {
        return BASE_KEY + String.format(key, args);
    }
}
