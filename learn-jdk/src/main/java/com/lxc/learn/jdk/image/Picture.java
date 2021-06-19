package com.lxc.learn.jdk.image;


import lombok.extern.slf4j.Slf4j;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * @Auther: lixianchun
 * @Date: 2020/3/3 14:34
 * @Description:
 */
@Slf4j
public class Picture{
    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }
    public static BufferedImage mergeImage(BufferedImage img1,
                                           BufferedImage img2, boolean isHorizontal) {
        int w1 = img1.getWidth();
        int h1 = img1.getHeight();
        int w2 = img2.getWidth();
        int h2 = img2.getHeight();
        // 从图片中读取RGB
        int[] ImageArrayOne = new int[w1 * h1];
        ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
        int[] ImageArrayTwo = new int[w2 * h2];
        ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);

        // 生成新图片
        BufferedImage DestImage = null;
        if (isHorizontal) { // 水平方向合并
            DestImage = new BufferedImage(w1 + w2, h1 > h2 ? h1 : h2, BufferedImage.TYPE_INT_RGB);
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            DestImage.setRGB(w1, 0, w2, h2, ImageArrayTwo, 0, w2);
        } else { // 垂直方向合并
            DestImage = new BufferedImage(w1 > w2 ? w1 : w2, h1 + h2, BufferedImage.TYPE_INT_RGB);
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            DestImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2); // 设置下半部分的RGB
        }

        return DestImage;
    }
    /**
     * 生成新图片到本地
     */
    public void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
    }

    /**
     * 远程图片转BufferedImage
     * @param destUrl  远程图片地址
     * @return
     */
    public static BufferedImage getBufferedImageDestUrl(String destUrl) {
        HttpURLConnection conn = null;
        BufferedImage image = null;
        try {
            URL url = new URL(destUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == 200) {
                image = ImageIO.read(conn.getInputStream());
                return image;
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            conn.disconnect();
        }
        return image;
    }

    public static void main(String[] args) {
        Picture tt = new Picture();
        BufferedImage d = tt.loadImageLocal("C:\\Users\\lixianchun\\Desktop\\李显春健康打卡.jpg");
        BufferedImage b = tt.loadImageLocal("C:\\Users\\lixianchun\\Desktop\\李显春.jpg");
        tt.writeImageLocal("C:\\Users\\lixianchun\\Desktop\\33.jpg", tt.mergeImage(b, d,true));
        //将多张图片合在一起
        System.out.println("success");
    }
}
