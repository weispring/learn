package com.lxc.learn.junit;

import com.lxc.learn.common.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixianchun
 * @description
 * @date 2020/7/1
 */
@Slf4j
public class Main {

    /**
     *
     */

    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("./rr.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        Map map = new HashMap();
        map.put("nameSpace","CACHE_CONTRACT_ID_SPACE");
        while ((line = bufferedReader.readLine()) != null){
            map.put("key","CACHE_CONTRACT_INFO_HKBN_KEY_" + line);
            HttpClientUtil.postJsonBody("http://umall.hk.chinamobile.com/umall/business/consumer/hazlecast/deleteSpace",map);
        }
    }

}
