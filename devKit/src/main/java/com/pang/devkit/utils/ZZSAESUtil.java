package com.pang.devkit.utils;

import android.util.Base64;

import java.net.URLEncoder;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 由hoozy于2023/2/28 09:12进行创建
 * 描述：
 */
public class ZZSAESUtil {
    public static String LoginEncrypt(String sSrc, String key) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(LoginEncryptGeneralKey(key), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");// "算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        String str = new String(Base64.encodeToString(encrypted, 0));
        str = URLEncoder.encode(str, "UTF-8");
        return str;
    }

    /**
     * 构建密钥字节码
     *
     * @param keyStr
     * @return
     * @throws Exception
     */
    private static byte[] LoginEncryptGeneralKey(String keyStr) throws Exception {
        byte[] bytes = keyStr.getBytes("utf-8");
       /* MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bytes);*/
        return bytes;
    }


    /**
     * 提供密钥和向量进行加密
     *
     * @param sSrc
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static String Encrypt(String sSrc, String key, String iv) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(GeneralKey(key), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
        IvParameterSpec _iv = new IvParameterSpec(GeneralIv(iv));// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, _iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        //   return new String(Base64.encodeBase64(encrypted));
        return new String(Base64.encodeToString(encrypted, 0));
    }

    /**
     * 构建密钥字节码
     *
     * @param keyStr
     * @return
     * @throws Exception
     */
    private static byte[] GeneralKey(String keyStr) throws Exception {
        byte[] bytes = keyStr.getBytes("utf-8");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bytes);
        return md.digest();
    }

    /**
     * 构建加解密向量字节码
     *
     * @param keyStr
     * @return
     * @throws Exception
     */
    private static byte[] GeneralIv(String keyStr) throws Exception {
        byte[] bytes = keyStr.getBytes("utf-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        return md.digest();
    }
}
