package com.lxc.learn.jdk.image;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/25
 */
@Slf4j
public class GrayPicture {

    public static void main(String[] args) {
        //transformGray_R("C:\\Users\\vpclub\\Desktop\\timg.jpg","./1.jpg");
        clearBackground("C:\\Users\\vpclub\\Desktop\\timg.jpg","./123.png");
    }

    /**
     * @param imagePath 要灰化图像的名字
     * @param path 生成的图像的名字
     *
     */
    public static void transformGray_R(String imagePath, String path) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(imagePath));
            for(int y = image.getMinY(); y  < image.getHeight(); y++) {
                for(int x = image.getMinX(); x < image.getWidth(); x ++) {
                    int pixel = image.getRGB(x, y);
                    int r = (pixel >> 16) & 0x00ff;
                    pixel = (r & 0x000000ff) | (pixel & 0xffffff00); //用r的值设置b的值
                    pixel = ((r<<8) & 0x0000ff00) | (pixel & 0xffff00ff);//用r的值设置g的值
                    image.setRGB(x, y, pixel);
                }
            }
            try {
                ImageIO.write(image, "jpg", new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 透明化

     alpha的值也是在0~255之间，当alpha为0时，则图片完全透明，为255时不透明，所以其值越小越透明，由此可知，保留想要的颜色，透明掉不想要的颜色是很简单的。
     处理方法和灰化方法一样，这次修改的是alpha值。通用的方法是加权法，也可以根据图片颜色的类型选择合适的方法。
     下面代码以加权值为alpha值，加权值得到的图片的亮度，所以，越黑的地方，亮度越小，其alpha值最小，越透明。所以，此方法是可行的。
     */
    public static void clearBackground(String imagePath, String dstPath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image srcImage = icon.getImage();
        BufferedImage dstImage = new BufferedImage(srcImage.getWidth(icon.getImageObserver()), srcImage.getHeight(icon.getImageObserver()), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics gr = dstImage.getGraphics();
        gr.drawImage(srcImage, 0,0,icon.getImageObserver());
        int height = dstImage.getHeight();
        int width = dstImage.getWidth();
        for(int y = dstImage.getMinY(); y < height; y++) {
            for(int x = dstImage.getMinX(); x < width; x++) {
                int pixel = dstImage.getRGB(x, y);
                int r = (pixel & 0x00ff0000) >> 16;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;
                int liangDu = (int)(0.21f * 4 + 0.71f * g + 0.08f * b);//获取亮度
                //以亮度作为alpha值
                pixel = (liangDu << 24) & 0xff000000 | (pixel & 0x00ffffff); //alpha值在24~32
                dstImage.setRGB(x, y, pixel);
                //dstImage.setRGB(x, y, Integer.parseInt("-1111111000000000000000000000000",2));
            }
        }

        try {
            ImageIO.write(dstImage, "png", new File(dstPath));//不要忘记更改图片格式为png格式，jpg不支持透明
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        Integer i = Integer.parseInt("-1111111000000000000000000000000",2);
        String a = Integer.toBinaryString(i);
        System.out.println(a);
    }
}
