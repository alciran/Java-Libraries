package com.conarflib.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Class with method used for generate String values.
 * 
 * @author Alciran Franco
 */
public class StringGeneratorUtil {

    private static char[] SYMBOLS = "^$*.[]{}()?!@#%&/,><_~".toCharArray();
    private static char[] LOWERCASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static char[] UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static char[] NUMBERS = "0123456789".toCharArray();
    private static char[] ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789^$*.[]{}()?!@#%&/,><_~"
            .toCharArray();
    private static Random rand = new SecureRandom();

    /**
     * Generate a random password with: chars (lowercase and uppercase), special
     * chars and number.
     * 
     * @param stringLength Length of String to generate.
     * @return String password.
     */
    public static String generateRandomPassword(Integer stringLength) {
        char[] password = new char[stringLength];
        password[0] = LOWERCASE[rand.nextInt(LOWERCASE.length)];
        password[1] = UPPERCASE[rand.nextInt(UPPERCASE.length)];
        password[2] = NUMBERS[rand.nextInt(NUMBERS.length)];
        password[3] = SYMBOLS[rand.nextInt(SYMBOLS.length)];
        for (int i = 4; i < stringLength; i++) {
            password[i] = ALL_CHARS[rand.nextInt(ALL_CHARS.length)];
        }
        for (int i = 0; i < password.length; i++) {
            int randomPosition = rand.nextInt(password.length);
            char temp = password[i];
            password[i] = password[randomPosition];
            password[randomPosition] = temp;
        }
        return new String(password);
    }

    /**
     * Generate a Hash md5 from a String.
     * 
     * @param string The String to generate hash md4.
     * @return String with hash.
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String generateHashMD5(String string) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] passwordBytes = string.getBytes("UTF-8");
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = messageDigest.digest(passwordBytes);

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            stringBuffer.append(Integer.toString((md5Bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }

    /**
     * Generate a string with letters (lower case) and numbers.
     * 
     * @param stringLength Length of String to generate.
     * @return String of lenght provided.
     */
    public static String generateWithLettersAndNumbers(int stringLength) {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        String key = "";
        int index = -1;
        for (int i = 0; i < stringLength; i++) {
            index = rand.nextInt(chars.length());
            key += chars.substring(index, index + 1);
        }
        return key;
    }

}
