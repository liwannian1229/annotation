package com.lwn.my.test;

import com.lwn.my.service.MyServiceApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyServiceApp.class)// 指定spring-boot的启动类
@Transactional
public abstract class BaseTest {

}
