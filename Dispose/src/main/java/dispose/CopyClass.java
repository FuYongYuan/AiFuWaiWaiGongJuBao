package dispose;


import enumerate.CommonlyUsedType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 将一个Class对象的各个属性值，复制到另外一个Class对象
 */
public class CopyClass {
    /**
     * 将一个Class对象的各个属性值通过setter和getter方法，复制到另外一个Class对象
     *
     * @param object1 被取值的对象
     * @param object2 要赋值的对象
     * @param clazz   取值对象类型
     * @param getNull 是否取空值
     */
    public static <T> void copyClassGetSet(Object object1, Object object2, Class<T> clazz, boolean getNull) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldNameFirstUpperCase = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
            Method method = object1.getClass().getMethod("get" + fieldNameFirstUpperCase);
            Object value = method.invoke(object1);
            if ((value != null && !"".equals(value)) || getNull) {
                method = getMethod(clazz, field, fieldNameFirstUpperCase);
                method.invoke(object2, value);
            }
        }
    }

    /**
     * 将一个Class对象的各个属性值不通过setter和getter方法，复制到另外一个Class对象
     *
     * @param object1 被取值的对象
     * @param object2 要赋值的对象
     * @param clazz   取值对象类型
     * @param getNull 是否取空值
     */
    public static <T> void copyClass(Object object1, Object object2, Class<T> clazz, boolean getNull) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object value = field.get(object1);
            if ((value != null && !"".equals(value)) || getNull) {
                if (field.getType().getName().equals(CommonlyUsedType.Type_String.getValue())) {
                    if (value != null) {
                        field.set(object2, value.toString());
                    }
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Util_Date.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Sql_Date.getValue())) {
                    if (value instanceof Date) {
                        field.set(object2, value);
                    }
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Integer.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_int.getValue())) {
                    Integer v = null;
                    if (value != null) {
                        v = Integer.parseInt(value.toString());
                    }
                    field.set(object2, v);
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_double.getValue())) {
                    Double d = null;
                    if (value != null) {
                        d = Double.parseDouble(value.toString());
                    }
                    field.set(object2, d);
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Boolean.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_boolean.getValue())) {
                    field.set(object2, value);
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Timestamp.getValue())) {
                    if (value instanceof Timestamp) {
                        field.set(object2, value);
                    }
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_BigDecimal.getValue())) {
                    if (value instanceof BigDecimal) {
                        field.set(object2, value);
                    }
                } else {
                    field.set(object2, value);
                }
            }
        }
    }


    /**
     * 将MAP中的值.复制至另一个Class对象 为NULL不复制
     */
    public static <T> T copyMap(Map<String, Object> map, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T obj = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object value = map.get(field.getName());
            if ((value != null && !"".equals(value))) {
                if (field.getType().getName().equals(CommonlyUsedType.Type_String.getValue())) {
                    field.set(obj, value.toString());
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Util_Date.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Sql_Date.getValue())) {
                    field.set(obj, value);
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Integer.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_int.getValue())) {
                    Integer v = Integer.parseInt(value.toString());
                    field.set(obj, v);
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_double.getValue())) {
                    Double d = Double.parseDouble(value.toString());
                    field.set(obj, d);
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Boolean.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_boolean.getValue())) {
                    field.set(obj, value);
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Timestamp.getValue())) {
                    field.set(obj, value);
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_BigDecimal.getValue())) {
                    field.set(obj, new BigDecimal(value.toString()));
                } else {
                    field.set(obj, value);
                }
            }
        }
        return obj;
    }

    /**
     * 将MAP中的值.复制至另一个Class对象 为NULL不复制
     */
    public static <T> List<T> copyMap(List<Map<String, Object>> mapList, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        List<T> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            list.add(copyMap(map, clazz));
        }
        return list;
    }

    /**
     * 将MAP中的值.复制至另一个Class对象 为NULL不复制
     */
    public static <T> T copyMapGetSet(Map<String, Object> map, Class<T> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        T obj = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldNameFirstUpperCase = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
            Method method;
            Object value1 = map.get(fieldName);
            Object value2 = map.get(fieldNameFirstUpperCase);
            if ((value1 != null && !"".equals(value1)) || (value2 != null && !"".equals(value2))) {
                Object value = value1 != null ? value1 : value2;
                if (!"".equals(value)) {
                    method = getMethod(clazz, field, fieldNameFirstUpperCase);
                    if (field.getType().getName().equals(CommonlyUsedType.Type_Integer.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_int.getValue())) {
                        value = Integer.parseInt(value.toString());
                    } else if (field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_double.getValue())) {
                        value = Double.parseDouble(value.toString());
                    }
                    method.invoke(obj, value);
                }
            }
        }
        return obj;
    }

    /**
     * 将MAP中的值.复制至另一个Class对象 为NULL不复制
     */
    public static <T> List<T> copyMapGetSet(List<Map<String, Object>> mapList, Class<T> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        List<T> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            list.add(copyMapGetSet(map, clazz));
        }
        return list;
    }

    private static <T> Method getMethod(Class<T> clazz, Field field, String fieldNameFirstUpperCase) throws NoSuchMethodException {
        Method method;
        if (field.getType().getName().equals(CommonlyUsedType.Type_String.getValue())) {
            method = clazz.getMethod("set" + fieldNameFirstUpperCase, String.class);
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_Util_Date.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Sql_Date.getValue())) {
            method = clazz.getMethod("set" + fieldNameFirstUpperCase, Date.class);
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_Integer.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_int.getValue())) {
            method = clazz.getMethod("set" + fieldNameFirstUpperCase, Integer.class);
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_double.getValue())) {
            method = clazz.getMethod("set" + fieldNameFirstUpperCase, Double.class);
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_Boolean.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_boolean.getValue())) {
            method = clazz.getMethod("set" + fieldNameFirstUpperCase, Boolean.class);
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_BigDecimal.getValue())) {
            method = clazz.getMethod("set" + fieldNameFirstUpperCase, BigDecimal.class);
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_Timestamp.getValue())) {
            method = clazz.getMethod("set" + fieldNameFirstUpperCase, Timestamp.class);
        } else {
            method = clazz.getMethod("set" + fieldNameFirstUpperCase, Object.class);
        }
        return method;
    }
}
