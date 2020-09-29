package com.lwn.more;

import com.lwn.common.AppleUtil;
import com.lwn.common.ImageVerCodeUtil;
import com.lwn.common.JsonUtil;
import com.lwn.enumeration.ApiCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试jwt用法
 */

public class TestJwt {

    // 生成token
    @Test
    public void testCreateToken() {

        JwtBuilder builder = Jwts.builder().setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "it cast");

        System.out.println(builder.compact());

    }

    // 解析出token
    @Test
    public void testParseToken() {

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1MjM0MTM0NTh9.gq0J-cOM_qCNqU_s-d_IrRytaNenesPmqAIhQpYXHZk";
        Claims claims = Jwts.parser().setSigningKey("it cast").parseClaimsJws(token).getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("IssuedAt:" + claims.getIssuedAt());
    }

    // 生成有过期时间的token
    @Test
    public void testCreateExpireTimeToken() {

        //为了方便测试，我们将过期时间设置为1分钟
        long now = System.currentTimeMillis();//当前时间
        long exp = now + 1000 * 60;//过期时间为1分钟
        JwtBuilder builder = Jwts.builder().setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "it  cast")
                .setExpiration(new Date(exp));
        System.out.println(builder.compact());

    }

    // 解析出有过期时间的token
    @Test
    public void testParseExpireTimeToken() {

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1MjM0MTM0NTh9.gq0J-cOM_qCNqU_s-d_IrRytaNenesPmqAIhQpYXHZk";
        Claims claims = Jwts.parser().setSigningKey("it cast").parseClaimsJws(token).getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("IssuedAt:" + claims.getIssuedAt());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        System.out.println("签发时间:" + sdf.format(claims.getIssuedAt()));
        System.out.println("过期时间:" + sdf.format(claims.getExpiration()));
        System.out.println("当前时间:" + sdf.format(new Date()));
    }

    // 自定义claims,存储更多信息
    @Test
    public void testMyCreateToken() {

        //为了方便测试，我们将过期时间设置为1分钟
        long now = System.currentTimeMillis();//当前时间
        long exp = now + 1000 * 60;//过期时间为1分钟
        JwtBuilder builder = Jwts.builder().setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "it cast")
                .setExpiration(new Date(exp))
                .claim("roles", "admin")
                .claim("logo", "logo.png");
        System.out.println(builder.compact());
    }

    // 解析出自定义生成的token
    @Test
    public void testMyParseToken() {

        String compactJws = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1MjM0MTczMjMsImV4cCI6MTUyMzQxNzM4Mywicm9sZXMiOiJhZG1pbiIsImxvZ28iOiJsb2dvLnBuZyJ9.b11p4g4rE94rqFhcfzdJTPCORikqP_1zJ1MP8KihYTQ";
        Claims claims = Jwts.parser().setSigningKey("it cast").parseClaimsJws(compactJws).getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("roles:" + claims.get("roles"));
        System.out.println("logo:" + claims.get("logo"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        System.out.println("签发时间:" + sdf.format(claims.getIssuedAt()));
        System.out.println("过期时间:" + sdf.format(claims.getExpiration()));
        System.out.println("当前时间:" + sdf.format(new Date()));
    }

    @Test
    public void testGenerateNum() {
       /* StringBuilder stringBuilder = CommonUtil.generateRandomNumber(11);
        System.out.println(stringBuilder);*/
        String s = ImageVerCodeUtil.generateVerifyCode(6);
        System.out.println(s);

    }

    @Test
    public void testIdentityToken() {

        String identityToken = "eyJraWQiOiJlWGF1bm1MIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLnl3c3kuaW9zLmRlbW8iLCJleHAiOjE1ODY5NDY5NzAsImlhdCI6MTU4Njk0NjM3MCwic3ViIjoiMDAwMzI3LmNkMDBlMzk3NGVhODQwMmRiZTNhMzNlNjg2N2YxZWU2LjEwMDYiLCJjX2hhc2giOiJsQTFkcDlZMnZBVzlFQXlkSWw2MVh3IiwiZW1haWwiOiI5ZXpyMmszaDZzQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTg2OTQ2MzcwLCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.GqZFKMQ3KTG42x2-W7r69nnYqqoBHszI4LBI7m7ysyqBRyt1XSGDPy440F153C8x05VgZgkYi0mIZheCIenIMl5R0unOKrXhvHihgwIKtuvPClRQmAyZYxOWct8xGoPvrRpZr4AkJwxauUxaY8NIoV8-UrNduQcjW8-63-wF9B0F-2p61WZuOEmCoULj2aW7fBoRgFylGbQpXAU_8t32fj1JG3OJzErDJsi1P1CJyKaamd-UpVmgwyaCl0nXMnX0CB0ERqb76M67BHY0ji3VBuIp3uZczEEJMzFtgAevOfgoNYRFicVBr25XoyaWYPxZgYnI-AeUQgvnwHaacx4bkg";

        System.out.println(AppleUtil.parserIdentityToken(identityToken));


    }
    @Test
    public void testApiCode(){

    }
}
