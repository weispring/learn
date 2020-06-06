package com.lxc.learn.arithmetic.file;

import java.io.*;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import lombok.experimental.UtilityClass;
import org.bouncycastle.util.encoders.Base64;

/**
 * Created by zhouman on 2020/3/31
 */
@UtilityClass
public class AESUtils {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;
    private static final int CACHE_SIZE = 684;

    /**
     * <p>
     * 生成随机密钥
     * </p>
     *
     * @return
     * @throws Exception
     */
    public String getSecretKey() throws Exception {
        return getSecretKey(null);
    }


    /**
     * <p>
     * 生成密钥
     * </p>
     *
     * @param seed 密钥种子
     * @return
     * @throws Exception
     */
    public String getSecretKey(String seed) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom;
        if (seed != null && !"".equals(seed)) {
            secureRandom = new SecureRandom(seed.getBytes());
        } else {
            secureRandom = new SecureRandom();
        }
        keyGenerator.init(KEY_SIZE, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return encode(secretKey.getEncoded());
    }

    /**
     * <p>
     * 加密
     * </p>
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public byte[] encrypt(byte[] data, String key) throws Exception {

        Key k = toKey(decode(key));
        byte[] raw = k.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }

    /**
     * <p>
     * 文件加密
     * </p>
     *
     * @param key
     * @param sourceFilePath
     * @param destFilePath
     * @throws Exception
     */
    public void encryptFile(String key, String sourceFilePath, String destFilePath) throws Exception {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath);
        if (sourceFile.exists() && sourceFile.isFile()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
            try (InputStream in = new FileInputStream(sourceFile)) {
                CipherInputStream cin;
                try (OutputStream out = new FileOutputStream(destFile)) {
                    Key k = toKey(decode(key));
                    byte[] raw = k.getEncoded();
                    SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
                    Cipher cipher = Cipher.getInstance(ALGORITHM);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
                    cin = new CipherInputStream(in, cipher);
                    byte[] cache = new byte[CACHE_SIZE];
                    int nRead = 0;
                    while ((nRead = cin.read(cache)) != -1) {
                        System.out.println("en : " + nRead);
                        out.write(java.util.Base64.getEncoder().encode(removeBlank(cache,nRead)));
                        out.flush();
                    }
                    out.close();
                }
                cin.close();
                in.close();
            }
        }
    }

    /**
     * <p>
     * 解密
     * </p>
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public byte[] decrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decode(key));
        byte[] raw = k.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }

    /**
     * <p>
     * 文件解密
     * </p>
     *
     * @param key
     * @param sourceFile
     * @param decryptFilePath
     * @throws Exception
     */
    public File decryptFile(String key, File sourceFile, String decryptFilePath) throws Exception {
        File destFile = null;
        destFile = new File(decryptFilePath);
        if (sourceFile.exists() && sourceFile.isFile()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
            try (FileInputStream in = new FileInputStream(sourceFile)) {
                try (FileOutputStream out = new FileOutputStream(destFile)) {
                    Key k = toKey(decode(key));
                    byte[] raw = k.getEncoded();
                    SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
                    Cipher cipher = Cipher.getInstance(ALGORITHM);
                    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                    CipherOutputStream cout = new CipherOutputStream(out, cipher);
                    byte[] cache = new byte[CACHE_SIZE];
                    int nRead = 0;
                    while ((nRead = in.read(cache)) != -1) {
                        System.out.println("--- : " + nRead);
                        byte[] leave = new byte[24];
                        boolean endFill = false;
                        if (nRead < CACHE_SIZE){
                            nRead = nRead - 24;
                            System.arraycopy(cache,nRead-24,leave,0,24);
                            endFill = true;
                        }

                        byte[] bytes = removeBlank(cache,nRead);
                        System.out.println(bytes[534]);
                        System.out.println(bytes[535]);
                        //System.out.println(bytes[536]);
                        try {
                            cout.write(java.util.Base64.getDecoder().decode(bytes));
                            if (endFill){
                                cout.write(java.util.Base64.getDecoder().decode(leave));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
           /*                 System.out.println(bytes[537]);
                            System.out.println(bytes[538]);*/
                        }

                    }
                    cout.flush();
                    cout.close();
                    out.close();
                }
                in.close();
            }
        }
        return destFile;
    }

    /**
     * <p>
     * 转换密钥
     * </p>
     *
     * @param key
     * @return
     * @throws Exception
     */
    private Key toKey(byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        return secretKey;
    }

    public byte[] decode(String base64) throws Exception {
        return Base64.decode(base64.getBytes());
    }

    public String encode(byte[] bytes) throws Exception {
        return new String(Base64.encode(bytes));
    }

    /**
     * 加密包装读入流，读取规则是512byte,剩余的部分，最后还有16byte,导致base64结果有两次结束标记（**==）,会导致解码出错
     *
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        //
        String source = "F:\\own-test\\source.txt";
        String encrypt = "F:\\own-test\\encode.txt";
        String decrypt = "F:\\own-test\\decode.txt";
        String key = "4OQ/XO2GFBVIgXXXMidk/Q==";
        encryptFile(key,source,encrypt);
        decryptFile(key,new File(encrypt),decrypt);

    }

    private static byte[] removeBlank(byte[] data, int length){
        byte[] bytes = new byte[length];
        System.arraycopy(data,0,bytes,0,length);
        return bytes;
    }
}
