package com.lxc.learn.arithmetic.massdataprocessing;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/27 9:57
 */
@Slf4j
public class BigFile {

    private static long FILE_SIZE = 10 * 1024;//10KB

    /**
     * 将文件hash取模之后放到不同的小文件中
     * @param targetFile 要去重的文件路径
     * @param splitSize 将目标文件切割成多少份hash取模的小文件个数
     * @return
     */
    public static File[] splitFile(String targetFile,int splitSize){
        File file = new File(targetFile);
        BufferedReader reader = null;
        PrintWriter[] pws = new PrintWriter[splitSize];
        File[] littleFiles = new File[splitSize];
        String parentPath = file.getParent();
        File tempFolder = new File(parentPath + File.separator + "zero");
        if(!tempFolder.exists()){
            tempFolder.mkdir();
        }
        for(int i=0;i<=splitSize;i++){
            littleFiles[i] = new File(tempFolder.getAbsolutePath() + File.separator + i + ".txt");
            if(littleFiles[i].exists()){
                littleFiles[i].delete();
            }
            try {
                pws[i] = new PrintWriter(littleFiles[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                tempString = tempString.trim();
                if(tempString != ""){
                    //关键是将每行数据hash取模之后放到对应取模值的文件中，确保hash值相同的字符串都在同一个文件里面
                    int index = Math.abs(tempString.hashCode() % splitSize);
                    pws[index].println(tempString);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            for(int i=0;i<=splitSize;i++){
                if(pws[i] != null){
                    pws[i].close();
                }
            }
        }
        return littleFiles;
    }

    /**
     * 对小文件进行去重合并
     * @param littleFiles 切割之后的小文件数组
     * @param path 去重之后的文件路径
     * @param splitSize 小文件大小
     */
    public static void distinct(File[] littleFiles,String path,int splitSize){
        String distinctFilePath =  new File(path).getParentFile() +  File.separator + "distinct.txt";

        File distinctedFile = new File(distinctFilePath);
        FileReader[] frs = new FileReader[splitSize];
        BufferedReader[] brs = new BufferedReader[splitSize];
        PrintWriter pw = null;
        try {
            if(distinctedFile.exists()){
                distinctedFile.delete();
            }
            distinctedFile.createNewFile();
            pw = new PrintWriter(distinctedFile);
            Set<String> unicSet = new HashSet<String>();
            for(int i=0;i<=splitSize;i++){
                if(littleFiles[i].exists()){
                    System.out.println("开始对小文件：" + littleFiles[i].getName() + "去重");
                    frs[i] = new FileReader(littleFiles[i]);
                    brs[i] = new BufferedReader(frs[i]);
                    String line = null;
                    while((line = brs[i].readLine())!=null){
                        if(line != ""){
                            unicSet.add(line);
                        }
                    }
                    for(String s:unicSet){
                        pw.println(s);
                    }
                    unicSet.clear();
                    System.gc();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1){
            e1.printStackTrace();
        } finally {
            for(int i=0;i<=splitSize;i++){
                try {
                    if(null != brs[i]){
                        brs[i].close();
                    }
                    if(null != frs[i]){
                        frs[i].close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //合并完成之后删除临时小文件
                if(littleFiles[i].exists()){
                    littleFiles[i].delete();
                }
            }
            if(null != pw){
                pw.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String path = "E:\\lcode\\learn\\data.txt";
        File file = new File(path);
        log.info("{}", (file.length())/1024/1024);
        long splitSize = file.length() / FILE_SIZE + ((file.length() % FILE_SIZE) > 0 ? 1 : 0);
        File[] files = splitFile(path,(int) splitSize);
        distinct(files,path,(int) splitSize);
    }

    @Test
    public void createTestTxt() throws Exception{
        Long start = System.currentTimeMillis();
        String path = "E:\\lcode\\learn\\data.txt";
        long count = 1 * 1024 * 1024 * 1024 / 36;//字节
        PrintWriter pw = new PrintWriter(new FileOutputStream(new File(path)));
        int i =0;
        while (i < (int)count){
            pw.write(UUID.randomUUID().toString());
            pw.write("\n");
            ++i;
        }
        log.info("耗时：{}，times:{}", System.currentTimeMillis() - start, count);
    }

    @Test
    public void te(){
        int a = 1;
        switch (a){
            case 1:{
                System.out.println("a");
            }
            case 2:{
                System.out.println("b");
            }
        }
    }

}
