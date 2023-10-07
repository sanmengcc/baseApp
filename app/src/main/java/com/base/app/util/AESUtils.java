package com.base.app.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

/**
 * AES加解密
 */
public class AESUtils {

    private static String Algorithm = "AES";
    private static String AlgorithmProvider = "AES/CBC/PKCS5Padding"; //算法/模式/补码方式


    public static byte[] generatorKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(Algorithm);
        keyGenerator.init(256);//默认128，获得无政策权限后可为192或256
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    public static IvParameterSpec getIv(String iv) throws UnsupportedEncodingException {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("utf-8"));
        return ivParameterSpec;
    }

    public static String encrypt(String src, String key, String iv) {
        try {
            SecretKey secretKey = new SecretKeySpec(key.getBytes("utf-8"), Algorithm);
            IvParameterSpec ivParameterSpec = getIv(iv);
            Cipher cipher = Cipher.getInstance(AlgorithmProvider);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            byte[] cipherBytes = cipher.doFinal(src.getBytes(Charset.forName("utf-8")));
            return base64ToString(cipherBytes);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String src, String key, String iv) {
        try {
            SecretKey secretKey = new SecretKeySpec(key.getBytes("utf-8"), Algorithm);
            IvParameterSpec ivParameterSpec = getIv(iv);
            Cipher cipher = Cipher.getInstance(AlgorithmProvider);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] hexBytes = stringToBase64(src);
            byte[] plainBytes = cipher.doFinal(hexBytes);
            return new String(plainBytes, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 字符串装换成 Base64
     */

    public static byte[] stringToBase64(String key) throws Exception {
        return Base64.decodeBase64(key.getBytes());
    }

    /**
     * Base64装换成字符串
     */
    public static String base64ToString(byte[] key) throws Exception {
        return new Base64().encodeToString(key);
    }

    public static void main(String[] args) {
        System.out.println(AESUtils.encrypt("zjsm_shop", "68dd2a6613ede433", "68dd2a6613ede433"));
        System.out.println(AESUtils.decrypt("jScGkDqjjqUxd89UUFSMwQ==", "68dd2a6613ede433", "68dd2a6613ede433"));
    }

}

