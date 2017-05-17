//copy right
package code.warehouse.common.utils;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * package code.warehouse.common.utils
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 14:01
 **/
public class CipherUtils {
    private static final String PBE_WITH_MD5_AND_DES = "PBEWithMD5AndDES";
    private static byte[] salt = new byte[]{ -87, -101, -56, 50, 86, 53, -29, 3 };
    private static final String passphrase = "BQA<vom_PMfcixSh123XEa#NLIw@Osj^U";

    private CipherUtils() {
    }

    /**
     * @deprecated
     */
    public static String md5(String plain) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(plain.getBytes("UTF-8"));
        } catch (Exception var8) {
            return null;
        }

        byte[] byteArray = messageDigest.digest();
        StringBuilder buff = new StringBuilder();
        byte[] var7 = byteArray;
        int var6 = byteArray.length;

        for (int var5 = 0; var5 < var6; ++var5) {
            byte aByteArray = var7[var5];
            if (Integer.toHexString(255 & aByteArray).length() == 1) {
                buff.append("0").append(Integer.toHexString(255 & aByteArray));
            } else {
                buff.append(Integer.toHexString(255 & aByteArray));
            }
        }

        return buff.toString();
    }

    public static final String MD5(String plain) {
        return SHA(plain, "MD5");
    }

    public static final String SHA256(String plain) {
        return SHA(plain, "SHA-256");
    }

    public static final String SHA512(String plain) {
        return SHA(plain, "SHA-512");
    }

    private static String SHA(String plain, String type) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.reset();
            messageDigest.update(plain.getBytes("UTF-8"));
        } catch (Exception var9) {
            return null;
        }

        byte[] byteArray = messageDigest.digest();
        StringBuilder buff = new StringBuilder();
        byte[] var8 = byteArray;
        int var7 = byteArray.length;

        for (int var6 = 0; var6 < var7; ++var6) {
            byte aByteArray = var8[var6];
            if (Integer.toHexString(255 & aByteArray).length() == 1) {
                buff.append("0").append(Integer.toHexString(255 & aByteArray));
            } else {
                buff.append(Integer.toHexString(255 & aByteArray));
            }
        }

        return buff.toString();
    }

    public static String encrypt(String plain) {
        return encrypt(plain, "BQA<vom_PMfcixSh123XEa#NLIw@Osj^U");
    }

    public static String encrypt(String plain, String passphrase) {
        try {
            PBEKeySpec e = new PBEKeySpec(passphrase.toCharArray(), salt, 19);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(e);
            Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
            PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 19);
            ecipher.init(1, key, paramSpec);
            byte[] utf8 = plain.getBytes("UTF-8");
            byte[] enc = ecipher.doFinal(utf8);
            return new String(Base64.encode(enc));
        } catch (Exception var8) {
            return null;
        }
    }

    public static String decrypt(String secret) {
        return decrypt(secret, "BQA<vom_PMfcixSh123XEa#NLIw@Osj^U");
    }

    public static String decrypt(String secret, String passphrase) {
        try {
            PBEKeySpec e = new PBEKeySpec(passphrase.toCharArray(), salt, 19);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(e);
            byte[] dec = Base64.decode(secret.getBytes());
            Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
            PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 19);
            dcipher.init(2, key, paramSpec);
            byte[] utf8 = dcipher.doFinal(dec);
            return new String(utf8, "UTF-8");
        } catch (Exception var8) {
            return null;
        }
    }


    /*public static void main(String[] args) {
        System.out.println(CipherUtils.SHA256("123456"));
    }*/
}
