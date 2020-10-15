package com.lwn.my.service.design_patterns.prototype;

/**
 * @author liwannian
 * @date 2020/10/8 16:53
 */
public class RealizeType implements Cloneable {

    RealizeType() {
        System.out.println("具体原型创建成功");
    }

    public Object clone() throws CloneNotSupportedException {
        System.out.println("具体原型复制成功");

        return super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {

        RealizeType realizeType = new RealizeType();
        RealizeType realizeType1 = (RealizeType) realizeType.clone();
        System.out.println("realizeType==realizeType1:" + (realizeType == realizeType1));
    }
}
