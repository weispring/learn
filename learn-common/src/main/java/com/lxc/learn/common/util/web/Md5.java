package com.lxc.learn.common.util.web;

import com.lxc.learn.common.constant.SystemConstant;
import com.lxc.learn.common.util.Exceptions;
import com.lxc.learn.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.comparator.ComparableComparator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/18 16:17
 */
@Slf4j
public class Md5 {

    /**
     * MD5签名算法
     *
     * @param map 要参与签名的数据Map
     * @param apiKey API密钥
     * @return 签名
     */
    public static String getMD5SignFromMap(Map<String, ? extends Object> map, String apiKey) {
        return getSignFromMap(map, apiKey, SystemConstant.MD5);
    }

    /**
     * 签名算法
     *
     * @param map 要参与签名的数据Map
     * @param apiKey API密钥
     * @param algorithm 运算法测
     * @return 签名
     */
    public static String getSignFromMap(Map<String, ? extends Object> map, String apiKey,
                                        String algorithm) {
        ArrayList<String> list = new ArrayList<String>();
        if (null != map && !map.isEmpty()) {
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                if (StringUtil.isEmpty(entry.getValue())
                        || "sign".equalsIgnoreCase(entry.getKey())) {
                    continue;
                }

                if (entry.getValue() instanceof Collection
                        && !((Collection) entry.getValue()).isEmpty()) {
                    list.add(entry.getKey() + "=" + JsonUtil.objectToJson(entry.getValue()) + "&");
                } else if (entry.getValue() instanceof Map
                        && !((Map) entry.getValue()).isEmpty()) {
                    list.add(entry.getKey() + "=" + JsonUtil.objectToJson(entry.getValue()) + "&");
                } else {
                    list.add(entry.getKey() + "=" + entry.getValue() + "&");
                }
            }
        }
        return createSign(list, apiKey, algorithm);
    }

    /**
     * 生成签名
     *
     * @param list (key=value&)类型字符串列表
     * @param apiKey 签名密钥
     * @param algorithm 运算法测
     * @return 签名
     */
    private static String createSign(ArrayList<String> list, String apiKey, String algorithm) {
        String result = null;
        if (null != list && !list.isEmpty()) {
            int size = list.size();
            String[] arrayToSort = list.toArray(new String[size]);
            Arrays.sort(arrayToSort, new ComparableComparator());
            StringBuilder stringSignTemp = new StringBuilder();
            for (int i = 0; i < size; i++) {
                stringSignTemp.append(arrayToSort[i]);
            }
            String signTempStr;
            if (!StringUtil.isEmpty(apiKey)) {
                stringSignTemp.append("key=" + apiKey);
                signTempStr = stringSignTemp.toString();
            } else {
                signTempStr = stringSignTemp.substring(0, stringSignTemp.length() - 1);
            }
            log.info("createSign Before MD5:" + signTempStr);
            result = md5(signTempStr, algorithm).toUpperCase();
        }
        log.info("createSign SendSmsResult:" + result);
        return result;
    }

    public static String md5(String str, String algorithm) {
        if (StringUtil.isEmpty(str)) {
            return null;
        } else {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
                byte[] digest = messageDigest.digest(str.getBytes("utf-8"));
                return new String(Hex.encodeHex(digest));
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                throw Exceptions.unchecked(e);
            }
        }
    }



}
