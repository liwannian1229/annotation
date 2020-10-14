package com.lwn.task;

import com.lwn.cache.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

@Slf4j
@Component
public class ScheduleTask {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @Scheduled(cron = " 5 * * * * ? ")
    public void scheduleDoSomething() {

        log.info("开始缓存student表的id");

        String sql = "SELECT id FROM student WHERE is_del = 0";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Object>) preparedStatement -> {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                redisUtils.set("table_id:" + id, id);
            }

            return null;
        });
    }

    @Scheduled(cron = " 5 * 5 * * ? ")
    public void scheduleRecommendData() {

        // PreparedStatement继承Statement,CallableStatement又继承于PrepareStatement,
        // 爷爷是最基础的sql执行者
        // 父亲可以预编译,防止sql注入
        // 儿子在前两者的基础上,增加了可以调用存储过程的功能
        String sql = "";
        jdbcTemplate.execute(sql, (CallableStatementCallback<Object>) ps -> {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String string = rs.getString("");

                redisUtils.set("", "");
            }

            return null;
        });

    }
}
