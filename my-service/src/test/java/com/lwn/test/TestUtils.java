package com.lwn.test;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lwn.BaseTest;
import com.lwn.auth.RedisUtils;
import com.lwn.model.entity.UserInfo;
import com.lwn.model.mapper.UserInfoMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;

import java.util.List;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
public class TestUtils extends BaseTest {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Value("${spring.profiles.active}")
    private String profilesActive;

    @Test
    public void testPrefix() {
        //String key = redisUtils.prefix("key");

        System.out.println(profilesActive);
    }

    @Test
    @Rollback(false)
    public void testDB() {
      /*  QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        // ge >=
        queryWrapper.ge(true, "id", 4);
        List<UserInfo> userInfos = userInfoMapper.selectList(queryWrapper);
        userInfos.forEach(u -> {
            System.out.println(u);
            userInfoMapper.insert(u);
        });*/
    }
}
