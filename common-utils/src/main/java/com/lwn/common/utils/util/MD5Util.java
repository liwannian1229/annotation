package com.lwn.common.utils.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;

/**
 * MD5hash算法加密工具类
 */
@Slf4j
public class MD5Util {

    /**
     * 获取32位MD5签名字符串
     */
    public static String get32MD5String(byte[] bytes) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(bytes);
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取32位MD5摘要
     */
    public static String get32MD5String(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(str.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取16位MD5签名字符串
     */
    public static String get16MD5String(String str) {
        String md5_16 = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(str.getBytes());
            md5_16 = new String(new Hex().encode(bs));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        if (md5_16 != null && md5_16.length() >= 32) {
            md5_16 = md5_16.substring(8, 24);
        }

        return md5_16;
    }

    /**
     * 获取32位MD5签名字符串
     */
    public static String get32MD5String(File file) throws Exception {
        MessageDigest messagedigest = getMessageDigest();
        InputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int numRead;
            while ((numRead = fis.read(buffer)) > 0) {
                messagedigest.update(buffer, 0, numRead);
            }
            return new BigInteger(1, messagedigest.digest()).toString(16);
        } catch (IOException e) {
            throw new Exception("File load failed.", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.warn("Close file failed");
                }
            }
        }
    }

    /**
     * 验证签名
     *
     * @param data
     * @param signature 签名
     * @return
     */
    public static boolean checkMD5(String data, String signature) {
        String s = get32MD5String(data);
        return signature.equals(s);
    }

    /**
     * 生成含有随机盐的MD5密码
     *
     * @param data 明文数据
     * @return 加密后的密码
     */
    public static String getSaltMD5String(String data) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        data = get32MD5String(data + salt); //加盐后加密
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {   //把盐按照一定的规则存入加密码中
            cs[i] = data.charAt(i / 3 * 2);  //把加密后的密码按一定规则排列
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = data.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 校验密码是否正确
     *
     * @param data      要检测的数据
     * @param signature 加密后含随机盐的的密码
     * @return 是否一样
     */
    public static boolean checkSaltMD5(String data, String signature) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = signature.charAt(i);
            cs1[i / 3 * 2 + 1] = signature.charAt(i + 2);
            cs2[i / 3] = signature.charAt(i + 1);
        }
        String salt = new String(cs2);
        return Objects.equals(get32MD5String(data + salt), new String(cs1));
    }

    /**
     * 获取md5签名
     *
     * @return
     */
    private static MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Not support MD5 digest.", e);
        }
    }


}