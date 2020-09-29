package com.lwn.test;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lwn.BaseTest;
import com.lwn.common.CommonUtil;
import com.lwn.common.JsonUtil;
import com.lwn.model.entity.Student;
import com.lwn.auth.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
public class TestUtils extends BaseTest {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void testA() {
        int a = 5; // 0000 0101
        int b = 3; // 0000 0011
        a &= b; // 0000 0001
        //  System.out.println(Integer.toBinaryString(a));

        // 十进制转二进制输出
        output(5);
    }

    public void output(int ten) {
        StringBuilder outPrint = new StringBuilder();
        int count = 0;
        int begin = ten;
        do {
            count++;// 循环次数
            int out = ten % 2;
            if (out == 0) {
                ten = ten / 2;
                outPrint.append(0);
            } else {
                int temp = (ten - out) / 2;
                if (temp != 0) {
                    ten = temp;
                    outPrint.append(out);
                } else {
                    outPrint.append(out);
                    break;
                }
            }
        } while (true);
        outPrint.reverse();
        System.out.println("循环次数: " + count);
        System.out.println("十进制数: " + begin + " 转二进制: " + outPrint);
    }

    // 16位订单号
    @Test
    public void getOrderNo() {

        final String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final String number = "0123456789";
        Random random = new Random();
        StringBuilder orderNo = new StringBuilder((letter.charAt(random.nextInt(letter.length())) + "") + (letter.charAt(random.nextInt(letter.length())) + ""));
        for (int i = 0; i < 16; i++) {
            String c = number.charAt(random.nextInt(number.length())) + "";
            orderNo.append(c);
        }
        System.out.println("订单号:" + orderNo);
    }

    @Test
    public void testHttpReq() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert sra != null;
        HttpServletRequest req = sra.getRequest();
        StringUtils.isBlank(""); // obj.trim().length()==0
        StringUtils.isEmpty("");

    }

    // 随机生成11位手机号
    @Test
    public void testGeneratePhone() {
        final String first = "1";
        // final String[] seconds = {"3", "4", "5", "6", "7", "8", "9"};
        final String second = "3456789";
        // final String[] bodies = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        final String body = "0123456789";
        StringBuilder phone = new StringBuilder();
        Random random = new Random();
        phone.append(first);
        // phone.append(seconds[random.nextInt(seconds.length)]);
        phone.append(second.charAt(random.nextInt(second.length())));
        for (int i = 0; i < 9; i++) {
            //    phone.append(bodies[random.nextInt(body.length())]);
            phone.append(body.charAt(random.nextInt(body.length())));
        }
        System.out.println("长度为:" + phone.length());
        System.out.println(phone);
    }

    @Test
    public void testPrefix() {
        String key = redisUtils.prefix("key");

        System.out.println(key);
    }
}
