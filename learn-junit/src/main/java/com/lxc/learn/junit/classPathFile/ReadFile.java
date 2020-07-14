package com.lxc.learn.junit.classPathFile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import sun.security.krb5.internal.PAData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 前三种方法在开发环境(IDE中)和生产环境(linux部署成jar包)都可以读取到，第四种只有开发环境 时可以读取到，生产环境读取失败。
 * 推测主要原因是springboot内置tomcat，打包后是一个jar包，无法直接读取jar包中的文件，读取只能通过类加载器读取。
 * 前三种都可以读取到其实殊途同归，直接查看底层代码都是通过类加载器读取文件流，类加载器可以读取jar包中的编译后的class文件，当然也是可以读取jar包中的excle模板了。
 *
 * @Auther: lixianchun
 * @Date: 2020/6/28 21:31
 * @Description:
 */
@Slf4j
public class ReadFile {

    public static void main(String[] args) {
        String path = "static/index.html";
        testFile(path);
/*        ResourceLoader resourceLoader;
        resourceLoader.getResource()*/
    }


    public static void testFile(String path){
        test((t)->{
            ClassPathResource classPathResource = new ClassPathResource(path);
            try {
                File file = classPathResource.getFile();
                if (classPathResource.exists() && file.exists()){
                    log.info("ClassPathResource:{}",true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });

        test((t)->{
            try {
                //本质任然是调用 getResource
                InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
                log.info("getResourceAsStream:{}",inputStream.available());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });

        test((t)->{
            try {
                InputStream inputStream = new ReadFile().getClass().getResourceAsStream(path);
                log.info("new ReadFile().getClass().getResourceAsStream:{}",inputStream == null ? false : inputStream.available());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        test((t)->{
            try {
                File file = ResourceUtils.getFile("classpath:"+path);
                log.info("ResourceUtils.getFile:{}",file.exists());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        FileSystemResource resource = new FileSystemResource("src/test/resources/test.txt");
        final File file = resource.getFile();
    }

    public static void test(Function consumer){
        try {
            consumer.apply("");
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }
}
