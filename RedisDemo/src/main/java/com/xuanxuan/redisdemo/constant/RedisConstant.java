package com.xuanxuan.redisdemo.constant;

public interface RedisConstant {
    String LOCK_KEY = "CACHE_BREAKDOWN_KEY";

    String SIGN_IN_KEY_PREFIX = "user:signin";

    static String generateSignInKey(long userId, int year) {
        return String.format("%s:%s:%s", SIGN_IN_KEY_PREFIX, year, userId);
    }
}
