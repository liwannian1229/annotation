package com.lwn.my.service.testClass;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 截屏
 *
 * @since 2020 10.19 18点17分
 */
public class ScreenShot {

    private final String filePreStr; // 默认前缀（选择存储路径例如： D：\\）
    //    private static int serialNum = 0;  //截图名称后面的数字累加
    private static final Long serialNum = System.currentTimeMillis();  //截图名称
    private final String imageFormat; // 图像文件的格式
    private final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); //获取全屏幕的宽高尺寸等数据

    public ScreenShot() {
        // 默认截图名称
        filePreStr = "cameraImg";
        //截图后缀
        imageFormat = "png";
    }

    public ScreenShot(String prefixName, String format) {
        filePreStr = prefixName;
        imageFormat = format;
    }

    public void snapShot() {
        try {
            // *** 核心代码 *** 拷贝屏幕到一个BufferedImage对象screenshot
            BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(0, 0, (int) dimension.getWidth(), (int) dimension.getHeight()));
//            serialNum++;
            // 根据文件前缀变量和文件格式变量，自动生成文件名
            String fileName = filePreStr + serialNum + "." + imageFormat;
            File output = new File(fileName);
            System.out.println("保存的文件名:" + fileName);
            // 将screenshot对象写入图像文件
            ImageIO.write(screenshot, imageFormat, output);
            System.out.println("保存成功!");
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }

    // 运行之后，即可将全屏幕截图保存到指定的目录下面<br>　　　　
    // 配合前端页面上面的选择尺寸等逻辑，传到后台，即可实现自由选择截图区域和大小的截图<br>
    public static void main(String[] args) {
        ScreenShot cam = new ScreenShot("d:\\", "png");
        cam.snapShot();
    }
}
