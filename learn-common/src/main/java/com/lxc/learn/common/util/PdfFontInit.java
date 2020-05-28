package com.lxc.learn.common.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.zeroturnaround.zip.ZipUtil;

import javax.annotation.PostConstruct;
import java.io.*;

@Slf4j
public class PdfFontInit {

    /**
     * 初始化
     */

    static {
        try {
            log.info("初始化");
            // 只运行在liunx系统下
            String ocName = System.getProperty("os.name").toLowerCase();

/*            if (ocName.indexOf("linux") < 0) {
                log.warn(ocName + " 系统不进行Wkhtmltopdf组件初始化");
                return;
            }*/

            String tmpdir = System.getProperty("java.io.tmpdir");
            String separator = System.getProperty("file.separator");
            String homedir = separator + "opt" + separator + "app-root" + separator + "src";
            log.info("HOME目录:{}", homedir);
            log.info("临时目录:{} ", tmpdir);
            // 复制 fonts
            Resource fontResource = new ClassPathResource("wkhtmltopdf" + separator + "fonts" + separator + "simsun.ttc");
            //linux服务器$HOME/.fonts目录下
            File fontsFile = new File(homedir + separator + ".fonts" + separator + "simsun.ttc");
            if (!fontsFile.getParentFile().exists()) {
                fontsFile.getParentFile().mkdirs();
            }
            FileCopyUtils.copy(fontResource.getInputStream(), new FileOutputStream(fontsFile));

            log.info(fontsFile.getParentFile() + "字体simsun 复制文件成功");


            // 复制 wkhtmltopdf
            Resource resource = new ClassPathResource("wkhtmltopdf" + separator + "wkhtmltox.zip");
            File targetDir = new File(tmpdir + separator + "wkhtmltox.zip");
            FileCopyUtils.copy(resource.getInputStream(), new FileOutputStream(targetDir));
            log.info("wkhtmltopdf 复制文件成功");

            // 解压
            ZipUtil.unpack(targetDir, new File(tmpdir));
            log.info("wkhtmltopdf 解压成功");

            // 设置执行脚本权限
            if (ocName.indexOf("linux") > 0) {
                Runtime.getRuntime().exec("chmod 777 -R " + tmpdir + separator + "wkhtmltox");
            }
            log.info("wkhtmltopdf 设置权限成功");
        } catch (IOException ex) {
            log.info("wkhtmltopdf 初始化失败：{}", ex);
        }


        try {
            if(log.isDebugEnabled()){
                log.debug("【PDF转图片】 初始化系统资源...");
            }
            String ocName=System.getProperty("os.name").toLowerCase();

            String fontPath=File.separator + "opt" + File.separator + "app-root" + File.separator + "src";
            System.setProperty("user.home",fontPath);
            log.info("【user.home】path: {}",System.getProperty("user.home"));
            String path = System.getProperty("pdfbox.fontcache");
            log.info("[pdfbox.fontcache] path={}",path);
            path = System.getProperty("user.home");
            log.info("[user.home] path={}",path);

            fontPath+=File.separator+".fonts";
            if(log.isDebugEnabled()){
                log.debug("【PDF转图片】 资源路径: {}",fontPath);
            }

            //AdobeSongStd-Light.otf
            // 复制 fonts
            Resource fontResource = new ClassPathResource("fonts"+File.separator+"AdobeSongStd-Light.otf");
            //linux服务器$HOME/.fonts目录下
            String fontTempName=fontPath+File.separator+"AdobeSongStd-Light.otf";
            File fontFile = new File(fontTempName);
            if (!fontFile.getParentFile().exists()) {
                fontFile.getParentFile().mkdirs();
            }
            FileCopyUtils.copy(fontResource.getInputStream(), new FileOutputStream(fontFile));
            log.info(fontFile.getParentFile() + "复制文件"+fontFile.getName()+"成功");

            //STSong-Light.ttf
            // 复制 fonts
            fontResource = new ClassPathResource("fonts"+File.separator+"STSong-Light.ttf");
            //linux服务器$HOME/.fonts目录下
            fontTempName=fontPath+File.separator+"STSong-Light.ttf";
            fontFile = new File(fontTempName);
            FileCopyUtils.copy(fontResource.getInputStream(), new FileOutputStream(fontFile));
            log.info(fontFile.getParentFile() + "复制文件"+fontFile.getName()+"成功");
            // 设置执行脚本权限
            if (ocName.indexOf("linux") > 0) {
                Runtime.getRuntime().exec("chmod -R 777 "+fontPath);
            }

            log.info(fontPath+"-->设置权限成功");
            String[] command=new String[]{"/bin/sh","-c","cd "+fontPath};
            exec(command);
            log.info("进入目录：{}",fontPath);
            command=new String[]{"/bin/sh","-c","mkfontscale"};
            exec(command);
            exec(command);
            command=new String[]{"/bin/sh","-c","mkfontdir"};
            exec(command);
            log.info(fontPath+"-->更新字体索引信息成功");
            command=new String[]{"/bin/sh","-c","fc-cache -fv"};
            exec(command);
            log.info(fontPath+"-->刷新字体缓存成功");
            command=new String[]{"/bin/sh","-c","source /etc/profile"};
            exec(command);
            log.info(fontPath+"-->重读环境变量成功");
            command=new String[]{"/bin/sh","-c","fc-list"};
            exec(command);
            log.info(fontPath+"-->查看字体成功");
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    public static final void exec(String[] command) throws IOException{
        BufferedInputStream in = null;
        BufferedReader br = null;
        try {
            // Execute Shell Command
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            in = new BufferedInputStream(p.getInputStream());
            br = new BufferedReader(new InputStreamReader(in));
            String s;
            while ((s = br.readLine()) != null) {
                log.info("{}",s);
            }
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        } finally {
            if (br != null){
                br.close();
            }
            if (in != null){
                in.close();
            }
        }
    }
}
