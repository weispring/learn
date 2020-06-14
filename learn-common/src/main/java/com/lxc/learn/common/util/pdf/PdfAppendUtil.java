package com.lxc.learn.common.util.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.lxc.learn.common.util.web.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/1
 */
@Slf4j
public class PdfAppendUtil {

    public static boolean addImgToPdf(String pdfPath, List<String> pngs, String newPath, boolean isLocal,String password) {
        boolean result = false;
        PdfReader reader = null;
        PdfStamper stamper = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(pdfPath));
            if (password != null){
                reader = new PdfReader(fis,password.getBytes());
            }else {
                reader = new PdfReader(fis);
            }
/*            Field field = null;
            try {
                field = PdfReader.class.getDeclaredField("encrypted");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            UnsafeUtils.getUnsafe().putBoolean(reader, UnsafeUtils.getUnsafe().objectFieldOffset(field),false);*/

            stamper = new PdfStamper(reader, new FileOutputStream(newPath));
            append(pngs, isLocal, reader, stamper);
            result = true;
            stamper.flush();

        } catch (DocumentException | IOException e) {
            log.error("向pdf追加图片失败:{}",e.getMessage(),e);
            return result;
        } finally {
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                reader.close();
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static void append(List<String> pngs, boolean isLocal, PdfReader reader, PdfStamper stamper) throws IOException, DocumentException {
        if (CollectionUtils.isEmpty(pngs)){
            return;
        }
        int currentPageIndex = reader.getNumberOfPages() + 1;
        stamper.insertPage(currentPageIndex, reader.getPageSizeWithRotation(1));//新增空白页
        float currentPageLeave = reader.getPageSizeWithRotation(currentPageIndex).getHeight();

        boolean first = true;
        for (String png : pngs){
            PdfContentByte under = stamper.getOverContent(reader.getNumberOfPages());//捕获新增的空白页
            BufferedImage bufferedImage = null;
            if (!isLocal){
                bufferedImage = ImageIO.read(new URL(png));
            }else {
                bufferedImage = ImageIO.read(new File(png));
            }
            if (!first  && (bufferedImage.getHeight() > currentPageLeave)){
                stamper.insertPage(currentPageIndex+1, reader.getPageSizeWithRotation(1));//新增空白页
                under = stamper.getOverContent(reader.getNumberOfPages());//捕获新增的空白页
                currentPageIndex = currentPageIndex+1;
                currentPageLeave = reader.getPageSizeWithRotation(currentPageIndex).getHeight();
            }
            first = false;
            /** 已左下角为坐标原点 */
            /** 已左下角为坐标原点 */
            float absoluteX = bufferedImage.getWidth();
            float absoluteY = bufferedImage.getHeight();

            if (reader.getPageSizeWithRotation(currentPageIndex).getWidth()-bufferedImage.getWidth() < 0){
                absoluteX = reader.getPageSizeWithRotation(currentPageIndex).getWidth();
            }
            if (absoluteY > reader.getPageSizeWithRotation(currentPageIndex).getHeight()){
                absoluteY = reader.getPageSizeWithRotation(currentPageIndex).getHeight();
            }

            float percentX = absoluteX / bufferedImage.getWidth();
            float percentY = absoluteY / bufferedImage.getHeight();
            BufferedImage bd = null;
            if (percentX >= percentY){
                bd = zoomImage(bufferedImage,percentY);
            }else {
                bd = zoomImage(bufferedImage,percentX);
            }
            String formate = png.substring(png.lastIndexOf("."));
            if (StringUtil.isNotEmpty(formate)){
                formate = "png";
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,formate,byteArrayOutputStream);
            Image image = Image.getInstance(byteArrayOutputStream.toByteArray());

            image.scaleAbsolute(bd.getWidth(),bd.getHeight());

            float x = (reader.getPageSizeWithRotation(currentPageIndex).getWidth()- bd.getWidth())/2;

            image.setAbsolutePosition(x,
                    (currentPageLeave - bd.getHeight()) < 0 ? 0 : (currentPageLeave - bd.getHeight()));
            currentPageLeave = currentPageLeave - bd.getHeight();
            under.addImage(image);
        }
    }

    /*public static void main(String[] args) {
        addImgToPdf("C:\\Users\\vpclub\\Desktop\\Corp SA_Summarized Agreement_edit version_v2 2.pdf", Arrays.asList("http://devcloud.vpclub.cn/group1/M00/0A/8A/rBAFJF57Lg2AW90SAABajbpnggo387.png",
                "http://devcloud.vpclub.cn/group1/M00/0A/82/rBAFJF5y5liANCHZAAZHwOAgT1w650.jpg",
                "http://devcloud.vpclub.cn/group1/M00/0A/8A/rBAFJF57Lg2AW90SAABajbpnggo387.png"),
                "C:\\Users\\vpclub\\Desktop\\Corp SA_Summarized Agreement_edit version_000.pdf",false);
    }*/


    public static BufferedImage zoomImage(BufferedImage im, double resizeTimes) {
        BufferedImage result = null;
        try {
            /* 原始图像的宽度和高度 */
            int width = im.getWidth();
            int height = im.getHeight();

            /* 调整后的图片的宽度和高度 */
            int toWidth = (int) (width * resizeTimes);
            int toHeight = (int) (height * resizeTimes);
            /* 新生成结果图片 */
            result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
            result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0,
                    null);
        } catch (Exception e) {
            System.out.println("创建缩略图发生异常" + e.getMessage());
        }
        return result;
    }

    public static void main(String[] args) {
        addImgToPdf("C:\\Users\\vpclub\\Desktop\\18757510-366b-4913-9345-9b44a54329ba.pdf",Arrays.asList("http://devcloud.vpclub.cn/group1/M00/0B/68/rBAFJF7YvFKAGrJqAAIREydDpl874.jpeg","http://devcloud.vpclub.cn/group1/M00/0B/68/rBAFJF7YvF-AeXZrAAb2oAl7N8E39.jpeg"),
                "C:\\Users\\vpclub\\Desktop\\1212.pdf",false,null);
    }
}

