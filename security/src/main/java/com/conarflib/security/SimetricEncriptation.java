package com.conarflib.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SimetricEncriptation {

    private SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");

    }

    public SecretKeySpec getKey(String ext_pass) throws Exception {
        byte[] salt = new String("12345678").getBytes();
        String password = "Conarf!Word@Cript#" + ext_pass;
        return createSecretKey(password.toCharArray(), salt, 40000, 128);
    }

    public String encrypt(String value, SecretKeySpec key)
            throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException,
            InvalidParameterSpecException, NoSuchAlgorithmException, NoSuchPaddingException {

        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(value.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    public String decrypt(String propertyName, SecretKeySpec key)
            throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, IOException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        String iv = propertyName.split(":")[0];
        String property = propertyName.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private byte[] base64Decode(String value) throws IOException {
        return Base64.getDecoder().decode(value);
    }

}
