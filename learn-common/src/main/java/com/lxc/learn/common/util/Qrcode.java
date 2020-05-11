package com.lxc.learn.common.util;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/11
 */
@Slf4j
public class Qrcode {

    public static void main(String[] args) {
        try {
            createQrCodeFile();
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }

    public static void createQrCodeFile() throws IOException {
        BufferedImage image = createImage("122", 300, 300);
        //通过流将图片输出到目标路径
        StringBuilder picPath = new StringBuilder();
        picPath.append(System.getProperty("java.io.tmpdir"));
        picPath.append("/UMall/tmpFile/");
        picPath.append("JPG");

        if (!new File(picPath.toString()).exists()){
            new File(picPath.toString()).mkdirs();
        }

        picPath.append(File.separator);
        picPath.append(UUID.randomUUID().toString().replace("-",""));
        picPath.append(".jpg");
        FileOutputStream fileOutputStream = null;
        File pictureFile = new File(picPath.toString());
        fileOutputStream = new FileOutputStream(pictureFile);
        ImageIO.write(image, "JPG", fileOutputStream);
    }


    /**
     *
     * @param content 二维码内容
     * @param qrcodeWidth 二维码宽度
     * @param qrcodeHeight 二维码高度
     * @return
     */
    public static BufferedImage createImage(String content, int qrcodeWidth, int qrcodeHeight) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(com.google.zxing.EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, qrcodeWidth, qrcodeHeight, hints);
        }catch (WriterException e){
            log.error(e.getMessage(),e);
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        return image;
    }

}
