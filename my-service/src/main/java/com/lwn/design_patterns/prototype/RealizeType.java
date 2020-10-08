package com.lwn.design_patterns.prototype;

/**
 * @author liwannian
 * @date 2020/10/8 16:53
 */
class RealizeType implements Cloneable {

    RealizeType() {
        System.out.println("具体原型创建成功");
    }

    public Object clone() throws CloneNotSupportedException {
        System.out.println("具体原型复制成功");

        return (RealizeType) super.clone();
    }

    public static class ProtoTypeTest {
        public static void main(String[] args) throws CloneNotSupportedException {

            RealizeType realizeType = new RealizeType();
            RealizeType realizeType1 = (RealizeType) realizeType.clone();
            System.out.println("realizeType==realizeType1:" + (realizeType == realizeType1));
        }
    }
}
