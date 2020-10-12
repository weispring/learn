package com.lxc.learn.jdk.image;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/12
 */
@Slf4j
public class WaterMark {

    public static void addWaterMark(String path, String target) {
        Color color = new Color(255, 200, 0, 118);   // 水印颜色
        Font font = new Font("微软雅黑", Font.ITALIC, 45);  //水印字体
//        String waterMarkContent="图片来源：http://www.baidu.com/zjq_1314520";
        String waterMarkContent="面试资料";   //水印内容
        String tarImgPath = path;  //原图片
        try {
            File file = new File(target);  //存储目标路径
            BufferedImage buImage = ImageIO.read(file);
            int width = buImage.getWidth(); //图片宽
            int height = buImage.getHeight(); //图片高

            //添加水印
            BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(buImage, 0, 0, width, height, null);
            g.setColor(color); //水印颜色
            g.setFont(font); //水印字体

            int x = width -2*getWatermarkLength(waterMarkContent, g);  //这是一个计算水印位置的函数，可以根据需求添加
            int y = height - 1*getWatermarkLength(waterMarkContent, g);
            g.drawString(waterMarkContent, 400, 300); //水印位置
            g.dispose(); //释放资源

            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufferedImage, "jpg", outImgStream);
            System.out.println("添加水印完成");
            outImgStream.flush();
            outImgStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    public static void main(String[] args) {
        String source = "";
        String target = "";
        addWaterMark(source, target);
    }


}
