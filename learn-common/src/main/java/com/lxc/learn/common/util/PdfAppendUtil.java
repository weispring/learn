package com.lxc.learn.common.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/1
 */
@Slf4j
public class PdfAppendUtil {

    public static boolean addImgToPdf(String pdfPath, List<String> pngs, String newPath, boolean isLocal) {
        boolean result = false;
        PdfReader reader = null;
        PdfStamper stamper = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(pdfPath));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(),e);
        }
        try {
            reader = new PdfReader(fis);
            Field field = null;
            try {
                field = PdfReader.class.getDeclaredField("encrypted");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            UnsafeUtils.getUnsafe().putBoolean(reader, UnsafeUtils.getUnsafe().objectFieldOffset(field),false);

            stamper = new PdfStamper(reader, new FileOutputStream(newPath));
            append(pngs, isLocal, reader, stamper);
            result = true;
            stamper.flush();
            stamper.close();
            reader.close();
        } catch (DocumentException | IOException e) {
            log.error("向pdf追加图片失败:{}",e.getMessage(),e);
            return result;
        } finally {
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                reader.close();
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

        for (String png : pngs){
            PdfContentByte under = stamper.getOverContent(reader.getNumberOfPages());//捕获新增的空白页
            Image image = null;
            if (!isLocal){
                image = Image.getInstance(HttpClientUtil.invokeGet(png,"utf-8",6000));
            }else {
                image = Image.getInstance(png);
            }
            if (image.getHeight() > currentPageLeave){
                stamper.insertPage(currentPageIndex+1, reader.getPageSizeWithRotation(1));//新增空白页
                under = stamper.getOverContent(reader.getNumberOfPages());//捕获新增的空白页
                currentPageIndex = currentPageIndex+1;
                currentPageLeave = reader.getPageSizeWithRotation(currentPageIndex).getHeight();
            }
            /** 已左下角为坐标原点 */
            /** 已左下角为坐标原点 */
            float x = 0f;
            float absoluteX = image.getWidth();
            float absoluteY = image.getHeight();
            float a = image.getAbsoluteX();
            float b = image.getAbsoluteY();
            if (reader.getPageSizeWithRotation(currentPageIndex).getWidth()-image.getWidth() < 0){
                absoluteX = reader.getPageSizeWithRotation(currentPageIndex).getWidth();
            }else {
                x = (reader.getPageSizeWithRotation(currentPageIndex).getWidth()-image.getWidth())/2;
            }
            if (absoluteY > reader.getPageSizeWithRotation(currentPageIndex).getHeight()){
                absoluteY = reader.getPageSizeWithRotation(currentPageIndex).getHeight();
            }

            image.scaleAbsolute(absoluteX,absoluteY);

            image.setAbsolutePosition(x,
                    (currentPageLeave - image.getHeight()) < 0 ? 0 : (currentPageLeave - image.getHeight()));
            currentPageLeave = currentPageLeave - image.getHeight();
            under.addImage(image);
        }
    }

    public static void main(String[] args) throws Exception {
/*        addImgToPdf("C:\\Users\\vpclub\\Desktop\\Corp SA_Summarized Agreement_edit version_v2 2.pdf", Arrays.asList("http://devcloud.vpclub.cn/group1/M00/0A/8A/rBAFJF57Lg2AW90SAABajbpnggo387.png",
                "http://devcloud.vpclub.cn/group1/M00/0A/82/rBAFJF5y5liANCHZAAZHwOAgT1w650.jpg",
                "http://devcloud.vpclub.cn/group1/M00/0A/8A/rBAFJF57Lg2AW90SAABajbpnggo387.png"),
                "C:\\Users\\vpclub\\Desktop\\Corp SA_Summarized Agreement_edit version_000.pdf",false);*/


        File picture = new File("C:\\Users\\vpclub\\Desktop\\微信图片_20200601190126.png");
        BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
        ColorModel color = sourceImg.getColorModel();
        log.info("位深度:" + color.getPixelSize()); //该函数能取到位深度

    }

}
