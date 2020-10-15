package com.lwn.my.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lwn.common.utils.enums.ResourceCharacter;
import com.lwn.repo.model.entity.Student;
import com.lwn.repo.model.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liwannian
 * @date 2020/10/10 21:20
 */
@Service
@Slf4j
public class CountService {

    @Autowired
    private StudentMapper studentMapper;

    public String getCount(String temp) {

        if (temp.equals(ResourceCharacter.FIVE)) {
//            List<Student> students = studentMapper.selectList(null);
//            List<Student> collect = students.stream().filter(s -> s.getId() == 2).collect(Collectors.toList());

            QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", 2);
            List<Student> students = studentMapper.selectList(queryWrapper);

            return students.get(0).getName();
        }
        return "";
    }
}
