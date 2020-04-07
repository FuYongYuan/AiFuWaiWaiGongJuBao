package encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {
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
            byte[] keyBytes = Base64.decodeBase64(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey key = keyFactory.generatePublic(keySpec);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(context.getBytes());
            return Base64.encodeBase64String(bytes);
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
            byte[] content = context.getBytes();
            Cipher cipher = Cipher.getInstance("RSA");
            byte[] keyBytes = Base64.decodeBase64(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] responseByte = cipher.doFinal(Base64.decodeBase64(content));
            return new String(responseByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}