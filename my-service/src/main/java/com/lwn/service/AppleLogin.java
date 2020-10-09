package com.lwn.service;

import com.alibaba.fastjson.JSONObject;
import com.lwn.common.AppleUtil;
import com.lwn.context.UserContext;
import com.lwn.enumeration.Const;
import com.lwn.model.entity.UserInfo;
import com.lwn.request.SessionHolder;
import com.lwn.response.ResponseResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Service
@Api(value = "苹果登录")
public class AppleLogin {

    @Autowired
    private UserContext userContext;

    @Transactional
    public ResponseResult<Object> appleLogin(String identityToken) {
        try {
            Map<String, String> map = new HashMap<>();

            // 验证identityToken
            if (!AppleUtil.verify(identityToken)) {

                return ResponseResult.failureResult("授权验证失败");
            }
            // 对identityToken解码
            JSONObject json = AppleUtil.parserIdentityToken(identityToken);
            if (json == null) {

                return ResponseResult.failureResult("授权验证失败");
            }
            UserInfo userInfo = (UserInfo) Objects.requireNonNull(SessionHolder.getRequest()).getAttribute(Const.CURRENT_USER);
            String token = userContext.login(userInfo);
            map.put("token", token);
            return ResponseResult.successResult(map);
        } catch (Exception e) {
            log.error("app wxLogin error:" + e.getMessage(), e);
            return ResponseResult.errorResult("服务器异常", e);
        }

    }
}
