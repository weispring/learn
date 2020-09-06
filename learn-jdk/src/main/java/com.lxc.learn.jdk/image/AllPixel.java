package com.lxc.learn.jdk.image;

import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/24
 */
@Slf4j
public class AllPixel {

    /**
     * 32bit 位深 从高到低位分别标识 透明度、r、g、b各8位
     * @param args
     */
    public static void main(String[] args) {
        String image = "C:\\Users\\vpclub\\Desktop\\无标题.png";
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();
        System.out.println("width=" + width + ",height=" + height + ".");
        System.out.println("minx=" + minx + ",miniy=" + miny + ".");
        List<Pixel> list = new ArrayList(width * height);
        List<Pixel> alist = new ArrayList(width * height);
        Set set = new HashSet();
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                list.add(new Pixel(pixel(pixel)));
                alist.add(new Pixel(apixel(pixel)));
                set.add(pixel);
            }
        }

        Map<String, List<Pixel>> result = list.stream().collect(
                Collectors.groupingBy(e->e.getValue())
        );
        Map<String, List<Pixel>> aresult = alist.stream().collect(
                Collectors.groupingBy(e->e.getValue())
        );
        for (Map.Entry<String, List<Pixel>> entry : result.entrySet()){
            log.info("{}:{}", entry.getKey(), entry.getValue().size());
        }
        // 6%
        System.out.println(list);
    }


    public static String pixel(int pixel){
        int[] rgb = new int[3];
        rgb[0] = (pixel & 0xff0000) >> 16;
        rgb[1] = (pixel & 0xff00) >> 8;
        rgb[2] = (pixel & 0xff);
        String rgbColor = "(" + rgb[0] + ","
                + rgb[1] + "," + rgb[2] + ")";
        //System.out.println(rgbColor);
        return rgbColor;
    }


    public static String apixel(int pixel){
        int[] rgb = new int[3];
        int a = ((pixel & 0xff000000) >>> 24);
        rgb[0] = (pixel & 0xff0000) >> 16;
        rgb[1] = (pixel & 0xff00) >> 8;
        rgb[2] = (pixel & 0xff);
        String rgbColor = a + "(" + rgb[0] + ","
                + rgb[1] + "," + rgb[2] + ")";
        //System.out.println(rgbColor);
        return rgbColor;
    }
}
