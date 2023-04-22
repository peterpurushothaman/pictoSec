package com.example.pictosec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
public class passwordHash {

        //creates the hash text to be stored
        public static String createHash(String text, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
            KeySpec spec = new PBEKeySpec(text.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            String str = new String(hash);
            return str;
        }

}
