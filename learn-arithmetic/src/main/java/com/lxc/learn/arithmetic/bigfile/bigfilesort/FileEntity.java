package com.lxc.learn.arithmetic.bigfile.bigfilesort;

import java.io.BufferedReader;
import java.io.IOException;


/**
 *
 * @Description: File包装类，用于按行读取
 *
 */
public class FileEntity {


    private String line = "";
    private Integer lineNum = null;
    private BufferedReader br;

    /**
     * 传入文件 BufferedReader，包装成FileEntity
     *
     * @param br
     * @throws IOException
     */
    public FileEntity(BufferedReader br) throws IOException {
        this.br = br;
        // 初始化读取第一行
        readNextLine();
    }

    /**
     * 使用来排序的数据
     *
     * @throws IOException
     */
    private void readNextLine() throws IOException {
        line = br.readLine();
        if (line != null) {
            try {
                lineNum = Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {

            }
        }else {
            lineNum = null;
        }
    }

    /**
     * 关闭流
     */
    public void close() {
        if (this.br != null) {
            try {
                this.br.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 读取下一行
     *
     * @return FileEntity
     */
    public Integer getNextLine(Integer lastMin) {
        try {
            if (lastMin == null){
                readNextLine();
            }else if (lineNum == null){
                return lineNum;
            }
            else {
                if (lastMin >= lineNum){
                    readNextLine();
                }
            }

        } catch (IOException e) {
        }
        return lineNum;
    }


    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }
}
