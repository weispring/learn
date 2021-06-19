package com.lxc.learn.jdk.wirelessnet;

/**
 * @Auther: lixianchun
 * @Date: 2020/2/22 11:24
 * @Description:
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lxc.learn.jdk.wirelessnet.Command.CMD_TARGET_DIR;
import static com.lxc.learn.jdk.wirelessnet.Command.PASSWORD_TEMP_PATH;

/**
 * 配置文件生成器
 */
public class ProfileGenerator {
    private String ssid = null;
    private ExecutorService threadPool = Executors.newFixedThreadPool(4);
    public ProfileGenerator(String ssid) {
        this.ssid = ssid;
    }
    /**
     * 生成配置文件
     */
    public void genProfile() {
        List<String> passwordList = null;
        int counter = 0;
        // TODO 待测试的密码集合
        passwordList = Arrays.asList("12345678","12345678yy","ics2u4ml");
        if (passwordList != null && passwordList.size() > 0) {
            // 生成配置文件
            for (String password : passwordList) {
                GenThread genThread = new GenThread(ssid, password);
                genThread.run();
                //threadPool.execute(genThread);
            }
        }
    }

    /**
     * 校验WLAN配置文件是否正确
     * <p>
     * 校验步骤为：
     * ---step1 添加配置文件
     * ---step3 连接wifi
     * ---step3 ping校验
     */
    public static synchronized boolean check(String ssid, String password) {
        System.out.println("check : " + password);
        try {
            String profileName = Command.PASSWORD_TEMP_PATH + "\\" + password + ".xml";
            if (addProfile(profileName)) {
                if (connect(ssid)) {
                    Thread.sleep(1000);
                    if (ping()) {
                        System.exit(0);
                        return true;
                    }else {
                        execute("netsh wlan delete profile name="+profileName,CMD_TARGET_DIR);
                    }
                }
            }else {
                execute("netsh wlan delete profile name="+profileName,CMD_TARGET_DIR);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 添加配置文件
     *
     * @param profileName 添加配置文件
     */
    private static boolean addProfile(String profileName) {
        String cmd = Command.ADD_PROFILE.replace("FILE_NAME", profileName);
        List<String> result = execute(cmd, CMD_TARGET_DIR);
        if (result != null && result.size() > 0) {
            if (result.get(0).contains("添加到接口")) {
                return true;
            }
        }
        return false;
    }
    /**
     * 连接wifi
     *
     * @param ssid 添加配置文件
     */
    private static boolean connect(String ssid) {
        boolean connected = false;
        String cmd = Command.CONNECT.replace("SSID_NAME", ssid);
        List<String> result = execute(cmd, null);
        if (result != null && result.size() > 0) {
            if (result.get(0).contains("已成功完成")) {
                connected = true;
            }
        }
        return connected;
    }
    /**
     * ping 校验
     */
    private static boolean ping() {
        boolean pinged = false;
        String cmd = "ping " + Command.PING_DOMAIN;
        List<String> result = execute(cmd, null);
        if (result != null && result.size() > 0) {
            for (String item : result) {
                if (item.contains("TTL")) {
                    System.out.println("success");
                    pinged = true;
                    break;
                }
            }
        }
        return pinged;
    }

    /**
     * 执行器
     *
     * @param cmd   CMD命令
     * @param filePath 需要在哪个目录下执行
     */
    private static List<String> execute(String cmd, String filePath) {
        Process process = null;
        List<String> result = new ArrayList<String>();
        try {
            if (filePath != null) {
                process = Runtime.getRuntime().exec(cmd, null, new File(filePath));
            } else {
                process = Runtime.getRuntime().exec(cmd);
            }
            BufferedReader bReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));
            String line = null;
            while ((line = bReader.readLine()) != null) {
                result.add(line);
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
class GenThread implements Runnable {
    private String ssid = null;
    private String password = null;
    GenThread(String ssid, String password) {
        this.ssid = ssid;
        this.password = password;
    }
    public void run() {
        String profileContent = Command.PROFILE_CONTENT.replace(Command.WIFI_NAME, ssid);
        profileContent = profileContent.replace(Command.WIFI_PASSWORD, password);
        String fileName = PASSWORD_TEMP_PATH + "\\" + password + ".xml";
        File file = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(profileContent.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ProfileGenerator.check(ssid,password);
    }
}
