package com.lwn.simple;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.lwn.common.*;
import com.lwn.model.entity.Student;
import org.junit.Test;

import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class TestOne {

    @Test
    public void testBase() {

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

        // JsonUtil
        String s1 = JsonUtil.toJson(student);
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

        System.out.println(MD5Util.getMD5String("12345678"));
    }
}
