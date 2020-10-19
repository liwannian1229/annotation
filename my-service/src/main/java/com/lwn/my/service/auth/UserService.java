package com.lwn.my.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lwn.my.service.cache.RedisUtils;
import com.lwn.common.utils.util.BeanUtil;
import com.lwn.common.utils.util.CommonUtil;
import com.lwn.common.utils.util.ImageVerCodeUtil;
import com.lwn.common.utils.util.MD5Util;
import com.lwn.my.service.context.UserContext;
import com.lwn.common.utils.enums.LoginStatusCode;
import com.lwn.my.service.exception.ValidationException;
import com.lwn.repo.model.entity.UserInfo;
import com.lwn.repo.model.mapper.UserInfoMapper;
import com.lwn.my.service.model.ro.UserInfoRo;
import com.lwn.my.service.model.vo.LoginVo;
import com.lwn.common.utils.request.SessionHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserContext userContext;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisUtils redisUtils;

    // 登录
    public LoginVo doLogin(UserInfoRo ro) {
        String sessionId = null;
        Cookie[] cookies = Objects.requireNonNull(SessionHolder.getRequest()).getCookies();
        if (cookies != null) {
            sessionId = Arrays.stream(cookies).filter(c -> c.getName().equals("sessionId")).map(Cookie::getValue).findFirst().orElse(null);
        }
        if (CommonUtil.isEmpty(sessionId)) {
            sessionId = SessionHolder.getRequest().getHeader("sessionId");
            if (CommonUtil.isEmpty(sessionId)) {
                sessionId = SessionHolder.getRequest().getParameter("sessionId");
                if (CommonUtil.isEmpty(sessionId)) {
                    sessionId = (String) SessionHolder.getSession().getAttribute("sessionId");
                }
            }
        }
        String captcha = redisUtils.getString("captcha:" + sessionId, 60 * 10);
        if (captcha == null || sessionId == null) {
            throw new ValidationException("验证码不正确!");
        }
        if (!captcha.equalsIgnoreCase(ro.getCaptcha()) || CommonUtil.isEmpty(captcha)) {

            log.error("输入的验证码:" + ro.getCaptcha() + "错误");

            return new LoginVo(null, LoginStatusCode.FAILURE, "验证码错误!");
        }

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", ro.getName())
                .eq("password", MD5Util.get32MD5String(ro.getPassword()));
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        if (userInfo == null) {

            log.error("输入的用户名:" + ro.getName() + ",密码:" + ro.getPassword() + "错误");

            return new LoginVo(null, LoginStatusCode.FAILURE, "密码或用户名错误!");
        }

        log.info(ro.getName() + " 登录成功!");

        return new LoginVo(userContext.login(BeanUtil.target(UserInfo.class).accept(userInfo)), LoginStatusCode.SUCCESS, null);
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
        userInfo.setPassword(MD5Util.get32MD5String(ro.getPassword()));

        // 密码加密后入库
        userInfoMapper.insert(userInfo);
    }

    // 生成验证码
    public void generateCaptcha(HttpServletResponse response) throws IOException {

        String sessionId = SessionHolder.getSession().getId();
        Cookie cookie = new Cookie("sessionId", sessionId);
        cookie.setPath("/");
        // 默认-1,即不存储cookie;0为退出即删除cookie;其他值为存储秒数
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        response.addHeader("sessionId", sessionId);
        response.addHeader("Access-Control-Expose-Headers", "*");

        SessionHolder.getSession().setAttribute("sessionId", sessionId);
        String captcha = ImageVerCodeUtil.outputVerifyImage(400, 100, response.getOutputStream(), 4);

        redisUtils.set("captcha:" + sessionId, captcha, 10 * 60);
    }

    // 退出登录
    public void loginOut() {
        userContext.removeCurrentUser();
    }

}
