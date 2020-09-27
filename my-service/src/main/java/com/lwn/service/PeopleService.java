package com.lwn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwn.cache.RedisCache;
import com.lwn.model.entity.People;
import com.lwn.model.mapper.PeopleMapper;
import com.lwn.model.ro.PeopleRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class PeopleService {

    @Autowired
    private PeopleMapper peopleMapper;

    @RedisCache(key = "'people_pages:'+#ro.name+':'+#ro.pageIndex+'_'+#ro.pageSize", expire = 10)
    public IPage<People> selectPeoplesPages(PeopleRo ro) {
        Page<People> page = new Page<>(ro.getPageIndex(), ro.getPageSize());
        QueryWrapper<People> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(ro.getOrderType().getValue().equals("desc"), false, ro.getSort())
                .like("name", ro.getName());

        return peopleMapper.selectPage(page, queryWrapper);
    }

}
