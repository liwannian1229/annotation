package com.lwn.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lwn.annotation.TokenValidator;
import com.lwn.model.entity.People;
import com.lwn.model.ro.PeopleRo;
import com.lwn.response.ResponseResult;
import com.lwn.service.PeopleService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/deletePeople")
    @ApiOperation(value = "删除人类")
    @TokenValidator
    public ResponseResult<IPage<People>> deletePeople(@RequestParam("id") Long id) {

        peopleService.deletePeople(id);

        return ResponseResult.successResult();

    }
}
