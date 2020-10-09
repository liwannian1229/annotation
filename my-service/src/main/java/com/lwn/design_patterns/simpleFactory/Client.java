package com.lwn.design_patterns.simpleFactory;

import java.util.Objects;

public class Client {

    public interface Product {
        // 接口中的所有方法都是公共的,抽象的
        void show();
    }

    public static class Product_1 implements Product {
        @Override
        public void show() {
            System.out.println("产品一展示!");
        }
    }

    public static class Product_2 implements Product {
        @Override
        public void show() {
            System.out.println("产品二展示!");
        }
    }

    static final class Const {
        static final int PRODUCT_1 = 1;
        static final int PRODUCT_2 = 2;
    }

    static class SimpleFactory {
        public static Product makeProduct(int kind) {
            // kind可以是byte,short,int,char,String
            switch (kind) {
                case Const.PRODUCT_1:
                    return new Product_1();

                case Const.PRODUCT_2:
                    return new Product_2();

                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) {
        Objects.requireNonNull(SimpleFactory.makeProduct(1)).show();
    }
}
