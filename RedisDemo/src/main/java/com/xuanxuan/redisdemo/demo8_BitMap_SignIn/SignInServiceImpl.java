package com.xuanxuan.redisdemo.demo8_BitMap_SignIn;

import com.xuanxuan.redisdemo.constant.RedisConstant;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Service
public class SignInServiceImpl implements SignInService {

    @Resource
    private RedissonClient redissonClient;


    @Override
    public boolean addSignIn(long userId) {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int offset = today.getDayOfYear();

        // 1) 获取该用户的 BitMap
        String signInKey = RedisConstant.generateSignInKey(userId, year);
        RBitSet signInBitSet = redissonClient.getBitSet(signInKey);

        // 2) 如果未签到则签到
        if (!signInBitSet.get(offset)) {
            signInBitSet.set(offset);
        }

        // 3) 签到成功
        return true;
    }

    @Override
    public List<Integer> getSignIn(long userId, Integer year) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        // 1) 获取该用户的 BitMap
        String signInKey = RedisConstant.generateSignInKey(userId, year);
        RBitSet signInBitSet = redissonClient.getBitSet(signInKey);

        // 2) 本地把 BitMap 缓存起来，避免频繁向 Redis 发起请求，这很关键
        BitSet bitSet = signInBitSet.asBitSet();

        // 3) 遍历 bitSet, 获取该用户本年签到记录
        List<Integer> signInList = new ArrayList<>();
        int idx = bitSet.nextSetBit(0);
        while (idx >= 0) {
            signInList.add(idx);
            idx = bitSet.nextSetBit(idx + 1);
        }

        return signInList;
    }


}
