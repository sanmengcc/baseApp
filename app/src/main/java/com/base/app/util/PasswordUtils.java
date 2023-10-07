package com.base.app.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 密码工具类
 */
public class PasswordUtils {


    /**
     * 生成普通的MD5码
     * @param input
     * @return
     */
    public static String MD5(String input) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return "check jdk";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = input.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    //生成“盐”和加盐后的MD5码，并将盐混入到MD5码中
    public static String generate(String password) {
        //生成一个16位的随机数，也就是所谓的“盐”
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        //将“盐”加到明文中，并生成新的MD5码
        password = md5Hex(password + salt);
        //将“盐”混到新生成的MD5码中，之所以这样做是为了后期更方便的校验明文和秘文，也可以不用这么做，不过要将“盐”单独存下来，不推荐这种方式
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }


    //生成MD5
    private static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成密码
     * @param account
     * @param encryptPassword
     * @return
     */
    public static String generate(String account, String encryptPassword) {
        return MD5(account + encryptPassword).toLowerCase();
    }


    /**
     * 验证密码是否正确
     * @param account
     * @param encryptPassword
     * @param password
     * @return
     */
    public static boolean verify(String account, String encryptPassword,String password) {
        return password.equals(generate(account, encryptPassword));
    }

    public static void main(String[] args) {
        System.out.println(PasswordUtils.generate("admin",PasswordUtils.MD5("admin")));
    }
}
