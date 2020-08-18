package dispose;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;


/**
 * 编码转换工具类
 *
 * @author fyy
 */
public class ConvertDispose {
    /**
     * 4次
     */
    private static final int FREQUENCY = 4;

    /**
     * 转换Unicode
     */
    public static String convertUnicode(String ori) {
        char aChar;
        int len = ori.length();
        StringBuilder outBuffer = new StringBuilder(len);
        for (int x = 0; x < len; ) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < FREQUENCY; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }

        }
        return outBuffer.toString();
    }


    /**
     * BASE64编码
     */
    public static String encodeBase64(byte[] str) {
        return new String(Base64.encodeBase64(str));
    }

    /**
     * BASE64解码
     */
    public static byte[] decodeBase64(String str) {
        return Base64.decodeBase64(str);
    }

    /**
     * URL编码
     */
    public static String encodeUrl(String str) {
        try {
            return new URLCodec().encode(str, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * URL解码
     */
    public static String decodeUrl(String str) {
        try {
            return new URLCodec().decode(str, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }


}
