package com.lxc.learn.jdk.wirelessnet;

/**
 * @Auther: lixianchun
 * @Date: 2020/2/22 11:50
 * @Description:
 */
public class Command {

    public static String CONNECT = "netsh wlan connect name=SSID_NAME";

    public static String PING_DOMAIN = "192.168.1.1";

    public static String ADD_PROFILE = "netsh wlan add profile filename=FILE_NAME";


    public static String PASSWORD_TEMP_PATH = "E:\\wirelessnet";

    public static String CMD_TARGET_DIR = "C:\\Users\\lixianchun";

    public static String WIFI_NAME = "SSID_NAME";

    public static String WIFI_PASSWORD = "MIMA";

    public static String PROFILE_CONTENT = "<?xml version=\"1.0\"?>\n" +
            "<WLANProfile xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v1\">\n" +
            "\t<name>SSID_NAME</name>\n" +
            "\t<SSIDConfig>\n" +
            "\t\t<SSID>\n" +
            "\t\t\t<hex>4368696E614E65742D4134694E</hex>\n" +
            "\t\t\t<name>SSID_NAME</name>\n" +
            "\t\t</SSID>\n" +
            "\t</SSIDConfig>\n" +
            "\t<connectionType>ESS</connectionType>\n" +
            "\t<connectionMode>auto</connectionMode>\n" +
            "\t<MSM>\n" +
            "\t\t<security>\n" +
            "\t\t\t<authEncryption>\n" +
            "\t\t\t\t<authentication>WPA2PSK</authentication>\n" +
            "\t\t\t\t<encryption>AES</encryption>\n" +
            "\t\t\t\t<useOneX>false</useOneX>\n" +
            "\t\t\t</authEncryption>\n" +
            "\t\t\t<sharedKey>\n" +
            "\t\t\t\t<keyType>passPhrase</keyType>\n" +
            "\t\t\t\t<protected>false</protected>\n" +
            "\t\t\t\t<keyMaterial>MIMA</keyMaterial>\n" +
            "\t\t\t</sharedKey>\n" +
            "\t\t</security>\n" +
            "\t</MSM>\n" +
            "\t<MacRandomization xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v3\">\n" +
            "\t\t<enableRandomization>false</enableRandomization>\n" +
            "\t</MacRandomization>\n" +
            "</WLANProfile>";






}
