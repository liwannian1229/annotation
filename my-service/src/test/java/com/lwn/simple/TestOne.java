package com.lwn.simple;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lwn.common.AppleUtil;
import com.lwn.common.CommonUtil;
import com.lwn.common.JsonUtil;
import com.lwn.common.MD5Util;
import com.lwn.model.entity.People;
import com.lwn.model.entity.Student;
import com.lwn.testClass.CustomConverter;
import com.lwn.thread.ThreadUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class TestOne {

    @Test
    public void testBase() {

    }

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
        org.apache.commons.lang.StringUtils.isBlank(""); // obj.trim().length()==0
        org.apache.commons.lang.StringUtils.isEmpty("");

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
    public void testToJson() {
        Student student = new Student();
        student.setName("wannian");

        // 谷歌的Gson转json
        Gson gson = new Gson();
        String s = gson.toJson(student);
        System.out.println("Gson:" + s);

        // 阿里巴巴的fastJson转json
        Object o = JSON.toJSON(student);
        System.out.println("alibaba Object:" + o);

        String s1 = JsonUtil.toJson(student);
        String s2 = JSON.toJSONString(student);
        System.out.println("alibaba string" + s1);

        // 是否相等
//        System.out.println(s == JSON.toJSON(s));
    }

    @Test
    public void testIdentityToken() {

        String identityToken = "eyJraWQiOiJlWGF1bm1MIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLnl3c3kuaW9zLmRlbW8iLCJleHAiOjE1ODY5NDY5NzAsImlhdCI6MTU4Njk0NjM3MCwic3ViIjoiMDAwMzI3LmNkMDBlMzk3NGVhODQwMmRiZTNhMzNlNjg2N2YxZWU2LjEwMDYiLCJjX2hhc2giOiJsQTFkcDlZMnZBVzlFQXlkSWw2MVh3IiwiZW1haWwiOiI5ZXpyMmszaDZzQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTg2OTQ2MzcwLCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.GqZFKMQ3KTG42x2-W7r69nnYqqoBHszI4LBI7m7ysyqBRyt1XSGDPy440F153C8x05VgZgkYi0mIZheCIenIMl5R0unOKrXhvHihgwIKtuvPClRQmAyZYxOWct8xGoPvrRpZr4AkJwxauUxaY8NIoV8-UrNduQcjW8-63-wF9B0F-2p61WZuOEmCoULj2aW7fBoRgFylGbQpXAU_8t32fj1JG3OJzErDJsi1P1CJyKaamd-UpVmgwyaCl0nXMnX0CB0ERqb76M67BHY0ji3VBuIp3uZczEEJMzFtgAevOfgoNYRFicVBr25XoyaWYPxZgYnI-AeUQgvnwHaacx4bkg";

        System.out.println(AppleUtil.parserIdentityToken(identityToken));
    }

    @Test
    public void testGenerateNum() {
       /* StringBuilder stringBuilder = CommonUtil.generateRandomNumber(11);
        System.out.println(stringBuilder);*/
//        String s = ImageVerCodeUtil.generateVerifyCode(6);
//        System.out.println(s);

        //JSONArray authKeys = AppleUtil.getAuthKeys();
        //System.out.println(authKeys);

//        System.out.println(RegexUtils.hasSpecialChar("@#$123"));
//        System.out.println(RegexUtils.isPhoneNumber("3000000000"));
        // System.out.println(RegexpUtils.isHardRegexpValidate("D:\\0.jpg", RegexpUtils.ICON_REGEXP));

        String token = "annotation_lwn_";
        token += CommonUtil.generateRandomNumber(new Random().nextInt((int) (Math.random() * 10) + 1));
        token += ((System.currentTimeMillis()) + "").substring(0, 8) + CommonUtil.getUUID();
        token = MD5Util.getMD5String(token);
        // encoder编码器,decoder解码器
        System.out.println(Base64.getEncoder().encodeToString((token == null ? "" : token).getBytes()));

        System.out.println("编码之前:" + token);
        System.out.println("编码之后:" + Base64.getEncoder().encodeToString((token.getBytes())));

        System.out.println("解码之后:" + JsonUtil.toJson(Base64.getDecoder().decode(Base64.getEncoder().encodeToString((token.getBytes())))));
    }

    @Test
    public void testPwd() {

//        System.out.println(MD5Util.getMD5String("12345678"));
//        People people = new People();
//        people.setDel(true);
//        people.setUserId(4L);
//        people.setName("哈哈哈");
//        people.setId(4L);
//        System.out.println(JsonUtil.toJson(people));
        String token = "annotation_lwn_";
        // new Random()的nextInt(a)方法,随机生成一个[0,a)的整数,Math.random()随机生成一个[0,1)的小数
        token += CommonUtil.getUUID().substring(0, 8);
        token += ((System.currentTimeMillis()) + "").substring(0, 8);
        token = MD5Util.getMD5String(token);

        // encoder编码器,decoder解码器
        String s = Base64.getEncoder().encodeToString((token == null ? "" : token).getBytes());
        System.out.println(s);
    }

    @Test
    public void testTimeStamp() {

        System.out.println("开始时间:" + System.currentTimeMillis() / 1000);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束时间:" + DateTime.now().getMillis() / 1000);
    }

    // 测试三种Collection包的方法
    @Test
    public void testCollections() {
        List<String> list = new ArrayList<>();
        System.out.println("-----------------------------------测试两个Collections包---下面两行为true----------------------------------------");
        System.out.println(Collections.emptyList().equals(list));
        System.out.println(io.jsonwebtoken.lang.Collections.isEmpty(list));
        list.add("1");
        System.out.println("-----------------------------------测试三个CollectionUtils包---以下全是false----------------------------------------");
        System.out.println(CollectionUtils.isEmpty(list));
        System.out.println(org.springframework.util.CollectionUtils.isEmpty(list));
        System.out.println(com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isEmpty(list));

        List<String> newEmptyList = new ArrayList<>(Collections.emptyList());
        newEmptyList.add(0, "第一位是什么呢");
        newEmptyList.add(1, "第二位是什么呢");
        newEmptyList.add(2, "第三位是什么呢");
        System.out.println(newEmptyList);
        // delimiter 分隔符,separator 分离器
        System.out.println(String.join(",", newEmptyList));
        System.out.println(StringUtils.join(newEmptyList, ","));
        System.out.println(org.apache.commons.lang.StringUtils.join(newEmptyList, ","));
    }

    @Test
    public void testShuffle() {
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        // 打乱顺序
        Collections.shuffle(strings);
        System.out.println(strings);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(strings.get(new Random().nextInt(strings.size())));
        }
        Collections.shuffle(list);
        System.out.println(list);
    }

    @Test
    public void testHuTool() {
        // 测试 HuTool 克隆
//        People people = new People();
//        people.setId(1L);
//        people.setName("测试");
//        people.setUserId(1L);
//        people.setDel(false);
//
//        People people1 = ObjectUtil.cloneByStream(people); // 深度克隆
//        People people2 = ObjectUtil.cloneIfPossible(people);
//        People clone = ObjectUtil.clone(people);
//
//        System.out.println(JsonUtil.toJson(people1));
//        System.out.println(JsonUtil.toJson(people2));
//        System.out.println(JsonUtil.toJson(clone));
        // 测试 HuTool Convert转化类
//        String a = "1";
//        Integer integer = Convert.toInt(a);
//        System.out.println(integer);
//
//        String date = "2020-04-25 12:23:45";
//        System.out.println(DateUtil.date(Convert.toDate(date, new Date())));
//
//        Object[] o = {"1", 2, new DateTime()};
//        List<String> strings = Convert.toList(String.class, o);
//        System.out.println(JsonUtil.toJson(strings));
//        String s = "这也是醉了呀";
//        String hex = Convert.toHex(s, CharsetUtil.CHARSET_UTF_8);
//        System.out.println(hex);
//        System.out.println(Convert.hexToStr(hex, CharsetUtil.CHARSET_UTF_8));

//        long s = 3600 * 1000;
//        System.out.println(Convert.convertTime(s, TimeUnit.MILLISECONDS, TimeUnit.HOURS));

        double d = 67556.32;

//结果为："陆万柒仟伍佰伍拾陆元叁角贰分"
//        System.out.println(Convert.digitToChinese(d));
// 包装类和基本类型之间的转换
        //去包装
//        Class<?> wrapClass = Integer.class;

//结果为：int.class
//        Class<?> unWraped = Convert.unWrap(wrapClass);
//        System.out.println(unWraped);

//包装
//        Class<?> primitiveClass = long.class;

//结果为：Long.class
//        Class<?> wraped = Convert.wrap(primitiveClass);
//        System.out.println(wraped);

        // 自定义的转化
//        int a = 3423;
//        ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
//        String result = converterRegistry.convert(String.class, a);

        // 检查两者是否相等,不相等会报异常,相等则没有任何反应
//        Assert.assertEquals("3423", result);
        ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
//此处做为示例自定义String转换，因为Hutool中已经提供String转换，请尽量不要替换
//替换可能引发关联转换异常（例如覆盖String转换会影响全局）
        converterRegistry.putCustom(String.class, CustomConverter.class);
        int a = 454553;
        String result = converterRegistry.convert(String.class, a);
        System.out.println(result);
        Assert.assertEquals("Custom:454553", result);

    }

    @Test
    public void testHuToolDate() {

//        System.out.println(DateUtil.ageOfNow("19971229"));
//        System.out.println(cn.hutool.core.date.DateTime.now());
        long start = System.currentTimeMillis();
        TimeInterval timeInterval = new TimeInterval();

        // 参数只要是一个函数式接口(即FunctionalInterface),且只有一个(没有入参的)抽象方法,那么就可以使用()->{}这种lambda表达式
        ThreadUtils.executeCachedThread(() -> {
            System.out.println(1111111);
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        long interval = timeInterval.interval();
        System.out.println(end - start);
        System.out.println(interval);

        String s = ThreadUtils.lookMe(() -> "1");
        System.out.println(s);
    }

}
