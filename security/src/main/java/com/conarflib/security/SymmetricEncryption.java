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

/**
 * This class is used to encrypt and decrypt a string using symmetric method,
 * provide a single secret key. To encrypt, a concatenation is performed with
 * the secret key and a static password on this class.
 * 
 * @author Alciran Franco
 */
public class SymmetricEncryption {

    private static String STATIC_KEY = "Conarf!Word@Cript#";

    /**
     * Method to get a SecretKeySpec provide a secret key value.
     * 
     * @param secretKey Secret Key
     */
    public static SecretKeySpec getKey(String secretKey)
            throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        byte[] salt = new String("12345678").getBytes();
        String key = STATIC_KEY + secretKey;
        return createSecretKey(key.toCharArray(), salt, 40000, 128);
    }

    /**
     * This method encrypt a String using concatenation of password + secret key
     * provided.
     * 
     * @param value     String to encrypt
     * @param secretKey Secret key
     * @return A String with encrypt value.
     */
    public static String encrypt(String value, SecretKeySpec secretKey)
            throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException,
            InvalidParameterSpecException, NoSuchAlgorithmException, NoSuchPaddingException {

        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(value.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    /**
     * This method decrypt a String using concatenation of password + secret key
     * provided
     * 
     * @param value     String to decrypt
     * @param secretKey Secret key
     * @return A String with decrypt value.
     */
    public static String decrypt(String value, SecretKeySpec secretKey)
            throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, IOException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        String iv = value.split(":")[0];
        String property = value.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] base64Decode(String value) throws IOException {
        return Base64.getDecoder().decode(value);
    }

}
