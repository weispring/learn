package com.lxc.learn.junit.util.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.lxc.learn.common.util.WebTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixianchun
 * @description
 * @date 2020/3/26
 */
@Slf4j
public class PdfMerge {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

    {
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        threadPoolTaskExecutor.setQueueCapacity(100);

    }

    //分辨率
    public static final float DEFAULT_DPI = 130;


    public static void main(String[] args) {
        //pdfToImageFile("C:\\Users\\vpclub\\Desktop\\f56191613_20200110130506.pdf");
        mergePdfFiles(Arrays.asList("C:\\Users\\vpclub\\Desktop\\李显春_java开发_3年 - 副本.pdf","C:\\Users\\vpclub\\Desktop\\李显春_java开发_3年.pdf"),"./l.pdf");
    }


    /**
     * 合并多个在线 pdf文件
     * @param urls
     * @return
     */
    public String mergePdfUrls(List<String> urls){
        if(CollectionUtils.isEmpty(urls)){
            return null;
        }
        Long startTime;
        Long endTime;
        Document  document = null;
        PdfReader reader = null;
        PdfCopy copy = null;
        String tempFilePath = "./mergePdf/" + UUID.randomUUID()+".pdf";
        try {
            document=new Document();
            copy=new PdfCopy(document,new FileOutputStream(tempFilePath));

            document.open();
            PdfImportedPage page;
            startTime=System.currentTimeMillis();
            ConcurrentHashMap<Integer,byte[]> pdfs=new ConcurrentHashMap<>();
            for (int i = 0; i < urls.size(); i++) {
                threadPoolTaskExecutor.execute(new DownloadPdf(pdfs,i,urls.get(i)));
            }
            while(pdfs.size()!=urls.size() && System.currentTimeMillis()-startTime<120000 )
            {
                Thread.sleep(100);
            }
            if(pdfs.size()!=urls.size())
            {
                throw new Exception("【加载PDF】 加载超时异常>>>>>>>>");
            }
            endTime=System.currentTimeMillis();
            log.info("【获取PDF资源】 完成合约下载>>>>>>>>>>>>>>>共耗时: {} 毫秒",(endTime-startTime));

            for (int i = 0; i < urls.size(); i++) {
                startTime=System.currentTimeMillis();
                reader = new PdfReader(pdfs.get(i));
                document.setPageSize(reader.getPageSize(1));
                int n = reader.getNumberOfPages();
                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
                endTime=System.currentTimeMillis();
                log.info("【拼接pdf】 url={} >>>>>>>>>>>>>>>耗时: {} 毫秒",urls.get(i),(endTime-startTime));
            }
            return tempFilePath;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(null != copy) {
                copy.close();
            }
            if(null != reader){
                reader.close();
            }
            if(null !=document){
                document.close();
            }
        }
    }

    @Slf4j
    public static class DownloadPdf  implements Runnable{

        private ConcurrentHashMap<Integer,byte[]> valueMap;
        private Integer key;
        private  String url;

        public DownloadPdf(ConcurrentHashMap<Integer,byte[]> valueMap,Integer key,String pUrl){
            this.valueMap=valueMap;
            this.key=key;
            this.url=pUrl;
        }
        @Override
        public void run() {
            long startTime=System.currentTimeMillis();
            byte[] bytes = getReturnByte(  this.url);
            if(bytes!=null && bytes.length>0){
                valueMap.put(key,bytes);
            }
            long endTime=System.currentTimeMillis();
            log.info("【获取PDF资源】 多线下载，不计入总耗时url={} >>>>>>>>>>>>>>>耗时: {} 毫秒",this.url,(endTime-startTime));
        }
    }

    public static byte[] getReturnByte(String url) {
        CloseableHttpClient httpClient = WebTools.getCloseableHttpClient();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                return EntityUtils.toByteArray(responseEntity);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
        return null;
    }


    /**
     * pdf 转图片
     * @param pdfPath
     * @return
     */
    public static String pdfToImageFile(String pdfPath) {
        String imageFile = "./"+UUID.randomUUID().toString()+".jpg";
        Long startTime = System.currentTimeMillis();
        Long endTime;
        try {
            // 图像合并使用参数
            // 总宽度
            int width = 0;
            //总长度
            int hight=0;
            // 保存一张图片中的RGB数据
            int[] singleImgRGB;
            int shiftHeight = 0;
            // 保存每张图片的像素值
            BufferedImage imageResult = null;
            // 利用PdfBox生成图像
            PDDocument pdDocument = PDDocument.load(new File(pdfPath));
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            int len = pdDocument.getNumberOfPages();
            float pageWidth;
            float pageHight;
            PDPage page;
            PDRectangle cropbBox;
            for(int num=0;num<len;num++){
                page=pdDocument.getPage(num);
                cropbBox = page.getCropBox();
                //TODO
                pageWidth=Math.round(cropbBox.getWidth() * (DEFAULT_DPI/72f));
                pageHight=Math.round(cropbBox.getHeight()* (DEFAULT_DPI/72f));
                if(pageWidth>width){
                    width= (int) pageWidth;
                }
                hight+=(pageHight);
            }
            int imageHeight;
            int imageWidth;
            BufferedImage image;
            // 循环每个页码
            for (int i = 0; i <len; i++) {
                image = renderer.renderImageWithDPI(i, DEFAULT_DPI, ImageType.GRAY);
                imageHeight = image.getHeight();
                imageWidth = image.getWidth();
                // 计算高度和偏移量
                if (i == 0) {
                    // 保存每页图片的像素值
                    // 第三位表示 位像素
                    imageResult = new BufferedImage(width, hight, BufferedImage.TYPE_BYTE_BINARY);
                    Graphics2D g=imageResult.createGraphics();
                    g.setBackground(Color.WHITE);
                    g.clearRect(0,0,width,hight);
                } else {
                    // 计算偏移高度
                    shiftHeight += imageHeight;
                }
                singleImgRGB = image.getRGB(0, 0, imageWidth, imageHeight, null, 0, width);
                // 写入流中
                imageResult.setRGB(0, shiftHeight, width, imageHeight, singleImgRGB, 0, width);
            }
            pdDocument.close();
            // 写图片
            write(imageResult,0.8f, new FileOutputStream(new File(imageFile)));
        } catch (Exception e) {
            log.error("PDF转图片失败:{}",e.getMessage(),e);
        }finally {
          /*  File file = new File(pdfPath);
            if (file.exists()){
                file.delete();
            }*/
        }
        log.info("pdf -> image :{}",System.currentTimeMillis() - startTime);
        return imageFile;
    }

    private  static boolean write(BufferedImage image, float quality, OutputStream outputStream) {
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
        IIOImage iIamge = new IIOImage(image, null, null);
        try {
            // 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput
            // 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput
            writer.setOutput(ImageIO
                    .createImageOutputStream(outputStream));
            writer.write(null, iIamge, iwp);
            endTime=System.currentTimeMillis();
            log.info("[压缩图片] write写入输出到httpServletResponse文件流 耗时: {} 毫秒",(endTime-startTime));
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return false;
        }finally {

        }
        return true;
    }


    /**
     * 本地pdf 文件合并
     * @param files
     * @param newfile
     * @return
     */
    public static boolean mergePdfFiles(List<String> files, String newfile) {
        if(CollectionUtils.isEmpty(files)){
            log.info("mergePdfFiles faild. files is null.");
            return false;
        }
        boolean retValue = false;
        com.lowagie.text.Document document = null;
        try {
            String tempPath = files.get(0);
            document = new com.lowagie.text.Document(new com.lowagie.text.pdf.PdfReader(tempPath).getPageSize(1));
            com.lowagie.text.pdf.PdfCopy copy = new com.lowagie.text.pdf.PdfCopy(document, new FileOutputStream(newfile));
            document.open();
            String path;
            for (int i = 0; i < files.size(); i++) {
                path = files.get(i);
                com.lowagie.text.pdf.PdfReader reader = new com.lowagie.text.pdf.PdfReader(files.get(i));

                int n = reader.getNumberOfPages();
                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    com.lowagie.text.pdf.PdfImportedPage page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
            }
            retValue = true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            document.close();
        }
        return retValue;
    }


    /**
     * image to pdfFile
     * @param fileUrl
     * @param pdfFilePath
     * @return
     */
    public static boolean imgUrlToPdf(String fileUrl, String pdfFilePath)  {
        FileOutputStream fos = null;
        Document document=new Document();
        try {
            fos = new FileOutputStream(pdfFilePath);
            PdfWriter.getInstance(document, fos);
            // 读取一个图片
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(fileUrl);
            float imageHeight = image.getScaledHeight();
            float imageWidth = image.getScaledWidth();
            if(imageHeight<842){
                document.setPageSize(PageSize.A4);
            }else{
                // 设置文档的大小
                document.setPageSize(new com.itextpdf.text.Rectangle(595,imageHeight*0.20f));
            }
            // 打开文档
            document.open();
            int i = 0;
            while (imageHeight > 500 || imageWidth > 500) {
                image.scalePercent(100 - i);
                i++;
                imageHeight = image.getScaledHeight();
                imageWidth = image.getScaledWidth();
            }
            image.setAlignment(Image.ALIGN_CENTER);
            image.scalePercent(17);
            // 插入一个图片
            document.add(image);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if(null != document) document.close();
            if(null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
        return true;
    }
}
