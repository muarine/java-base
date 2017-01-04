package com.muarine.algorithms;

import org.apache.commons.codec.binary.Base64;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * RSAAlgorithms
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 2016 11/18/16 17:59
 * @since 2.0.0
 */
public class RSAAlgorithms {

    public static void main(String[] args) throws IOException {
        String originText = "草你大爷";
        byte[] encrypte = RSAAlgorithms.encrypte(originText);

        System.out.println(new String(encrypte , CHARSET));

        System.out.println(RSAAlgorithms.verify(encrypte));

        System.out.println(new String(RSAAlgorithms.decrypte(encrypte) , CHARSET));

    }

    /**
     * 加密
     *
     * @param originText
     * @return
     */
    private static byte[] encrypte(String originText) {

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateCrtKeySpec rsaPrivateCrtKeySpec = initPrivateCrtKeySpec();
            //对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            PrivateKey privateKey = keyFactory.generatePrivate(rsaPrivateCrtKeySpec);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64(cipher.doFinal(originText.getBytes()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidKeySpecException | IOException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * URLEncoder charset
     */
    public static final String CHARSET = "UTF-8";


    /**
     * 解密
     *
     * @param encryptText
     * @return
     */
    private static byte[] decrypte(byte[] encryptText) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPublicKeySpec = initRSAPublicKeySpec();
            //对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            PublicKey publicKey1 = keyFactory.generatePublic(rsaPublicKeySpec);
            cipher.init(Cipher.DECRYPT_MODE, publicKey1);
            return cipher.doFinal(Base64.decodeBase64(encryptText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidKeySpecException | IOException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 校验加密数据
     *
     * @param encryData
     */
    private static boolean verify(byte[] encryData) throws IOException {

        RSAPrivateCrtKeySpec spec = initPrivateCrtKeySpec();
        RSAPublicKeySpec publicKeySpec = initRSAPublicKeySpec();

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(spec);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey);
            signature.update(encryData);
            byte[] signBytes = signature.sign();

            Signature verifySign = Signature.getInstance("SHA1withRSA");
            verifySign.initVerify(publicKey);
            verifySign.update(encryData);

            return verifySign.verify(signBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SignatureException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 初始化key
     *
     * openssl genrsa -out private.key 4096
     *
     * @return
     * @throws IOException
     */
    private static byte[] initPrivateKey() throws IOException {

        try (InputStream inputStream = RSAAlgorithms.class.getResourceAsStream("/private.key");){
            byte[] keyBytes = new byte[inputStream.available()];
            inputStream.read(keyBytes);
            String keyString = new String(keyBytes, CHARSET);
            String trimmedPrivateKey = keyString.replaceAll("(-+BEGIN (RSA )?PRIVATE KEY-+\\r?\\n|-+END (RSA )?PRIVATE KEY-+\\r?\\n?)", "");
            return Base64.decodeBase64(trimmedPrivateKey);
        }
    }

    private static RSAPrivateCrtKeySpec initPrivateCrtKeySpec() throws IOException {
        DerInputStream derReader = new DerInputStream(initPrivateKey());
        DerValue[] seq = derReader.getSequence(0);

        if (seq.length < 9) {
            System.out.println("Could not parse a PKCS1 private key.");
            return null;
        }

        // skip version seq[0];
        BigInteger modulus = seq[1].getBigInteger();
        BigInteger publicExp = seq[2].getBigInteger();
        BigInteger privateExp = seq[3].getBigInteger();
        BigInteger prime1 = seq[4].getBigInteger();
        BigInteger prime2 = seq[5].getBigInteger();
        BigInteger exp1 = seq[6].getBigInteger();
        BigInteger exp2 = seq[7].getBigInteger();
        BigInteger crtCoef = seq[8].getBigInteger();
        return new RSAPrivateCrtKeySpec(modulus, publicExp, privateExp, prime1, prime2, exp1, exp2, crtCoef);
    }

    private static RSAPublicKeySpec initRSAPublicKeySpec() throws IOException {

        DerInputStream derReader = new DerInputStream(initPrivateKey());
        DerValue[] seq = derReader.getSequence(0);

        if (seq.length < 9) {
            System.out.println("Could not parse a PKCS1 private key.");
            return null;
        }

        // skip version seq[0];
        // int version = seq[0].getBigInteger().intValue();
        BigInteger modulus = seq[1].getBigInteger();
        BigInteger publicExp = seq[2].getBigInteger();
        return new RSAPublicKeySpec(modulus, publicExp);
    }
}
