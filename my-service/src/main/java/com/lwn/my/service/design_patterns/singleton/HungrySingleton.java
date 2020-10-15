package com.lwn.my.service.design_patterns.singleton;

/**
 * @author liwannian
 * @date 2020年10月8号
 * 饿汉式 单例
 */
public class HungrySingleton {

    private static volatile HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
        System.out.println("这是饿汉式单例");
    }

    public HungrySingleton getInstance() {

        return instance;
    }


}
