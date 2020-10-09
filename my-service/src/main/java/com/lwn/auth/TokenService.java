package com.lwn.auth;

import com.lwn.enums.Const;
import com.lwn.exception.AnnotationException;
import com.lwn.exception.NoAuthException;
import com.lwn.model.entity.UserInfo;
import com.lwn.request.SessionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenService {

    @Autowired
    private RedisUtils redisUtils;

    @Value("${token.timeout:600}")
    private Long tokenTimeOut;

    public void checkTokenIsValid(boolean isCheck) {
        HttpServletRequest req = SessionHolder.getRequest();
        assert req != null;
        String token = req.getHeader("token");// getHeader()是获取HTTP头部信息,getParameter()是获取表单参数
        String userInfoId = redisUtils.get(Const.TOKEN + token, tokenTimeOut);
        if (StringUtils.isEmpty(token)) {
            token = req.getParameter("token");
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userInfoId)) {
                if (isCheck) {
                    throw new AnnotationException("token不存在");
                }
            }
        } else {
            if (StringUtils.isEmpty(userInfoId)) {
                if (isCheck) {
                    throw new AnnotationException("token失效");
                }
            }
        }
        checkUserInfo(isCheck, req, userInfoId);
    }

    private void checkUserInfo(boolean isCheck, HttpServletRequest req, String userInfoId) {
        UserInfo userInfo = redisUtils.get(Const.USER_INFO + userInfoId, UserInfo.class, tokenTimeOut);
        if (userInfo != null) {
            // 刷新缓存
            redisUtils.get(Const.USER_INFO + userInfoId, tokenTimeOut);
            req.setAttribute(Const.CURRENT_USER, userInfo);
        } else {
            if (isCheck) {
                throw new NoAuthException("登录失效,请重新登录");
            }
        }
    }

}
