package encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密
 *
 * @author fyy
 */
public class RsaUtil {
    /**
     * 加密
     *
     * @param publicKey 公钥
     * @param context   加密字符串
     * @return 加密内容 null加密失败
     */
    public static String encrypt(String publicKey, String context) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, getRsaPublicKey(publicKey));
            byte[] bytes = cipher.doFinal(context.getBytes());
            return new String(Base64.encodeBase64(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param privateKey 私钥
     * @param context    解密字符串
     * @return 解密内容 null解密失败
     */
    public static String decrypt(String privateKey, String context) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, getRsaPrivateKey(privateKey));
            byte[] content = context.getBytes();
            byte[] responseByte = cipher.doFinal(Base64.decodeBase64(content));
            return new String(responseByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用getRSAPublicKey得到公钥,返回类型为PublicKey
     *
     * @param publicKey base64
     */
    public static RSAPublicKey getRsaPublicKey(String publicKey) {
        try {
            byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用getRsaPrivateKey得到公钥,返回类型为PrivateKey
     *
     * @param privateKey base64
     */
    public static RSAPrivateKey getRsaPrivateKey(String privateKey) {
        try {
            byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}