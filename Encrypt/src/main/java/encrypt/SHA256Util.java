package encrypt;

import org.apache.commons.codec.digest.DigestUtils;

public class SHA256Util {
    /**
     * SHA256编码
     */
    public static String encodeSHA256(String str) {
        if (str == null) {
            str = "";
        }
        return DigestUtils.sha256Hex(str);
    }
}
