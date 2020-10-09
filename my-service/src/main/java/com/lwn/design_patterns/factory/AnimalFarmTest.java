package com.lwn.design_patterns.factory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;

import java.awt.*;
import java.io.*;

public class AnimalFarmTest {

    public static void main(String[] args) {
        try {
            Animal a;
            AnimalFarm af;
            af = (AnimalFarm) ReadXML2.getObject();
            assert af != null;
            a = af.newAnimal();
            a.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    interface Animal {
        void show();
    }

    static class Horse implements Animal {
        JScrollPane sp;

        JFrame jf = new JFrame("工厂方法模式测试");

        public Horse() {
            Container container = jf.getContentPane();
            JPanel p1 = new JPanel();
            p1.setLayout(new GridLayout(1, 1));
            p1.setBorder(BorderFactory.createTitledBorder("动物:牛"));
            sp = new JScrollPane(p1);
            container.add(sp, BorderLayout.CENTER);
            JLabel l1 = new JLabel(new ImageIcon("C:\\Users\\Administrator\\Desktop\\3-1Q114140526\\A_Cattle.jpg"));
            p1.add(l1);
            jf.pack();
            jf.setVisible(false);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        @Override
        public void show() {
            jf.setVisible(true);
        }

    }

    //具体产品：牛类
    static class Cattle implements Animal {
        JScrollPane sp;
        JFrame jf = new JFrame("工厂方法模式测试");

        public Cattle() {
            Container contentPane = jf.getContentPane();
            JPanel p1 = new JPanel();
            p1.setLayout(new GridLayout(1, 1));
            p1.setBorder(BorderFactory.createTitledBorder("动物：牛"));
            sp = new JScrollPane(p1);
            contentPane.add(sp, BorderLayout.CENTER);
            JLabel l1 = new JLabel(new ImageIcon("C:\\Users\\Administrator\\Desktop\\3-1Q114140526\\A_Horse.jpg"));
            p1.add(l1);
            jf.pack();
            jf.setVisible(false);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //用户点击窗口关闭
        }

        public void show() {
            jf.setVisible(true);
        }
    }

    //抽象工厂：畜牧场
    interface AnimalFarm {
        Animal newAnimal();
    }

    //具体工厂：养马场
    static class HorseFarm implements AnimalFarm {

        @Override
        public Animal newAnimal() {
            System.out.println("新马出生！");

            return new Horse();
        }
    }

    //具体工厂：养牛场
    static class CattleFarm implements AnimalFarm {

        @Override
        public Animal newAnimal() {
            System.out.println("新牛出生！");

            return new Cattle();
        }
    }

    static class ReadXML2 {
        public static Object getObject() {
            try {
                DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = dFactory.newDocumentBuilder();
                Document doc;
                doc = builder.parse(new File("C:\\Users\\Administrator\\Desktop\\3-1Q114140526\\config2.xml"));
                NodeList nl = doc.getElementsByTagName("className");
                Node classNode = nl.item(0).getFirstChild();
                String cName = "FactoryMethod." + classNode.getNodeValue();
                System.out.println("新类名：" + cName);
                Class<?> c = Class.forName(cName);

                return c.newInstance();
            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        }
    }
}
