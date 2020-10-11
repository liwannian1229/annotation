package com.lwn.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwn.auth.RedisUtils;
import com.lwn.model.entity.People;
import com.lwn.model.mapper.PeopleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liwannian
 * @date 2020/10/11 20:33
 */
@Slf4j
@Component
public class RecommendTask {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private PeopleMapper peopleMapper;

    @Scheduled(cron = " 5 * * * * ? ")
    public void recommendTask() {
        // 根据某一些条件拿到一些可用数据
        QueryWrapper<People> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "第");
        List<People> peopleList = peopleMapper.selectList(queryWrapper);
        int p = peopleList.size() / 100;
        if (peopleList.size() % 100 != 0) {
            p += 1;
        }
    }
}
