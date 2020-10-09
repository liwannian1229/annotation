package com.lwn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwn.cache.RedisCache;
import com.lwn.cache.RedisCacheClear;
import com.lwn.common.BeanUtil;
import com.lwn.context.UserContext;
import com.lwn.exception.NotFoundException;
import com.lwn.model.entity.People;
import com.lwn.model.entity.UserInfo;
import com.lwn.model.mapper.PeopleMapper;
import com.lwn.model.ro.AddPeopleRo;
import com.lwn.model.ro.PeopleRo;
import com.lwn.model.ro.UpdatePeopleRo;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class PeopleService {

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private UserContext userContext;

    private Long getCurrentUserId() {

        return Optional.ofNullable(userContext.getUserInfo().getId()).orElse(new UserInfo().getId());
    }

    // @RedisCache存的key是自定义的key,值是方法的返回值
    @RedisCache(key = "'people_pages:'+#ro.name+':'+#ro.pageIndex+'_'+#ro.pageSize+':'+#ro.orderType.getValue()+':'+#ro.sort", expire = 600)
    public IPage<People> selectPeoplesPages(PeopleRo ro) {
        Page<People> page = new Page<>(ro.getPageIndex(), ro.getPageSize());
        QueryWrapper<People> queryWrapper = new QueryWrapper<>();
        // condition 表示是否排序
        queryWrapper.orderBy(true, ro.getOrderType().getValue().equals("asc"), ro.getSort())
                .like("name", ro.getName());

        return peopleMapper.selectPage(page, queryWrapper);
    }

    @RedisCacheClear(keys = "'people_pages:'")
    public void deletePeople(Long id) {

        People people = peopleMapper.selectById(id);
        if (people != null) {
            peopleMapper.deleteById(id);
        }

    }

    @RedisCacheClear(keys = "'people_pages:'")
    public void insertPeople(AddPeopleRo ro) {
        QueryWrapper<People> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(true, "name", ro.getName());
        List<People> list = peopleMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            peopleMapper.insert(BeanUtil.target(People.class).acceptDefault(ro, (people, addPeopleRo) -> {
                people.setUserId(getCurrentUserId());
            }));
        }

    }

    @RedisCacheClear(keys = "'people_pages:'")
    public void updatePeople(UpdatePeopleRo ro) {

        People people1 = peopleMapper.selectById(ro.getId());
        if (people1 != null) {
            peopleMapper.updateById(BeanUtil.target(People.class).accept(ro));
        }

    }

}
