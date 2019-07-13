package encrypt;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * SHA1算法
 */
public final class SHA1Util {

    /**
     * SHA1编码
     */
    public static String encode(String str) {
        if (str == null) {
            str = "";
        }
        return DigestUtils.sha1Hex(str);
    }

}  