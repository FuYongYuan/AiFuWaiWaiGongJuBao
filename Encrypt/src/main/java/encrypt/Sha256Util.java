package encrypt;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * SHA256加密
 *
 * @author fyy
 */
public class Sha256Util {
    /**
     * SHA256编码
     */
    public static String encode(String str) {
        if (str == null) {
            str = "";
        }
        MessageDigest messageDigest;
        String encode = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));
            encode = String.valueOf(Hex.encodeHex(hash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encode;
    }
}
