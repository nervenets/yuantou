package com.nervenets.general.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

    /**
     * aes 加密
     *
     * @param data
     * @return
     */
    public static String encryptData(String data, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return new String(Base64.encodeBase64(encrypted));
        } catch (Exception e) {
            System.out.println("aes 加密 失败 ：" + e.getMessage());
        }
        return null;
    }


    /**
     * aes 解密
     *
     * @param data 密文
     * @return
     */
    public static String decryptData(String data, String key, String iv) {
        try {
            byte[] encrypted1 = Base64.decodeBase64(data.getBytes());
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original);
        } catch (Exception e) {
            //
        }
        return null;
    }


//    public static void main(String[] args) {
//        String key = "luckyy1515168000";
//        String iv = "yunong1515168000";
//        String data = "php和java互通!";
//        String enStr = AESUtils.encryptData(data, key, iv);
//        System.out.println("加密:" + enStr);
//        String deStr = AESUtils.decryptData("EXG85Xg14n0A9cvJFzbA4mc4RSxXn70a4vuCOLn8IHUGGGoSmU3NeONpD4X7DYxA", key, iv);
//        System.out.println("解密:" + deStr);
//    }
}
