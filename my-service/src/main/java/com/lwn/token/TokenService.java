package com.lwn.token;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lwn.enumeration.Const;
import com.lwn.exception.AnnotationException;
import com.lwn.exception.NotFoundException;
import com.lwn.exception.TokenInValidException;
import com.lwn.model.entity.UserInfo;
import com.lwn.model.mapper.UserInfoMapper;
import com.lwn.model.ro.UserInfoRo;
import com.lwn.request.SessionHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
            Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();
            if (!claims.getSubject().equals(userInfo.getName() + ":" + userInfo.getPassword()) && (claims.getExpiration().getTime() < new Date().getTime())) {
                if (isCheck) {
                    throw new TokenInValidException("token失效");
                }
            }
            req.setAttribute(Const.CURRENT_USER, userInfo);
        }
    }

    public void checkToken(HttpServletRequest req) throws AnnotationException {

        String token = req.getHeader("token");// getHeader()是获取HTTP头部信息,getParameter()是获取表单参数
        if (StringUtils.isEmpty(token)) {
            token = req.getParameter("token");
            if (StringUtils.isEmpty(token)) {
                throw new AnnotationException("token不存在");
            }
        }

        /*if (!redisService.exists(token)) {
            throw new AnnotationException("重复的操作");
        }

        boolean remove = redisService.remove(token);
        if (!remove) {
            throw new AnnotationException("重复的操作");
        }*/
    }

    public void checkToken_1(boolean isCheck) throws AnnotationException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest req = attributes.getRequest();
        String token = req.getHeader("token");// getHeader()是获取HTTP头部信息,getParameter()是获取表单参数
        if (StringUtils.isEmpty(token)) {
            token = req.getParameter("token");
            if (StringUtils.isEmpty(token)) {
                if (isCheck) {
                    throw new AnnotationException("token不存在");
                }
            }
        }


    }
}
