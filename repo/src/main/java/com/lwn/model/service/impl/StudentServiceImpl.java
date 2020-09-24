package com.lwn.model.service.impl;

import com.lwn.model.entity.StudentEntity;
import com.lwn.model.dao.StudentDao;
import com.lwn.model.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwannian
 * @since 2020-09-24
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, StudentEntity> implements StudentService {

}
