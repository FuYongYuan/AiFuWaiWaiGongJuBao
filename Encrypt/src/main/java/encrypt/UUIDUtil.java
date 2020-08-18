package encrypt;

import enumerate.UUIDType;

import java.util.UUID;

/**
 * UUID密钥
 *
 * @author fyy
 */
public class UUIDUtil {

    /**
     * 获得一个32位UUID
     *
     * @return String 32位UUID
     */
    public static String get32() {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 获得一个36位UUID
     *
     * @return String 36位UUID
     */
    public static String get36() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number       int 需要获得的UUID数量
     * @param length32Or36 int 32 OR 36 其余暂不识别
     * @return String[] UUID数组
     */
    public static String[] gets(int number, UUIDType length32Or36) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            if (UUIDType.UUID32.equals(length32Or36)) {
                ss[i] = get32();
            } else if (UUIDType.UUID36.equals(length32Or36)) {
                ss[i] = get36();
            }
        }
        return ss;
    }
}
