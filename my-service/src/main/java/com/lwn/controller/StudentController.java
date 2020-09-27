package com.lwn.controller;

import com.lwn.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
}
