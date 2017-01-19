package com.muarine.algorithms;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * RSAUtil
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 2017 1/19/17 16:43
 * @since 2.0.0
 */
public class RSAUtil {

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public RSAUtil() {
    }

    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec e = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(e);
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public static boolean verify(String content, String sign, String publicKey) {
        try {
            KeyFactory e = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = e.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));
            boolean bverify = signature.verify(Base64.decodeBase64(sign));
            return bverify;
        } catch (Exception var8) {
            var8.printStackTrace();
            return false;
        }
    }

    public static String encrpyt(String content, String publicKeyStr) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, getPublicKey(publicKeyStr));
        byte[] enBytes = cipher.doFinal(content.getBytes(DEFAULT_CHARSET));
        return Base64.encodeBase64String(enBytes);
    }

    public static String decrypt(String content, String privateKeyStr) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, getPrivateKey(privateKeyStr));
        byte[] deBytes = cipher.doFinal(Base64.decodeBase64(content));
        return new String(deBytes, DEFAULT_CHARSET);
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.encodeBase64(key.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        return Base64.encodeBase64String(keyBytes);
    }

    public static <T> String getSignatureContent(Map<String, T> params) {
        if(params == null) {
            return null;
        } else {
            StringBuffer content = new StringBuffer();
            ArrayList keys = new ArrayList(params.keySet());
            Collections.sort(keys);

            for(int i = 0; i < keys.size(); ++i) {
                String key = (String)keys.get(i);
                String value = params.get(key).toString();
                content.append((i == 0?"":"&") + key + "=" + value);
            }

            return content.toString();
        }
    }

    public static String getListSignatureContent(List<Map> mapList) {
        if(mapList == null) {
            return null;
        } else {
            ArrayList listStr = new ArrayList();
            Iterator var2 = mapList.iterator();

            while(var2.hasNext()) {
                Map map = (Map)var2.next();
                listStr.add(getSignatureContent(map));
            }

            Collections.sort(listStr);
            return listStr.toString();
        }
    }
}
