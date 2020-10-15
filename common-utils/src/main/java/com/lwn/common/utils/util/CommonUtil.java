package com.lwn.common.utils.util;

import com.alibaba.fastjson.JSON;

import java.util.Random;
import java.util.UUID;

/**
 * @author liwannian
 * @since 2020年9月11日17点58分17点58分
 */

public class CommonUtil {

    // 十进制数字转为二进制
    public static StringBuilder TenToTwoOutPut(int ten) {

        StringBuilder outPrint = new StringBuilder();
        do {
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

        return outPrint.reverse();
    }

    // 获取uuid
    public static String getUUID() {

        return UUID.randomUUID().toString().replace("-", "");
    }

    // 获取16位订单号
    public static StringBuilder getOrderNo() {

        final String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final String number = "0123456789";

        Random random = new Random();
        StringBuilder orderNo = new StringBuilder((letter.charAt(random.nextInt(letter.length())) + "") + (letter.charAt(random.nextInt(letter.length())) + ""));
        for (int i = 0; i < 16; i++) {
            String c = number.charAt(random.nextInt(number.length())) + "";
            orderNo.append(c);
        }

        return orderNo;
    }

    // 是否为空
    public static boolean isEmpty(String str) {

        return str == null || str.trim().length() == 0;
    }

    // 是否不为空
    public static boolean isNotEmpty(String str) {

        return !isEmpty(str);
    }

    // json字符串 转为对象
    public static <T> T jsonToObject(String json, Class<T> clazz) {

        return JSON.parseObject(json, clazz);
    }

    // 对象转化为字符串
    public static String objectToJsonString(Object obj) {

        return JSON.toJSONString(obj);
    }

    // 随机生成11位手机号
    public static StringBuilder generatePhone() {
        final String first = "1";
        // final String[] seconds = {"3", "4", "5", "6", "7", "8", "9"};
        final String second = "3456789";
        // final String[] bodies = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        final String body = "0123456789";
        StringBuilder phone = new StringBuilder();
        Random random = new Random();
        phone.append(first);
        // phone.append(seconds[random.nextInt(seconds.length)]);
        phone.append(second.charAt(random.nextInt(second.length())));// [0,7)->[0,6]
        for (int i = 0; i < 9; i++) {
            // phone.append(bodies[random.nextInt(body.length())]);
            phone.append(body.charAt(random.nextInt(body.length())));
        }

        return phone;
    }

    // 获取一个指定长度的随机字符串
    public static StringBuilder generateRandomNumber(int numberLength) {
        if (numberLength <= 0) {
            numberLength = 1;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberLength; i++) {
            sb.append((int) (Math.random() * 10));
        }

        return sb;
    }
}
