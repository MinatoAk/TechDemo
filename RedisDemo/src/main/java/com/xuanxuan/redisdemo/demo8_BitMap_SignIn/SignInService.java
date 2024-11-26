package com.xuanxuan.redisdemo.demo8_BitMap_SignIn;

import java.util.List;

public interface SignInService {

    /**
     * 添加用户当日签到
     *
     * @param userId
     * @return
     */
    boolean addSignIn(long userId);

    List<Integer> getSignIn(long userId, Integer year);
}
