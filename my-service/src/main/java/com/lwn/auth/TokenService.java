package com.lwn.auth;

import com.lwn.enumeration.Const;
import com.lwn.exception.AnnotationException;
import com.lwn.exception.NoAuthException;
import com.lwn.exception.TokenInValidException;
import com.lwn.model.entity.UserInfo;
import com.lwn.request.SessionHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
            UserInfo userInfo = redisUtils.get(Const.USER_INFO + userInfoId, UserInfo.class, tokenTimeOut);
            if (userInfo != null) {
                // 刷新缓存
                redisUtils.get(Const.USER_INFO + userInfoId, tokenTimeOut);
                Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();
                if (!claims.getSubject().equals(userInfo.getName() + ":" + userInfo.getPassword()) && (claims.getExpiration().getTime() < new Date().getTime())) {
                    if (isCheck) {
                        throw new TokenInValidException("token失效");
                    }
                }
                req.setAttribute(Const.CURRENT_USER, userInfo);
            } else {
                throw new NoAuthException("登录失效,请重新登录");
            }
        }
    }

}
