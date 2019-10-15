package excel.util;

import dispose.DateDispose;
import dispose.TextDispose;
import enumerate.CommonlyUsedType;
import enumerate.DateType;
import excel.annotation.ExcelField;
import excel.exception.ExcelOperateException;
import excel.operation.set.Function;
import excel.operation.set.SheetData;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Excel属性处理帮助工具类
 */
public class ExcelDisposeUtil {
    /**
     * 对象和Excel有关的属性List获取
     *
     * @param tClass 类型
     */
    public static void initialization(Function function, SheetData sheetData, Class tClass) {
        try {
            //获取正确的要赋值的字段
            Field[] fields = tClass.getDeclaredFields();
            Field[] excelFields = new Field[fields.length];
            for (Field field : fields) {
                if (field.getAnnotation(ExcelField.class) != null) {
                    excelFields[field.getAnnotation(ExcelField.class).order() - 1] = field;
                }
            }

            if (excelFields.length > 0) {
                for (Field field : excelFields) {
                    if (field != null) {
                        sheetData.useField.add(field);

                        if ((function.getSubTotal() != null && function.getSubTotal().getReferenceFieldName() != null) &&
                                (function.getSubTotal().getReferenceFieldName().equals(field.getName()) ||
                                        function.getSubTotal().getReferenceFieldName().equals(field.getAnnotation(ExcelField.class).columnName()))) {
                            sheetData.subTotalReferenceField = field;
                        }

                        if ((function.getTotal() != null && function.getTotal().getReferenceFieldName() != null) &&
                                (function.getTotal().getReferenceFieldName().equals(field.getName()) ||
                                        function.getTotal().getReferenceFieldName().equals(field.getAnnotation(ExcelField.class).columnName()))) {
                            sheetData.totalReferenceField = field;
                        }

                        if ((function.getSubTotal() != null && function.getSubTotal().getCalculationFieldNameAndOrder() != null) &&
                                (function.getSubTotal().getCalculationFieldNameAndOrder().get(field.getName()) != null ||
                                        function.getSubTotal().getCalculationFieldNameAndOrder().get(field.getAnnotation(ExcelField.class).columnName()) != null)) {
                            sheetData.subTotalColumnIndex.add(field.getAnnotation(ExcelField.class).order());
                        }

                        if ((function.getTotal() != null && function.getTotal().getCalculationFieldNameAndOrder() != null) &&
                                (function.getTotal().getCalculationFieldNameAndOrder().get(field.getName()) != null ||
                                        function.getTotal().getCalculationFieldNameAndOrder().get(field.getAnnotation(ExcelField.class).columnName()) != null)) {
                            sheetData.totalColumnIndex.add(field.getAnnotation(ExcelField.class).order());
                        }

                        if ((function.getTotalAll() != null) &&
                                (function.getTotalAll().getCalculationFieldNameAndOrder().get(field.getName()) != null ||
                                        function.getTotalAll().getCalculationFieldNameAndOrder().get(field.getAnnotation(ExcelField.class).columnName()) != null)) {
                            sheetData.totalAllColumnIndex.add(field.getAnnotation(ExcelField.class).order());
                        }

                        if ((function.getSubTotal() != null && function.getSubTotal().getSpanFieldNames() != null) &&
                                (function.getSubTotal().getSpanFieldNames().contains(field.getName()) ||
                                        function.getSubTotal().getSpanFieldNames().contains(field.getAnnotation(ExcelField.class).columnName()))) {
                            sheetData.subTotalSpanField.add(field);
                        }

                        if ((function.getTotal() != null && function.getTotal().getSpanFieldNames() != null) &&
                                (function.getTotal().getSpanFieldNames().contains(field.getName()) ||
                                        function.getTotal().getSpanFieldNames().contains(field.getAnnotation(ExcelField.class).columnName()))) {
                            sheetData.totalSpanField.add(field);
                        }
                    }
                }
            } else {
                throw new ExcelOperateException("诊断：Excel导出未找到配置字段！", new NullPointerException());
            }
        } catch (Exception e) {
            throw new ExcelOperateException("诊断：Excel对应关系错误！", e);
        }
    }

    /**
     * 匹配对象中属性的值
     *
     * @param field             字段
     * @param obj               对象
     * @param dateType          日期格式
     * @param decimalAfterDigit 保留小数
     * @return 处理后的值
     */
    public static String correspondingValue(Field field, Object obj, boolean isGetMethodFieldValue, DateType dateType, int decimalAfterDigit) {
        try {
            //定义一个取对象中get方法的对象
            Object objectValue;
            if (isGetMethodFieldValue) {
                String fieldName = field.getName();
                String fieldNameFirstUpperCase = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
                Method method = obj.getClass().getMethod("get" + fieldNameFirstUpperCase);
                objectValue = method.invoke(obj);
            } else {
                objectValue = field.get(obj);
            }
            String value;
            if (objectValue == null) {
                value = null;
            } else if (objectValue instanceof Date && !field.getType().getName().equals(CommonlyUsedType.Type_String.getValue())) {
                value = DateDispose.formatting_Date((Date) objectValue, dateType);
            } else if (TextDispose.isDouble(objectValue.toString())) {
                StringBuilder sb = new StringBuilder("#0.");
                if (decimalAfterDigit > 0) {
                    for (int i = 0; i < decimalAfterDigit; i++) {
                        sb.append("0");
                    }
                } else {
                    sb.append("000");
                }
                DecimalFormat df = new DecimalFormat(sb.toString());
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(objectValue));
                value = df.format(bigDecimal);
            } else {
                value = objectValue.toString();
            }
            return value;
        } catch (Exception e) {
            throw new ExcelOperateException("诊断：Excel中数据格式转换错误！", e);
        }
    }

    /**
     * 匹配对象中属性的值
     *
     * @param type              字段
     * @param obj               对象
     * @param dateType          日期格式
     * @param decimalAfterDigit 保留小数
     * @return 处理后的值
     */
    public static String correspondingValue(Object obj, Type type, DateType dateType, int decimalAfterDigit) {
        try {
            //定义一个取对象中get方法的对象
            String value;
            if (obj == null) {
                value = null;
            } else if (obj instanceof Date && !(type == String.class)) {
                value = DateDispose.formatting_Date((Date) obj, dateType);
            } else if (TextDispose.isDouble(obj.toString())) {
                StringBuilder sb = new StringBuilder("#0.");
                if (decimalAfterDigit > 0) {
                    for (int i = 0; i < decimalAfterDigit; i++) {
                        sb.append("0");
                    }
                } else {
                    sb.append("000");
                }
                DecimalFormat df = new DecimalFormat(sb.toString());
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(obj));
                value = df.format(bigDecimal);
            } else {
                value = obj.toString();
            }
            return value;
        } catch (Exception e) {
            throw new ExcelOperateException("诊断：Excel中数据格式转换错误！", e);
        }
    }

    /**
     * 对象和Excel有关的属性List获取
     *
     * @param tClass 类型
     * @return 对象和Excel有关的属性List
     */
    public static List<Field> getFieldList(Class tClass) {
        List<Field> fieldList = new ArrayList<>();
        try {
            //获取正确的要赋值的字段
            Field[] fields = tClass.getDeclaredFields();
            Field[] excelFields = new Field[fields.length];
            for (Field field : fields) {
                if (field.getAnnotation(ExcelField.class) != null) {
                    excelFields[field.getAnnotation(ExcelField.class).order() - 1] = field;
                }
            }
            if (excelFields != null && excelFields.length > 0) {
                for (Field field : excelFields) {
                    if (field != null) {
                        fieldList.add(field);
                    }
                }
            }
        } catch (Exception e) {
            throw new ExcelOperateException("诊断：Excel对应关系错误！", e);
        }
        return fieldList;
    }
}
