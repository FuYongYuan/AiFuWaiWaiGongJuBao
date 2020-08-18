package excel.util;

import dispose.DateDispose;
import dispose.TextDispose;
import enumerate.CommonlyUsedType;
import enumerate.DateType;
import excel.annotation.ExcelField;
import excel.exception.ExcelOperateException;
import excel.operation.set.SheetSet;
import excel.operation.set.ValueLimit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Excel属性处理帮助工具类
 *
 * @author fyy
 */
public class ExcelDisposeUtil {
    /**
     * 对象和Excel有关的属性List获取
     */
    public static void initialization(SheetSet sheetSet) {
        try {
            //获取正确的要赋值的字段
            Field[] fields = sheetSet.getDataClass().getDeclaredFields();
            Field[] excelFields = new Field[fields.length];
            for (Field field : fields) {
                if (field.getAnnotation(ExcelField.class) != null) {
                    excelFields[field.getAnnotation(ExcelField.class).order() - 1] = field;
                }
            }

            if (excelFields.length > 0) {
                for (Field field : excelFields) {
                    if (field != null) {
                        sheetSet.getSheetCache().useField.add(field);

                        ExcelField excelField = field.getAnnotation(ExcelField.class);
                        boolean exist = (sheetSet.getFunction().getSubTotal() != null && sheetSet.getFunction().getSubTotal().getReferenceFieldName() != null) &&
                                (sheetSet.getFunction().getSubTotal().getReferenceFieldName().equals(field.getName()) ||
                                        sheetSet.getFunction().getSubTotal().getReferenceFieldName().equals(excelField.columnName()));
                        if (exist) {
                            sheetSet.getSheetCache().subTotalReferenceField = field;
                        }

                        exist = (sheetSet.getFunction().getTotal() != null && sheetSet.getFunction().getTotal().getReferenceFieldName() != null) &&
                                (sheetSet.getFunction().getTotal().getReferenceFieldName().equals(field.getName()) ||
                                        sheetSet.getFunction().getTotal().getReferenceFieldName().equals(excelField.columnName()));
                        if (exist) {
                            sheetSet.getSheetCache().totalReferenceField = field;
                        }

                        exist = (sheetSet.getFunction().getSubTotal() != null && sheetSet.getFunction().getSubTotal().getCalculationFieldNameAndOrder() != null) &&
                                (sheetSet.getFunction().getSubTotal().getCalculationFieldNameAndOrder().get(field.getName()) != null ||
                                        sheetSet.getFunction().getSubTotal().getCalculationFieldNameAndOrder().get(excelField.columnName()) != null);
                        if (exist) {
                            sheetSet.getSheetCache().subTotalColumnIndex.add(excelField.order());
                        }

                        exist = (sheetSet.getFunction().getTotal() != null && sheetSet.getFunction().getTotal().getCalculationFieldNameAndOrder() != null) &&
                                (sheetSet.getFunction().getTotal().getCalculationFieldNameAndOrder().get(field.getName()) != null ||
                                        sheetSet.getFunction().getTotal().getCalculationFieldNameAndOrder().get(excelField.columnName()) != null);
                        if (exist) {
                            sheetSet.getSheetCache().totalColumnIndex.add(excelField.order());
                        }

                        exist = (sheetSet.getFunction().getTotalAll() != null) &&
                                (sheetSet.getFunction().getTotalAll().getCalculationFieldNameAndOrder().get(field.getName()) != null ||
                                        sheetSet.getFunction().getTotalAll().getCalculationFieldNameAndOrder().get(excelField.columnName()) != null);
                        if (exist) {
                            sheetSet.getSheetCache().totalAllColumnIndex.add(excelField.order());
                        }

                        exist = (sheetSet.getFunction().getSubTotal() != null && sheetSet.getFunction().getSubTotal().getSpanFieldNames() != null) &&
                                (sheetSet.getFunction().getSubTotal().getSpanFieldNames().contains(field.getName()) ||
                                        sheetSet.getFunction().getSubTotal().getSpanFieldNames().contains(excelField.columnName()));
                        if (exist) {
                            sheetSet.getSheetCache().subTotalSpanField.add(field);
                        }

                        exist = (sheetSet.getFunction().getTotal() != null && sheetSet.getFunction().getTotal().getSpanFieldNames() != null) &&
                                (sheetSet.getFunction().getTotal().getSpanFieldNames().contains(field.getName()) ||
                                        sheetSet.getFunction().getTotal().getSpanFieldNames().contains(excelField.columnName()));
                        if (exist) {
                            sheetSet.getSheetCache().totalSpanField.add(field);
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
                value = DateDispose.formattingDate((Date) objectValue, dateType);
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
     * 对象和Excel有关的属性List获取
     *
     * @param tClass 类型
     * @return 对象和Excel有关的属性List
     */
    public static <T> List<Field> getFieldList(Class<T> tClass) {
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
            if (excelFields.length > 0) {
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

    /**
     * 值集转换
     *
     * @param sheetSet   页签对象
     * @param cellValue  原值
     * @param excelField 字段注解
     * @return 值集转换后
     */
    public static String getValueLimit(SheetSet sheetSet, String cellValue, ExcelField excelField) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //获取转换值集中对应值
        if (!excelField.valueLimit().isEmpty()) {
            if (excelField.valueLimit().contains(ExcelDisposeConstant.EQUAL)) {
                String[] valueLimits = excelField.valueLimit().split(";");
                for (String valueLimit : valueLimits) {
                    String[] vl = valueLimit.split("=");
                    if (vl.length == 2) {
                        if (vl[0].equals(cellValue)) {
                            cellValue = vl[1];
                        }
                    }
                }
            } else {
                ValueLimit vl = sheetSet.getValueLimit(excelField.valueLimit());
                if (vl != null) {
                    if (vl.getIsMap()) {
                        for (Object m : vl.getValueList()) {
                            Map map = (Map) m;
                            if (map.get(vl.getContrastFieldName()).toString().equals(cellValue)) {
                                cellValue = map.get(vl.getReplaceFieldName()).toString();
                            }
                        }
                    } else {
                        for (Object o : vl.getValueList()) {
                            Field contrastField = o.getClass().getDeclaredField(vl.getContrastFieldName());
                            Object contrastValue;
                            if (vl.getIsGetMethodFieldValue()) {
                                String fieldName = contrastField.getName();
                                String fieldNameFirstUpperCase = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
                                Method method = o.getClass().getMethod("get" + fieldNameFirstUpperCase);
                                contrastValue = method.invoke(o);
                            } else {
                                contrastValue = contrastField.get(o);
                            }
                            if (contrastValue.toString().equals(cellValue)) {
                                Field replaceField = o.getClass().getDeclaredField(vl.getReplaceFieldName());
                                Object replaceValue;
                                if (vl.getIsGetMethodFieldValue()) {
                                    String fieldName = replaceField.getName();
                                    String fieldNameFirstUpperCase = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
                                    Method method = o.getClass().getMethod("get" + fieldNameFirstUpperCase);
                                    replaceValue = method.invoke(o);
                                } else {
                                    replaceValue = replaceField.get(o);
                                }
                                cellValue = replaceValue.toString();
                            }
                        }
                    }
                }
            }
        }
        return cellValue;
    }
}
