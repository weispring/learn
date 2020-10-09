package com.lxc.learn.arithmetic.practice.bigfile.bigfilesort;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.*;


/**
 * 题目：超大文件去重并排序
 *
 * 思路：因为内存限制为32MB，将大文件分割成可以进行内部排序的的小文件，然后利用多路归并排序进行最终外部排序。
 * 步骤：
 * 1，产生随机数，生成10个测试文件，10个线程同时进行。
 * 2，将大文件分割1BM的小文件，每个线程对分割而成的内容进行内部排序后，写入文件。
 * 3，将所有小文件排序后，利用多路排序算法将小文件写入最终文件。

 *
 */
public class LargeFileSort {

    // 存放测试和结果文件路径，改变环境修改此地址
    private final static String ROOT_FILE_PATH = "F:\\own-test";

    // 切分文件大小 5M /
    private final static int BYTE_SIZE = 5 * 1024 * 1024;

    private static String LINE_SEPARATOR = "\n";

    private static String RESULT_FILE = "result.txt";

    /**
     * 入口函数
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Long root = System.currentTimeMillis();
        // 生成测试数据
        String testFilePath = ROOT_FILE_PATH + File.separator + "test.txt";
        GenerateFileTask.generateTestFile(testFilePath);
        Long gen = System.currentTimeMillis();
        System.out.println(String.format("初始化数据完成:%s s。", (gen - root) / 1000));

        Long divStart = System.currentTimeMillis();
        // 切分大文件成排序好的小文件，参数为同时执行线程数量
        List<File> tempFileList = splitBigFile(testFilePath);
        Long divEnd = System.currentTimeMillis();
        System.out.println(String.format("切分数据完成:%s s。", (divEnd - divStart) / 1000));

        Long mergeStart = System.currentTimeMillis();
        // 最终合并所有小文件
        merge(tempFileList);

        Long mergeEnd = System.currentTimeMillis();
        System.out.println(String.format("合并完成:%s 秒。", (mergeEnd - mergeStart) / 1000));

        // 验证
        Long checkStart = System.currentTimeMillis();
        validation();
        System.out.println(String.format("check 完成:%s 秒。", (System.currentTimeMillis() - checkStart) / 1000));
        System.exit(0);
    }



    /**
     * 合并分为两步，第一步将所有小文件合并和成较大的文件 , 第二部将所有文件合并最终结果。
     *
     * @throws IOException
     */
    private static void merge(List<File> tempFileList) {
        List<FileEntity> list = new ArrayList<>();

        Integer lastMinimum = null;
        try (FileWriter writer = new FileWriter(ROOT_FILE_PATH + File.separator + "result.txt")) {
            for (File file : tempFileList) {
                BufferedReader tmpReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                FileEntity fileEntity = new FileEntity(tmpReader);
                list.add(fileEntity);
            }
            while (true) {
                boolean canRead = false;
                FileEntity minEntry = null;
                Iterator<FileEntity> iterator = list.iterator();
                while (iterator.hasNext()){
                    FileEntity fileEntity = iterator.next();
                    Integer value = fileEntity.getNextLine(lastMinimum);
                    if (value == null) {
                        iterator.remove();
                        continue;
                    }
                    // 获取当前 reader 内容最小 entry
                    Integer temp;
                    if ((minEntry == null) ||
                            ((temp = fileEntity.getNextLine(lastMinimum)) != null
                            && Integer.valueOf(value) < minEntry.getLineNum())) {
                        minEntry = fileEntity;
                    }
                    canRead = true;
                }
                // 当且仅当所有 reader 内容为空时，跳出循环
                if (!canRead) {
                    break;
                }
                writer.write(minEntry.getLineNum() + LINE_SEPARATOR);
                lastMinimum = minEntry.getLineNum();
                //读取下一个
                minEntry.getNextLine(null);
                if (minEntry.getLineNum() == null){
                    minEntry.setLineNum(lastMinimum + 1);
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 注意关闭分片文件输入流
            for (FileEntity reader : list) {
                reader.close();
            }
        }
    }

    /**
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<File> splitBigFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            throw new Error("文件不存在");
        // 得到文件大小k
        int mbsize = (int) Math.ceil(file.length() / 1024);
        // 计算得到切分的文件数
        int fileNum = (int) Math.ceil(mbsize / (BYTE_SIZE/1024));
        // 创建零时文件
        List<File> tempFileList = createTempFileList(file, fileNum);
        // 切分文件
        divAndSort(file, tempFileList);
        return tempFileList;

    }


    /**
     * 切分文件并做第一次排序
     *
     * @param file
     * @param tempFileList
     */
    private static void divAndSort(File file, List<File> tempFileList) {
        BufferedReader bRead = null;
        try {
            // 读取大文件
            bRead = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            // 行数据保存对象
            String line = null;
            // 临时文件索引
            int index = tempFileList.size() - 1;
            // 保存数据
            List<String> lineList = new ArrayList<>();
            // 统计文件大小
            int byteSum = 0;
            // 循环临时文件并循环大文件
            while ((line = bRead.readLine()) != null) {
                line += "\n";
                byteSum += line.getBytes().length;
                // 如果长度达到每个文件大小则重新计算
                if (byteSum >= BYTE_SIZE) {
                    Long time0 = System.currentTimeMillis();
                    // 写入到文件
                    putLineListToFile(tempFileList.get(index), lineList);
                    Long time1 = System.currentTimeMillis();
                    System.out.println(String.format("写入文件%s：%s ms", index, time1 - time0));
                    index--;
                    byteSum = line.getBytes().length;
                    lineList.clear();
                }
                lineList.add(line);
            }
            if (lineList.size() > 0) {
                // 写入到文件
                putLineListToFile(tempFileList.get(0), lineList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bRead != null) {
                    bRead.close();
                }
            } catch (IOException e) {
                // no-op
            }
        }
    }

    /**
     * 把数据写到临时文件
     *
     * @param lineList
     */
    private static void putLineListToFile(File file, List<String> lineList) throws IOException {
        FileOutputStream tempFileFos = null;
        try {
            // 第一次写入文件时，调用Collection.sort进行内部排序
            lineList.sort(new LineComparator());
            tempFileFos = new FileOutputStream(file);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < lineList.size(); i++) {
                sb.append(lineList.get(i));
                if ((i + 1) % 1000 == 0) {
                    tempFileFos.write(sb.toString().getBytes());
                    sb.setLength(0);
                }
            }
            if (sb.length() > 0) {
                tempFileFos.write(sb.toString().getBytes());
            }
        } finally {
            if (tempFileFos != null) {
                tempFileFos.close();
            }
        }
    }

    /**
     * AIO异步写文件
     *
     * @param file
     * @param lineList
     * @throws IOException
     */
    private static void putLineListToFileNIO(File file, List<String> lineList) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 512);
        Future<Integer> operation = null;
        try {
            // 第一次写入文件时，调用Collection.sort进行内部排序
            lineList.sort(new LineComparator());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < lineList.size(); i++) {
                sb.append(lineList.get(i));
                if ((i + 1) % 1000 == 0) {
                    if (operation != null) {
                        while (!operation.isDone())
                            ;
                    }
                    buffer.put(sb.toString().getBytes());
                    buffer.flip();
                    operation = fileChannel.write(buffer, 0);
                    buffer.clear();
                    sb.setLength(0);
                }
            }
            if (sb.length() > 0) {
                buffer.put(sb.toString().getBytes());
                buffer.flip();
                operation = fileChannel.write(buffer, 0);
            }
        } finally {
            if (operation != null) {
                while (!operation.isDone())
                    ;
            }
            fileChannel.force(true);
            if (fileChannel.isOpen()) {
                fileChannel.close();
            }
        }
    }

    /**
     * 生成零时文件
     *
     * @param file
     * @param fileNum
     * @return List<File>
     */
    private static List<File> createTempFileList(File file, int fileNum) {
        List<File> tempFileList = new ArrayList<File>();
        String fileFolder = file.getParent();
        String name = file.getName();
        for (int i = 0; i < fileNum; i++) {
            // 创建临时文件
            File tempFile = new File(fileFolder + "/" + name + "_temp_" + i + ".txt");
            // 如果存在就删除
            if (tempFile.exists()) {
                tempFile.delete();
            }
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            tempFileList.add(tempFile);
        }
        return tempFileList;
    }

    /**
     * @param o1
     * @param o2
     * @return
     */
    public static int compare(String o1, String o2) {
        o1 = o1.trim();
        o2 = o2.trim();
        // 从小到大
        return Integer.parseInt(o1) - Integer.parseInt(o2);
        // 从大到小
        // return Integer.parseInt(o2) - Integer.parseInt(o1);
    }

    /**
     * 验证
     *
     * @throws IOException
     */
    private static void validation() throws IOException {
        System.out.println("执行验证");
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(ROOT_FILE_PATH + File.separator + RESULT_FILE)));
        String line = "";
        String pre = br.readLine();
        while ((line = br.readLine()) != null) {
            if (Integer.parseInt(line.trim()) < Integer.parseInt(pre.trim())) {
                System.out.println("验证不通过");
                System.exit(0);
            }
        }
        System.out.println("验证通过");
    }


    /**
     * 排序
     */
    static class LineComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return LargeFileSort.compare(o1, o2);
        }
    }

}