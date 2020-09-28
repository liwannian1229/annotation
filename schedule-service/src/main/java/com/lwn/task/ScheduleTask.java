package com.lwn.task;

import com.lwn.auth.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Scheduled(cron = "5 * * * * ? ")
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
}
