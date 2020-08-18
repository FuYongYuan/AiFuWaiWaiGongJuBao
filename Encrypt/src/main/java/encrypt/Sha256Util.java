package encrypt;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * SHA256加密
 *
 * @author fyy
 */
public class Sha256Util {
    /**
     * SHA256编码
     */
    public static String encodeSha256(String str) {
        if (str == null) {
            str = "";
        }
        return DigestUtils.sha256Hex(str);
    }
}
