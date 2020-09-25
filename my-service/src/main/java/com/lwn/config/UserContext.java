package com.lwn.config;

import com.lwn.model.entity.UserInfo;
import com.lwn.request.SessionHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class UserContext {

    // 获取当前用户信息
    public UserInfo getUserInfo() {

        HttpServletRequest request = SessionHolder.getRequest();
        if (request == null) {

            return null;
        }



        return null;
    }
}
