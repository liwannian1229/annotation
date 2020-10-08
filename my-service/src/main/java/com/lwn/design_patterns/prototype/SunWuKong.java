package com.lwn.design_patterns.prototype;


import javax.swing.*;
import java.awt.*;

/**
 * @author liwannian
 * @date 2020/10/8 17:12
 */
public class SunWuKong extends JPanel implements Cloneable {

    public SunWuKong() {
        JLabel l1 = new JLabel(new ImageIcon("C:\\Users\\Administrator\\Desktop\\Wukong.jpg"));
        this.add(l1);
    }

    public Object clone() {
        SunWuKong w = null;
        try {
            w = (SunWuKong) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("拷贝悟空失败!");
        }

        return w;
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame("原型模式测试");
        // layout 布局
        // 设置布局为网格布局一行两列
        jf.setLayout(new GridLayout(1, 2));

        // 拿到容器 container
        Container container = jf.getContentPane();

        SunWuKong sunWuKong = new SunWuKong();
        container.add(sunWuKong);

        SunWuKong sunWuKong1 = (SunWuKong) sunWuKong.clone();
        container.add(sunWuKong1);
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
