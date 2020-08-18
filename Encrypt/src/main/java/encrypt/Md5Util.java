package encrypt;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5加密
 *
 * @author fyy
 */
public class Md5Util {
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
