package com.lxc.learn.common.util.pdf;

import com.itextpdf.text.Rectangle;
import com.lxc.learn.common.util.PdfFontInit;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/15
 */
@Slf4j
public class PdfAddWater {


    public static void waterMark(String inputFile,String outputFile, String waterMarkName) {
        try {
            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
                    outputFile));
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",   BaseFont.EMBEDDED);
            java.awt.Rectangle pageRect = null;
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.3f);
            gs.setStrokeOpacity(0.4f);
            int total = reader.getNumberOfPages() + 1;

            JLabel label = new JLabel();
            FontMetrics metrics;
            int textH = 0;
            int textW = 0;
            label.setText(waterMarkName);
            metrics = label.getFontMetrics(label.getFont());
            textH = metrics.getHeight();
            textW = metrics.stringWidth(label.getText());

            PdfContentByte under;
            for (int i = 1; i < total; i++) {
                under = stamper.getOverContent(i);// 在内容上方加水印
                //content = stamper.getUnderContent(i);//在内容下方加水印
                gs.setFillOpacity(0.2f);
                // content.setGState(gs);
                under.beginText();

                under.setFontAndSize(base, 50);
                under.setTextMatrix(70, 200);
                under.showTextAligned(Element.ALIGN_CENTER, "测试水印！", 300,350, 55);

                under.endText();
            }
            //一定不要忘记关闭流
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String source = "C:\\Users\\vpclub\\Desktop\\java-李显春 - 副本.pdf";
        String target = "C:\\Users\\vpclub\\Desktop\\java-李显春 - 副本1.pdf";
        waterMark(source, target, "添加水印");
    }
}
