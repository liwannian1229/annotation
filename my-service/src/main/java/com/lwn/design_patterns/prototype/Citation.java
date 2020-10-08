package com.lwn.design_patterns.prototype;

/**
 * @author liwannian
 * @date 2020/10/8 19:36
 */
public class Citation implements Cloneable {
    // 奖状类
    String name;
    String info;
    String college;

    Citation(String name, String info, String college) {
        this.name = name;
        this.info = info;
        this.college = college;
        System.out.println("奖状创建成功！");
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {

        return name;
    }

    void display() {
        System.out.println(name + info + college);
    }

    public Object clone() throws CloneNotSupportedException {

        System.out.println("奖状拷贝成功!");
        Citation citation = (Citation) super.clone();

        return citation;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Citation citation = new Citation("Mary", "同学:在2020年获得奖状一枚!", "清华大学");
        citation.display();
        Citation citation1 = (Citation) citation.clone();
        citation1.setName("张三");
        citation1.display();
    }

}
