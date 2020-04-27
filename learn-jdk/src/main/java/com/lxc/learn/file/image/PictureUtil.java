package com.lxc.learn.file.image;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: lixianchun
 * @Date: 2020/3/3 14:34
 * @Description:
 */
@Slf4j
public class PictureUtil {
    /**
     * 导入本地图片到缓冲区
     */
    private static BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }
    private static BufferedImage mergeImage(BufferedImage img1,
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
    private static void writeImageLocal(String newImage, BufferedImage img) {
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
    private static BufferedImage getBufferedImageDestUrl(String destUrl) {
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

    public static String mergePicture(String url1, String url2){
        BufferedImage a = getBufferedImageDestUrl(url1);
        BufferedImage b = getBufferedImageDestUrl(url2);
        String path = "./brpicture/"+UUID.randomUUID().toString()+".png";
        writeImageLocal(path, mergeImage(a,b,true));
        return path;
    }


    public static void mergeRemotePicture(String path,String ... urls){

    }


    public static BufferedImage createImg(boolean isUrl, boolean resize,String ... urls) {
        List<BufferedImage> list = new ArrayList<>(urls.length);
        for (String url : urls){
            if (isUrl){
                list.add(getBufferedImageDestUrl(url));
            }else {
                list.add(loadImageLocal(url));
            }

        }
        int w = list.get(0).getWidth(),h = 0;
        for (BufferedImage bufferedImage : list){
            w = w + bufferedImage.getWidth();
/*            if (resize && bufferedImage.getWidth() > w){
                w = bufferedImage.getWidth();
            }else if (!resize && w > bufferedImage.getWidth()){
                w = bufferedImage.getWidth();
            }*/
        }
        w = w / list.size();

        List<Integer> highList = new ArrayList<>(urls.length);
        for (BufferedImage bufferedImage : list){
            int l = (int) (w * 1.0f * bufferedImage.getHeight() / bufferedImage.getWidth());
            highList.add(l);
            h = h + l;
        }



        BufferedImage bf = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED);
        log.info("占用内存：{} M",w*h/1024/1024);
        Graphics2D g2d = bf.createGraphics();
        g2d.setComposite(AlphaComposite.Src);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int tempHigh = 0;
        int i=0;
        for (BufferedImage b : list){
            g2d.drawImage(b,0,tempHigh, w, highList.get(i), null);
            tempHigh = tempHigh + highList.get(i);
            i++;
        }
        g2d.dispose();
        return bf;
    }


    public   static boolean write(BufferedImage image, float quality,HttpServletResponse httpServletResponse) {
        // 如果图片空，返回空
        if (image == null) {
            return false;
        }
        Long endTime;
        // 得到指定Format图片的writer
        Long startTime=System.currentTimeMillis();
        Iterator<ImageWriter> iter = ImageIO
                .getImageWritersByFormatName("jpg");// 得到迭代器
        ImageWriter writer =iter.next(); // 得到writer
        // 得到指定writer的输出参数设置(ImageWriteParam )
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // 设置可否压缩
        iwp.setCompressionQuality(quality); // 设置压缩质量参数
        iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

        ColorModel colorModel = ColorModel.getRGBdefault();
        // 指定压缩时使用的色彩模式
        iwp.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,
                colorModel.createCompatibleSampleModel(16, 16)));
        endTime=System.currentTimeMillis();
        log.info("[压缩图片] 配置耗时: {}",(endTime-startTime));
        IIOImage iIamge = new IIOImage(image, null, null);
        try {
            // 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput
            // 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput
            startTime=System.currentTimeMillis();
            writer.setOutput(ImageIO
                    .createImageOutputStream(httpServletResponse.getOutputStream()));
            writer.write(null, iIamge, iwp);
            endTime=System.currentTimeMillis();
            log.info("[压缩图片] write写入输出到httpServletResponse文件流 耗时: {} 毫秒",(endTime-startTime));
        } catch (IOException e) {
            log.warn("[压缩图片] 异常: {}"+e.getMessage());
            log.error(e.getMessage(),e);
            return false;
        }
        return true;
    }



    public static void main(String[] args) {
        PictureUtil tt = new PictureUtil();
        BufferedImage d = tt.loadImageLocal("C:\\Users\\lixianchun\\Desktop\\李显春健康打卡.jpg");
        BufferedImage b = tt.loadImageLocal("C:\\Users\\lixianchun\\Desktop\\李显春.jpg");
        tt.writeImageLocal("C:\\Users\\lixianchun\\Desktop\\33.jpg", tt.mergeImage(b, d,true));
        //将多张图片合在一起
        System.out.println("success");
    }

    @Test
    public void test(){
        BufferedImage bufferedImage = createImg(false,false,new String[]{
                "C:\\Users\\vpclub\\Desktop\\微信图片_20200427174530.jpg",
                "C:\\Users\\vpclub\\Desktop\\微信图片_20200427174534.jpg",
                "C:\\Users\\vpclub\\Desktop\\微信图片_20200427174537.jpg",
                "C:\\Users\\vpclub\\Desktop\\企业微信截图_20200427175429.png",
                "C:\\Users\\vpclub\\Desktop\\企业微信截图_20200427175429.png"
        });
        writeImageLocal("./3.png",bufferedImage);
    }
}
