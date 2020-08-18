package dispose;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于比较两个类是否完全相等
 *
 * @author fyy
 */
public class CompareClass {
    /**
     * 格式化时间
     */
    public static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 比对两个对象的值
     *
     * @param obj  对象的类型
     * @param obj1 要比对的第一个对象
     * @param obj2 要比对的第二个对象
     * @return true两个对象完全相等   false两个对象有不等的地方
     */
    public static <T> boolean compareBackBoolean(Class<T> obj, T obj1, T obj2) {
        try {
            if (obj1 != null && obj2 != null) {
                // 获得属性
                Field[] fields = obj.getDeclaredFields();
                for (Field field : fields) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj);
                    // 获得get方法
                    Method getMethod = pd.getReadMethod();
                    // 执行get方法返回一个Object
                    Object o1 = getMethod.invoke(obj1);
                    // 执行get方法返回一个Object
                    Object o2 = getMethod.invoke(obj2);
                    if (o1 != null && o2 != null) {
                        if (o1 instanceof String && o2 instanceof String) {
                            String oV1 = o1.toString();
                            String oV2 = o2.toString();
                            if (!oV1.equals(oV2)) {
                                return false;
                            }
                        }
                        if (o1 instanceof Date && o2 instanceof Date) {
                            String oV1 = sdf.format(o1);
                            String oV2 = sdf.format(o2);
                            if (!oV1.equals(oV2)) {
                                return false;
                            }
                        }
                        if (o1 instanceof Integer && o2 instanceof Integer) {
                            Integer oV1 = Integer.parseInt(o1.toString());
                            Integer oV2 = Integer.parseInt(o2.toString());
                            if (!oV1.equals(oV2)) {
                                return false;
                            }
                        }
                        if (o1 instanceof Double && o2 instanceof Double) {
                            Double oV1 = Double.parseDouble(o1.toString());
                            Double oV2 = Double.parseDouble(o2.toString());
                            if (!oV1.equals(oV2)) {
                                return false;
                            }
                        }
                    } else {
                        if (o1 != o2) {
                            return false;
                        }
                    }
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 比对两个对象的值
     *
     * @param obj  对象的类型
     * @param obj1 要比对的第一个对象
     * @param obj2 要比对的第二个对象
     * @return Map  key:不一样的属性名     value:obj2值
     */
    public static <T> Map<String, Object> compareBackMap(Class<T> obj, T obj1, T obj2) {
        Map<String, Object> map = new HashMap<>(16);
        // 获得属性
        Field[] fields = obj.getDeclaredFields();
        try {
            if (obj1 != null && obj2 != null) {
                for (Field field : fields) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj);
                    // 获得get方法
                    Method getMethod = pd.getReadMethod();
                    // 执行get方法返回一个Object
                    Object o1 = getMethod.invoke(obj1);
                    // 执行get方法返回一个Object
                    Object o2 = getMethod.invoke(obj2);
                    if (o1 != null && o2 != null) {
                        if (o1 instanceof String && o2 instanceof String) {
                            String oV1 = o1.toString();
                            String oV2 = o2.toString();
                            if (!oV1.equals(oV2)) {
                                map.put(field.getName(), o2);
                            }
                        }
                        if (o1 instanceof Date && o2 instanceof Date) {
                            String oV1 = sdf.format(o1);
                            String oV2 = sdf.format(o2);
                            if (!oV1.equals(oV2)) {
                                map.put(field.getName(), o2);
                            }
                        }
                        if (o1 instanceof Integer && o2 instanceof Integer) {
                            Integer oV1 = Integer.parseInt(o1.toString());
                            Integer oV2 = Integer.parseInt(o2.toString());
                            if (!oV1.equals(oV2)) {
                                map.put(field.getName(), o2);
                            }
                        }
                        if (o1 instanceof Double && o2 instanceof Double) {
                            Double oV1 = Double.parseDouble(o1.toString());
                            Double oV2 = Double.parseDouble(o2.toString());
                            if (!oV1.equals(oV2)) {
                                map.put(field.getName(), o2);
                            }
                        }
                    } else {
                        if (o1 != o2) {
                            map.put(field.getName(), o2);
                        }
                    }
                }
            } else {
                for (Field field : fields) {
                    map.put(field.getName(), null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
