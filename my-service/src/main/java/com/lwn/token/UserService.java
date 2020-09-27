package com.lwn.token;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lwn.common.BeanUtil;
import com.lwn.common.MD5Util;
import com.lwn.context.UserContext;
import com.lwn.exception.AnnotationException;
import com.lwn.exception.ValidationException;
import com.lwn.model.entity.UserInfo;
import com.lwn.model.mapper.UserInfoMapper;
import com.lwn.model.ro.UserInfoRo;
import com.lwn.model.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserContext userContext;

    @Autowired
    private UserInfoMapper userInfoMapper;

    // 登录
    public LoginVo doLogin(UserInfoRo ro) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", ro.getName())
                .eq("password", MD5Util.getMD5String(ro.getPassword()));
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        if (userInfo != null) {

            return new LoginVo(userContext.login(BeanUtil.target(UserInfo.class).accept(userInfo)), "login success");
        }

        return new LoginVo("密码或用户名错误!", "login error");
    }

    // 注册
    public void createUser(UserInfoRo ro) {

        UserInfo userInfo = new UserInfo();
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", ro.getName());
        if (!CollectionUtils.isEmpty(userInfoMapper.selectList(queryWrapper))) {

            throw new ValidationException("用户名已存在!");
        }
        userInfo.setName(ro.getName());
        userInfo.setPassword(MD5Util.getMD5String(ro.getPassword()));

        // 密码加密后入库
        userInfoMapper.insert(userInfo);
    }

    // 退出登录
    public void loginOut() {
        userContext.removeCurrentUser();
    }

}
