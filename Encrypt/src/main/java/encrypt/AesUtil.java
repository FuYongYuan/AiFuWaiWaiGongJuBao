package encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * AES加密
 *
 * @author fyy
 */
public class AesUtil {

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return 加密字符串
     */
    public static byte[] encrypt(String content, String password) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            return cipher.doFinal(byteContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return 解密字符串
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 加密
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf 二进制
     * @return 16进制
     */
    public static String parseByteToHexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    /**
     * 将16进制转换为二进制
     *
     * @param hexStr 16进制
     * @return 二进制
     */
    public static byte[] parseHexStrToByte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        IntStream.range(0, hexStr.length() / 2).forEach(i -> {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        });
        return result;
    }

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     */
    public static String encryptString(String content, String password) {
        return parseByteToHexStr(Objects.requireNonNull(encrypt(content, password)));
    }

    /**
     * 解密
     *
     * @param encrypt  待解密内容
     * @param password 解密密钥
     */
    public static String decryptString(String encrypt, String password) {
        byte[] decryptFrom = parseHexStrToByte(encrypt);
        return new String(Objects.requireNonNull(decrypt(decryptFrom, password)));
    }
}
