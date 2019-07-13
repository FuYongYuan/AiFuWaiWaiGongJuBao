package encrypt;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    /**
     * MD5编码
     */
    public static String encode(String str) {
        if (str == null) {
            str = "";
        }
        return DigestUtils.md5Hex(str);
    }
}
