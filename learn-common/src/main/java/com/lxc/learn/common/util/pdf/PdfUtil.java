package com.lxc.learn.common.util.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfStamper;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lxc.learn.common.constant.SystemConstant;
import com.lxc.learn.common.util.HttpClientUtil;
import com.lxc.learn.common.util.PdfFontInit;
import com.lxc.learn.common.util.reflect.UnsafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.misc.Unsafe;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author lixianchun
 * @Description
 * @date 2019/6/20 9:38
 */
@Slf4j
public class PdfUtil {


    public static void main(String[] args) throws Exception {
       /* String path = "";

        File file = new File("./tt33.pdf");
        //file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        String[] urls = new String[2];
        urls[0] = "file:///C:/Users/Dell/Desktop/umall/getContract.pdf";
        urls[1] = "file:///C:/Users/Dell/Desktop/umall/getContract.pdf";
        mergePdf(urls, "./tt33.pdf");


        fos.flush();
        fos.close();*/
        //testAddImgToPdf();

        byte[] bytes = new byte[]{-84,-71,31,120,-81,13,-19,-114,-44,-111,18,80,-104,21,91,-87,-126,-87,-4,-60,94,5,88,-53,
                                    52,-12,69,-84,123,-93,-89,80};
        //ISO-8859-1
        System.out.println(new String(bytes, Charset.forName("UTF-8")));

/*        PdfReader reader = new PdfReader("C:\\Users\\vpclub\\Desktop\\rBAFJF80usSAMp9fAAHyMxjh49o261.pdf","11".getBytes());
        int n = reader.getNumberOfPages();

        String a = new String("".getBytes());*/
    }

    public static void append(String uri, FileOutputStream fos) {
        InputStream is = null;
        try {
            URL url = new URL(uri);
            is = url.openConnection().getInputStream();
            byte[] bytes = new byte[1024];
            int n;
            while ((n = is.read(bytes)) != -1) {
                fos.write(bytes, 0, n);
            }
            //fos.write("\n\r".getBytes());
        } catch (Exception e) {

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {

                }
            }
        }

    }


    /**
     * 合成pdf,单独追加写流无效
     *
     * @param urls
     * @param newfile
     * @return
     */
    public static boolean mergePdf(String[] urls, String newfile) {
        boolean retValue = false;
        Document document = null;
        try {
            document = new Document(new PdfReader(new URL(urls[0])).getPageSize(1));
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));
            document.open();
            for (int i = 0; i < urls.length; i++) {
                PdfReader reader = new PdfReader(urls[i]);
                int n = reader.getNumberOfPages();
                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
            }
            retValue = true;
        } catch (Exception e) {

        } finally {
            document.close();
        }
        return retValue;
    }


    public static void encryption(String read, String write, String path, String newPath) {
        try{
            PdfReader reader = new PdfReader(path);
            com.lowagie.text.pdf.PdfStamper stamper = new com.lowagie.text.pdf.PdfStamper(reader, new FileOutputStream(newPath));
            // 设置密码
            stamper.setEncryption(read.getBytes(), write.getBytes(), 16, false);
            stamper.close();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    public static void decryption(String read, String write, String path, String newPath) {
        try{
            PdfReader reader = new PdfReader(path,read.getBytes());
            com.lowagie.text.pdf.PdfStamper stamper = new com.lowagie.text.pdf.PdfStamper(reader, new FileOutputStream(newPath));
            // 设置密码
            stamper.setEncryption("".getBytes(), "".getBytes(), 16, false);
            stamper.close();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }


    @Test
    public void test(){
        decryption("52628752","52628752","C:\\Users\\vpclub\\Desktop\\11.pdf","C:\\Users\\vpclub\\Desktop\\CorpDiscount_006.pdf");
    }



    public static void testAddImgToPdf(){
        addImgToPdf("C:\\Users\\vpclub\\Desktop\\CorpDiscount_00611.pdf","C:\\Users\\vpclub\\Desktop\\rBAFJF7GpkqAV5tfAACTBUvbv0E84.jpeg","C:\\Users\\vpclub\\Desktop\\CorpDiscount_006633.pdf",true);
    }


    public static boolean addImgToPdf(String pdfPath,String pngPath, String newPath,boolean isLocal) {
        boolean result = false;
        com.itextpdf.text.pdf.PdfReader reader = null;
        PdfStamper stamper = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(pdfPath));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(),e);
        }
        try {
            reader = new com.itextpdf.text.pdf.PdfReader(fis);
            Field field = null;
            try {
                field = com.itextpdf.text.pdf.PdfReader.class.getDeclaredField("encrypted");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            UnsafeUtils.getUnsafe().putBoolean(reader, UnsafeUtils.getUnsafe().objectFieldOffset(field),false);

            stamper = new PdfStamper(reader, new FileOutputStream(newPath));
            stamper.insertPage(reader.getNumberOfPages() + 1, reader.getPageSizeWithRotation(1));//新增空白页
            PdfContentByte under = stamper.getOverContent(reader.getNumberOfPages());//捕获新增的空白页
            Image image = null;
            if (!isLocal){
                image = Image.getInstance(HttpClientUtil.invokeGet(pngPath, SystemConstant.UTF8,60*1000));
            }else {
                image = Image.getInstance(pngPath);
            }
            int high = 0;
            for (int i=1;i<reader.getNumberOfPages();i++){
                high += reader.getPageSizeWithRotation(i).getHeight();
                System.out.println(high);
            }
            System.out.println(high);

            /** 已左下角为坐标原点 */
            image.setAbsolutePosition((reader.getPageSizeWithRotation(1).getWidth()-image.getWidth())/2,
                    (reader.getPageSizeWithRotation(1).getHeight() - image.getHeight() ));

        /*  image.scaleAbsolute(250, 200);// 图片大小 宽，高
            image.setAbsolutePosition(25, 530);*/

            under.addImage(image);
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


    /**
     * 測試未通過 todo: error
     */
    @Test
    public void pdfAppendPng() {
        try{
        // 模板文件路径
        String templatePath = "C:\\Users\\vpclub\\Desktop\\李显春_java开发_3年.pdf";
        // 生成的文件路径
        String targetPath = "C:\\Users\\vpclub\\Desktop\\李显春_java开发_3年01.pdf";
        // 书签名
        String fieldName = "自我评价";
        // 图片路径
        String imagePath = "C:\\Users\\vpclub\\Desktop\\cellphone.png";

        // 读取模板文件
        InputStream input = new FileInputStream(new File(templatePath));
        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(targetPath));
        // 提取pdf中的表单
        AcroFields form = stamper.getAcroFields();
        Class.forName(PdfFontInit.class.getName());
/*        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        form.addSubstitutionFont(BaseFont.createFont("STSong-Light",
            "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));*/

        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();

        // 读图片
        Image image = Image.getInstance(imagePath);
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);
        stamper.close();
        reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
