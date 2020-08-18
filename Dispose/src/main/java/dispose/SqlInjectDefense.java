package dispose;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * SQL注入防御
 *
 * @author fyy
 */
public class SqlInjectDefense {

    /**
     * sql注入防御方法
     *
     * @param parameter 参数
     * @return 是否被注入
     */
    public static boolean init(Object... parameter) {
        if (parameter != null && parameter.length > 0) {
            for (int i = 0; i < parameter.length; i++) {
                String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
                        + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|drop|execute)\\b)";
                Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
                try {
                    if (parameter[i] != null && !"".equals(parameter[i])) {
                        if (sqlPattern.matcher(parameter[i].toString()).find()) {
                            System.out.println("未能通过过滤器：第" + i + "个 : " + parameter[i]);
                            System.out.println("全部参数为 : " + Arrays.toString(parameter));
                            return true;
                        }
                    }
                } catch (Exception e) {
                    return true;
                }
            }
        }
        return false;
    }

}
