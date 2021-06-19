package com.lxc.learn.jdk.image;

import lombok.extern.slf4j.Slf4j;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 图片居中加水印
 * @author lixianchun
 * @description
 * @date 2020/10/12
 */
@Slf4j
public class WaterMark {

    public static void addWaterMark(String path, String target) {
        Color color = new Color(255, 200, 0, 118);   // 水印颜色
        Font font = new Font("微软雅黑", Font.ITALIC, 45);  //水印字体
        String waterMarkContent="测试数据";   //水印内容
        try {
            File file = new File(path);  //存储目标路径
            BufferedImage buImage = ImageIO.read(file);
            int width = buImage.getWidth(); //图片宽
            int height = buImage.getHeight(); //图片高
            //添加水印
            BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(buImage, 0, 0, width, height, null);
            g.setColor(color); //水印颜色
            g.setFont(font); //水印字体

            int x = (width -getWordWidth(font, waterMarkContent)) / 2;  //这是一个计算水印位置的函数，可以根据需求添加
            int y = (height - getWordHeight(font, waterMarkContent)) /2;
            g.drawString(waterMarkContent, x, y); //水印位置
            g.dispose(); //释放资源
            log.info("w:{}, h:{}, x:{}, y:{}", width, height, x, y);
            FileOutputStream outImgStream = new FileOutputStream(target);
            ImageIO.write(bufferedImage, "jpg", outImgStream);
            log.info("添加水印完成");
            outImgStream.flush();
            outImgStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
          int length = g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.toCharArray().length);
          log.info("water x: {}", length);
          return length;
    }

    private static int getWatermarkHight(String waterMarkContent, Graphics2D g) {
        //文字高度计算任有问题
        int h = g.getFontMetrics(g.getFont()).getHeight();
        log.info("water y: {}", h);
        return h;
    }


    public static int getWordWidth(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = 0;
        for (int i = 0; i < content.length(); i++) {
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

    public static int getWordHeight(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int height = metrics.getHeight();
        return height;
    }



    public static void main(String[] args) {
        String source = "C:\\Users\\vpclub\\Desktop\\timg.jpg";
        String target = "C:\\Users\\vpclub\\Desktop\\123.png";
        addWaterMark(source, target);
    }


}
