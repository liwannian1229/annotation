package com.lwn.my.service.context;

import com.lwn.common.utils.cache.RedisUtils;
import com.lwn.common.utils.util.CommonUtil;
import com.lwn.common.utils.util.MD5Util;
import com.lwn.common.utils.enums.ClientType;
import com.lwn.common.utils.enums.Const;
import com.lwn.repo.model.entity.UserInfo;
import com.lwn.repo.model.mapper.UserInfoMapper;
import com.lwn.common.utils.request.SessionHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Slf4j
@Component
public class UserContext {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Value("${token.timeout}")
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

        String token = createToken();

        redisUtils.set(Const.TOKEN + token, userInfo.getId(), tokenTimeOut);
        redisUtils.set(Const.USER_INFO + userInfo.getId(), userInfo, tokenTimeOut);
        redisUtils.set(Const.CLIENT_TYPE + ClientType.WEB, ClientType.WEB, tokenTimeOut);

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

    public String createToken() {
        String token = "annotation_lwn_";
        // new Random()的nextInt(a)方法,随机生成一个[0,a)的整数,Math.random()随机生成一个[0,1)的小数
        token += CommonUtil.getUUID().substring(0, 8);
        token += ((System.currentTimeMillis()) + "").substring(0, 8);
        token = MD5Util.get32MD5String(token);

        // encoder编码器,decoder解码器
        return Base64.getEncoder().encodeToString((token == null ? "" : token).getBytes());
    }

    public void updateCurrentUser(UserInfo userInfo) {

        // key相同自动刷新覆盖
        redisUtils.set(Const.USER_INFO + userInfo.getId(), userInfo, tokenTimeOut);
    }

}
