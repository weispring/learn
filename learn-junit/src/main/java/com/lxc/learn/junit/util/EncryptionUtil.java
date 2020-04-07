package com.lxc.learn.junit.util;

import com.alibaba.fastjson.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import org.springframework.util.Base64Utils;

/**
 * Created by Administrator on 2020/3/27.
 */
public class EncryptionUtil {

    /**
     * RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024
     */
    public static final int KEY_SIZE = 2048;

    //公钥
    private final static String publicKeytest = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAroKCNZIu7LkPor9XNx7+0W0O0ykzqUkWPi6kDcE3atti/LgXmCvRc052S6hzkVVb2wFmfcRaAnYmQRRkmWCTUuZKu/R4jq2wqb8apERblrALeQFnfHf71S5A6luhMew3aLrZ8hvbnkZkBYGNpBFa+b2Q3i1x71EMS3I2pPSjJ8n+sqEtJa0zn91zIF950ahBfoAurxN4k2ZfdB94x+6b7GT31nygf3cSbLIuvumN5lZAnbp+lCQVi69usZ+UTEXyY/6cQvAy5G1WL8NwcA72wGMult2n4/1tNGPWWXvBye4g/yGyNXBB8UzasO0aKesW2kU9bKsBmSvEXkmL5Bsi8wIDAQAB";


    private final static String md5Key = "d2s6y8ik9lg64f5";
    private final static String charset = "utf-8";


    public String create(Long userId, Long orderId, String iccid,
                         Integer method,
                         String POSCode,
                         String shopCode,
                         String accountNumber,
                         String accountCode,
                         Long timestamp,
                         String versions) {
        Storer storer = new Storer();
        storer.setAppKey("111111");
        storer.setVersions(versions);
        storer.setUserId(userId);
        storer.setIccid(iccid);
        storer.setOrderId(orderId);
        storer.setShopCode(POSCode);
        storer.setPOSCode(shopCode);
        storer.setAccountCode(accountCode);
        storer.setAccountNumber(accountNumber);
        storer.setMethod(method);
        storer.setTimestamp(timestamp);
        storer.setDigest(encode("111111" + storer.getTimestamp() + "22222", "22222"));
        String s = JSONObject.toJSONString(storer);
        String messageEn = encipher(s, publicKeytest);
        return JSONObject.toJSONString(messageEn);
    }

    //MD5加密处理
    public static String encode(String src, String key) {
        try {
            //得到一个指定的编码格式的字节数组，Linux和windows默认的编码格式不同，所以要指定特定的编码
            byte[] data = src.getBytes(charset);
            byte[] keys = key.getBytes();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                //结合key和相应的数据进行加密操作,ofxx的作用是补码，byte是8bits，而int是32bits
                int n = (0xff & data[i]) + (0xff & keys[i % keys.length]);
                sb.append("S").append(n);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return src;
    }


    /**
     * 获取公钥对象
     *
     * @param publicKeyBase64
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public PublicKey getPublicKey(String publicKeyBase64)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicpkcs8KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBase64));
        PublicKey publicKey = keyFactory.generatePublic(publicpkcs8KeySpec);
        return publicKey;
    }


    /**
     * 使用公钥加密
     *
     * @param content         待加密内容
     * @param publicKeyBase64 公钥 base64 编码
     * @return 经过 base64 编码后的字符串
     */
    public String encipher(String content, String publicKeyBase64) {
        return encipher(content, publicKeyBase64, KEY_SIZE / 8 - 11);
    }

    /**
     * 使用公司钥加密（分段加密）
     *
     * @param content         待加密内容
     * @param publicKeyBase64 公钥 base64 编码
     * @param segmentSize     分段大小,一般小于 keySize/8（段小于等于0时，将不使用分段加密）
     * @return 经过 base64 编码后的字符串
     */
    public String encipher(String content, String publicKeyBase64, int segmentSize) {
        try {
            PublicKey publicKey = getPublicKey(publicKeyBase64);
            return encipher(content, publicKey, segmentSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段加密
     *
     * @param ciphertext  密文
     * @param key         加密秘钥
     * @param segmentSize 分段大小，<=0 不分段
     * @return
     */
    public String encipher(String ciphertext, java.security.Key key, int segmentSize) {
        try {
            // 用公钥加密
            byte[] srcBytes = ciphertext.getBytes();

            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA");
            // 根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] resultBytes = null;

            if (segmentSize > 0)
                resultBytes = cipherDoFinal(cipher, srcBytes, segmentSize); //分段加密
            else
                resultBytes = cipher.doFinal(srcBytes);
            String base64Str = Base64Utils.encodeToString(resultBytes);
            return base64Str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段大小
     *
     * @param cipher
     * @param srcBytes
     * @param segmentSize
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public byte[] cipherDoFinal(Cipher cipher, byte[] srcBytes, int segmentSize)
            throws IllegalBlockSizeException, BadPaddingException, IOException {
        if (segmentSize <= 0)
            throw new RuntimeException("分段大小必须大于0");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int inputLen = srcBytes.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > segmentSize) {
                cache = cipher.doFinal(srcBytes, offSet, segmentSize);
            } else {
                cache = cipher.doFinal(srcBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * segmentSize;
        }
        byte[] data = out.toByteArray();
        out.close();
        return data;
    }


    /**
     * 分段解密
     *
     * @param contentBase64 密文
     * @param key           解密秘钥
     * @param segmentSize   分段大小（小于等于0不分段）
     * @return
     */
    public String decipher(String contentBase64, java.security.Key key, int segmentSize) {
        try {
            // 用私钥解密
            byte[] srcBytes = Base64Utils.decodeFromString(contentBase64);
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher deCipher = Cipher.getInstance("RSA");
            // 根据公钥，对Cipher对象进行初始化
            deCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decBytes = null;//deCipher.doFinal(srcBytes);
            if (segmentSize > 0)
                decBytes = cipherDoFinal(deCipher, srcBytes, segmentSize); //分段加密
            else
                decBytes = deCipher.doFinal(srcBytes);

            String decrytStr = new String(decBytes);
            return decrytStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
