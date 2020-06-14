package com.lxc.learn.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/11
 */
@Slf4j
public class FileUtil {

    public List<String> log(Integer index,Integer size) throws Exception {
        if (size == null || size == 0){
            size = 10;
        }
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(new File("./logs/application.log"));
            br = new BufferedReader(fr);

            if (index > 1){
                Integer skip = (index - 1 ) * size;
                for (int i=0;i< skip;i++){
                    br.readLine();
                }
            }
            String line = null;
            List list = new ArrayList(size.intValue());
            while ((line = br.readLine()) != null){
                list.add(line);
            }
            return list;
        }finally {
            if (br != null){
                br.close();
            }
            if (fr != null){
                fr.close();
            }
        }
    }

}
