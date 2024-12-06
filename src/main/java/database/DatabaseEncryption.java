package database;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DatabaseEncryption {
    // Define constants for AES-256 encryption
    private static final String AES = "AES";
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String CONSTANT_KEY = "12345678901234567890123456789012"; // Must be 32 chars for AES-256
    private static final String CONSTANT_IV = "1234567890123456"; // Must be 16 chars for AES block size

    // Create SecretKeySpec from the constant key
    private static SecretKeySpec getKey() {
        return new SecretKeySpec(CONSTANT_KEY.getBytes(), AES);
    }

    // Create IvParameterSpec from the constant IV
    private static IvParameterSpec getIv() {
        return new IvParameterSpec(CONSTANT_IV.getBytes());
    }

    // Encrypt a string
    public static String encrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getKey(), getIv());
            byte[] cipherText = cipher.doFinal(input.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // Decrypt a string
    public static String decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getKey(), getIv());
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(plainText, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
