package com.lxc.learn.junit.util;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author lixianchun
 * @Description
 * @date 2019/6/20 9:38
 */
public class PdfUtil {


    public static void main(String[] args) throws Exception {
        String path = "";

        File file = new File("./tt33.pdf");
        //file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        String[] urls = new String[2];
        urls[0] = "file:///C:/Users/Dell/Desktop/umall/getContract.pdf";
        urls[1] = "file:///C:/Users/Dell/Desktop/umall/getContract.pdf";
        mergePdf(urls,"./tt33.pdf");


        fos.flush();
        fos.close();
    }

    public static void append(String uri,FileOutputStream fos) {
        InputStream is = null;
        try{
            URL url = new URL(uri);
            is = url.openConnection().getInputStream();
            byte[] bytes = new byte[1024];
            int n;
            while ((n=is.read(bytes)) != -1){
                fos.write(bytes,0,n);
            }
            //fos.write("\n\r".getBytes());
        }catch (Exception e){

        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {

                }
            }
        }

    }


    /**
     * 合成pdf,单独追加写流无效
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

}
