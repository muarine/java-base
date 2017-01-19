package com.muarine.algorithms;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

/**
 * SecurityUtil
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 2017 1/19/17 16:47
 * @since 2.0.0
 */
public class SecurityUtil {

    private static final String ALGORITHM_3DES = "DESede";
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public SecurityUtil() {
    }

    public static byte[] encrypt3DES(String encryptPassword, byte[] encryptByte) {
        try {
            Cipher e = init3DES(encryptPassword, 1);
            byte[] doFinal = e.doFinal(encryptByte);
            return doFinal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt3DES(String encryptPassword, String encryptStr) {
        try {
            Cipher e = init3DES(encryptPassword, 1);
            byte[] enBytes = e.doFinal(encryptStr.getBytes(DEFAULT_CHARSET));
            return Base64.encodeBase64String(enBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt3DES(String decryptPassword, byte[] decryptByte) {
        try {
            Cipher e = init3DES(decryptPassword, 2);
            byte[] doFinal = e.doFinal(decryptByte);
            return doFinal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt3DES(String decryptPassword, String decryptString) {
        try {
            Cipher e = init3DES(decryptPassword, 2);
            byte[] deBytes = e.doFinal(Base64.decodeBase64(decryptString));
            return new String(deBytes, DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Cipher init3DES(String decryptPassword, int cipherMode) throws Exception {
        SecretKeySpec deskey = new SecretKeySpec(decryptPassword.getBytes(), "DESede");
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(cipherMode, deskey);
        return cipher;
    }
}
