package encrypt;

import java.security.MessageDigest;

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
        try {
            StringBuilder stringBuilder = new StringBuilder(32);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            byte[] digest = messageDigest.digest(str.getBytes());

            for (byte b : digest) {
                String s = Integer.toHexString(b & 0xff);//改变的地方在这里
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * MD5编码-加盐算法
     */
    public static String encode(String str, String salt) {
        if (str == null) {
            str = "";
        } else {
            // 加盐
            str = str + salt;
            System.out.println(str);
        }

        byte[] bytes = str.getBytes();

        try {
            StringBuilder stringBuilder = new StringBuilder(32);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            byte[] digest = messageDigest.digest(bytes);

            for (byte b : digest) {
                String s = Integer.toHexString(b & 0xff);//改变的地方在这里
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }

}
