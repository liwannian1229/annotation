package com.lwn.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwn.model.entity.People;
import com.lwn.model.ro.PeopleRo;
import com.lwn.request.PageCondition;
import com.lwn.response.ResponseResult;
import com.lwn.service.PeopleService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @PostMapping("/selectPeoplesPages")
    @ApiOperation(value = "查询所有人数")
    public ResponseResult<IPage<People>> selectPeoplesPages(@RequestBody @Validated PeopleRo ro) {

        return ResponseResult.successResult(peopleService.selectPeoplesPages(ro));

    }
}
