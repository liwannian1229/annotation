package com.lwn.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

/**
 * @Description MybatisPlusOutImpl，自定义 直接使用控制台输出日志
 * 暂时没有用到
 **/
@Slf4j
@Deprecated
public class MybatisPlusOutImpl implements Log {

    public MybatisPlusOutImpl(String clazz) {

        log.debug(clazz);

    }

    public boolean isDebugEnabled() {

        return true;
    }

    public boolean isTraceEnabled() {

        return false;
    }

    public void error(String s, Throwable e) {

        log.error(s);
        e.getMessage();
    }

    public void error(String s) {

        log.error(s);
    }

    public void debug(String s) {

        log.debug(s);
    }

    public void trace(String s) {

        log.trace(s);
    }

    public void warn(String s) {

        log.warn(s);
    }
}

