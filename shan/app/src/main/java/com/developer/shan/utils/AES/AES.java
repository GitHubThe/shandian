package com.developer.shan.utils.AES;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    private static AES instance = null;
    private static byte[] KEY;
    // Your IV Initialization Vector
    private  static byte[] ivx;

    protected AES() {

        try {
            //Your Secret Key
            ivx = "LQ3iat1X2xOoq8Rs".getBytes("UTF8");
            KEY = "UjOZLKjDXa8lDemX".getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
    }

    public static AES getInstance() {

        if (instance == null) {
            instance = new AES();
        }
        return instance;
    }

    public String encrypt(String message) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException,
            UnsupportedEncodingException, InvalidAlgorithmParameterException {

        byte[] srcBuff = message.getBytes("UTF8");

        SecretKeySpec skeySpec = new SecretKeySpec(KEY, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivx);
        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        ecipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);

        byte[] dstBuff = ecipher.doFinal(srcBuff);

        String base64 = Base64.encodeToString(dstBuff, Base64.DEFAULT);

        return base64;

    }

    public String decrypt(String encrypted) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {

        SecretKeySpec skeySpec = new SecretKeySpec(KEY, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivx);

        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        ecipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);

        byte[] raw = Base64.decode(encrypted, Base64.DEFAULT);

        byte[] originalBytes = ecipher.doFinal(raw);

        String original = new String(originalBytes, "UTF8");

        return original;

    }
}
