package com.majazeh.risloo.utils.managers;

import android.util.Base64;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CryptoManager {

    /*
    ---------- Funcs ----------
    */

    public static String encrypt(String value, String publicKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            BadPaddingException,
            IllegalBlockSizeException,
            InvalidKeySpecException,
            InvalidKeyException {

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, stringToPublicKey(getPublicKey(publicKey)));

        byte[] data = value.getBytes();
        int chunkSize = 245;
        int encryptSize = (int) (Math.ceil(data.length / 245.0) * 256);
        int idx = 0;

        ByteBuffer buffer = ByteBuffer.allocate(encryptSize);
        while (idx < data.length) {
            int len = Math.min(data.length - idx, chunkSize);
            byte[] encryptChunk = cipher.doFinal(data, idx, len);

            buffer.put(encryptChunk);
            idx += len;
        }

        byte[] encryptData = buffer.array();

        return Base64.encodeToString(encryptData, Base64.DEFAULT);
    }

    public static String decrypt(String value, String privateKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            BadPaddingException,
            IllegalBlockSizeException,
            InvalidKeySpecException,
            InvalidKeyException {

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        cipher.init(Cipher.DECRYPT_MODE, stringToPrivateKey(getPrivateKey(privateKey)));

        byte[] data = Base64.decode(value, 1);
        int chunkSize = 256;
        int idx = 0;

        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        while (idx < data.length) {
            int len = Math.min(data.length - idx, chunkSize);
            byte[] decryptchunk = cipher.doFinal(data, idx, len);

            buffer.put(decryptchunk);
            idx += len;
        }

        byte[] decryptData = buffer.array();

        return new String(decryptData).replaceAll("\u0000.*", "");
    }

    /*
    ---------- Convert ----------
    */

    private static PublicKey stringToPublicKey(String publicKey)
            throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        byte[] bytes = Base64.decode(publicKey, Base64.DEFAULT);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePublic(spec);
    }

    private static PrivateKey stringToPrivateKey(String privateKey)
            throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        byte[] bytes = Base64.decode(privateKey, Base64.DEFAULT);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePrivate(spec);
    }

    /*
    ---------- Getter ----------
    */

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static String getPublicKey(String publicKey) {
        String prefix = "-----BEGIN PUBLIC KEY-----";
        String suffix = "-----END PUBLIC KEY-----";

        publicKey.trim();

        if (publicKey.startsWith(prefix) && publicKey.endsWith(suffix))
            return publicKey.substring(prefix.length(), publicKey.length() - suffix.length());
        else
            return publicKey;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static String getPrivateKey(String privateKey) {
        String prefix = "-----BEGIN RSA PRIVATE KEY-----";
        String suffix = "-----END RSA PRIVATE KEY-----";

        privateKey.trim();

        if (privateKey.startsWith(prefix) && privateKey.endsWith(suffix))
            return privateKey.substring(prefix.length(), privateKey.length() - suffix.length());
        else
            return privateKey;
    }

}