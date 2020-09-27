package com.lwn.context;

import com.lwn.common.CommonUtil;
import com.lwn.enumeration.Const;
import com.lwn.model.entity.UserInfo;
import com.lwn.model.mapper.UserInfoMapper;
import com.lwn.request.SessionHolder;
import com.lwn.token.RedisUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class UserContext {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Value("${token.timeout:600}")
    private Long tokenTimeOut;

    // 获取当前用户信息
    public UserInfo getUserInfo() {

        HttpServletRequest request = SessionHolder.getRequest();
        if (request == null) {

            return null;
        }

        Object currentUser = request.getAttribute(Const.CURRENT_USER);
        if (currentUser != null) {

            return (UserInfo) currentUser;
        }

        return null;
    }

    // login 登录
    public String login(UserInfo userInfo) {

        String token = createToken(userInfo);

        redisUtils.set(Const.TOKEN + token, userInfo.getId(), tokenTimeOut);
        redisUtils.set(Const.USER_INFO + userInfo.getId(), userInfo, tokenTimeOut);

        return token;
    }

    // 退出当前登录
    public void removeCurrentUser() {
        UserInfo userInfo = getUserInfo();

        if (userInfo != null) {
            HttpServletRequest request = SessionHolder.getRequest();
            assert request != null;
            String token = request.getHeader("token");
            if (CommonUtil.isEmpty(token)) {
                token = request.getParameter("token");
                if (CommonUtil.isNotEmpty(token)) {
                    redisUtils.delete(Const.TOKEN + token);
                    redisUtils.delete(Const.USER_INFO + userInfo.getId());
                }
            } else {
                redisUtils.delete(Const.TOKEN + token);
                redisUtils.delete(Const.USER_INFO + userInfo.getId());
            }
        }


    }

    public String createToken(UserInfo userInfo) {

        long now = System.currentTimeMillis();//当前时间
        long exp = now + 1000 * 60 * 10;//过期时间为10分钟
        JwtBuilder builder = Jwts.builder()
                .setSubject(userInfo.getName() + ":" + userInfo.getPassword())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "itcast")
                .setExpiration(new Date(exp));

        return builder.compact();
    }

    public void updateCurrentUser(UserInfo userInfo) {

        // key相同自动刷新覆盖
        redisUtils.set(Const.USER_INFO + userInfo.getId(), userInfo, tokenTimeOut);
    }

}
