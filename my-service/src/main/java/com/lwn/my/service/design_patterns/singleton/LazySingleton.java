package com.lwn.my.service.design_patterns.singleton;

/**
 * @author liwannian
 * @date 2020年10月8号
 * 懒汉式 单例
 */
public class LazySingleton {

    // volatile,synchronized,final 保证线程之间是可见的.即一个线程对其改变,其他线程知道
    private static volatile LazySingleton instance = null;

    private LazySingleton() {
        System.out.println("懒汉式单例模式");
    }

    public LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }

        return instance;
    }
}
