package com.lwn.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lwn.annotation.TokenValidator;
import com.lwn.model.entity.People;
import com.lwn.model.ro.AddPeopleRo;
import com.lwn.model.ro.PeopleRo;
import com.lwn.model.ro.UpdatePeopleRo;
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
    public ResponseResult<String> deletePeople(@RequestParam("id") Long id) {

        peopleService.deletePeople(id);

        return ResponseResult.successResult();

    }

    @PostMapping("/insertPeople")
    @ApiOperation(value = "新增人类")
    @TokenValidator
    public ResponseResult<String> insertPeople(@RequestBody @Validated AddPeopleRo ro) {

        peopleService.insertPeople(ro);

        return ResponseResult.successResult();

    }

    @PostMapping("/updatePeople")
    @ApiOperation(value = "修改人类")
    @TokenValidator
    public ResponseResult<String> insertPeople(@RequestBody @Validated UpdatePeopleRo ro) {

        peopleService.updatePeople(ro);

        return ResponseResult.successResult();

    }
}
