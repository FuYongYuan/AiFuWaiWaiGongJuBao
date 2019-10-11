package excel.operation;

import dispose.DateDispose;
import dispose.TextDispose;
import enumerate.CommonlyUsedType;
import enumerate.DateType;
import excel.annotation.ExcelField;
import excel.exception.ExcelOperateException;
import excel.util.ExcelDisposeUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Excel导入
 */
public class ExcelImport {
    /**
     * 读取每页数据
     *
     * @param sheet  页
     * @param tClass 转换的对象
     */
    public static <T> List<T> getExcel(Sheet sheet, Class<T> tClass) {
        return getExcel(sheet, tClass, true);
    }

    /**
     * 读取每页数据
     *
     * @param sheet                 页
     * @param tClass                转换的对象
     * @param isGetMethodFieldValue 是否使用Set来写入值
     */
    public static <T> List<T> getExcel(Sheet sheet, Class<T> tClass, Boolean isGetMethodFieldValue) {
        List<T> list = new ArrayList<>();
        try {
            //当前页一共多少行
            int rows = sheet.getLastRowNum();
            //当前页一共多少列
            int columns = sheet.getRow(0).getLastCellNum();
            //公共行对象
            Row row;
            //公共列对象
            Cell cell = null;
            //公共标题列对象
            Cell cellf = null;
            //获取正确的字段顺序
            List<Field> fieldList = ExcelDisposeUtil.getFieldList(tClass);
            //循环之后的行数据
            for (int j = 1; j <= rows; j++) {
                // 得到第j行
                row = sheet.getRow(j);
                //创建类型对象
                T obj = tClass.newInstance();
                if (row != null) {
                    //循环列
                    for (int i = 0; i < columns; i++) {
                        try {
                            //拿到标题列的值
                            cellf = sheet.getRow(0).getCell(i);
                            // 获取cell，如果报异常，说明整个row是空的null，直接在catch里面捕获，并赋值为空
                            cell = row.getCell(i);
                        } catch (NullPointerException ignored) {
                        } finally {
                            //如果没有读到数据行继续循环
                            if (cell != null && cellf != null) {
                                // 获取cell的类型
                                CellType type = cell.getCellType();
                                // 获取cell的类型
                                CellType typef = cellf.getCellType();
                                //判断是否为空如果为空则跳过.无论是标题列还是数据列
                                if (type != CellType.BLANK && typef != CellType.BLANK) {
                                    //循环标题
                                    for (Field field : fieldList) {
                                        //判断标题列是否和字段一样
                                        if (field.getAnnotation(ExcelField.class).columnName().equals(cellf.getRichStringCellValue().getString())) {
                                            setObj(cell, type, field, obj, isGetMethodFieldValue);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //保存对象
                list.add(obj);
            }
        } catch (Exception e) {
            throw new ExcelOperateException("诊断：Excel导入失败！", e);
        }
        return list;
    }

    private static void setObj(Cell cell, CellType type, Field field, Object obj, boolean isGetMethodFieldValue) {
        try {
            //结果对象
            Object value = null;
            if (type == CellType.NUMERIC) {
                value = cell.getNumericCellValue();
            } else if (type == CellType.STRING) {
                value = cell.getRichStringCellValue().getString();
            } else if (type == CellType.BOOLEAN) {
                value = cell.getBooleanCellValue();
            }
            if (value != null && !"".equals(value.toString())) {
                if (isGetMethodFieldValue) {
                    String fieldName = field.getName();
                    String fieldNameFirstUpperCase = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
                    Method method;
                    if (field.getType().getName().equals(CommonlyUsedType.Type_String.getValue())) {
                        method = obj.getClass().getMethod("set" + fieldNameFirstUpperCase, String.class);
                        String s;
                        if (TextDispose.isNumber(value.toString())) {
                            if (value.toString().indexOf(".") > 0 && value.toString().indexOf("E") > 0) {
                                DecimalFormat df = new DecimalFormat("#");
                                s = df.format(value);
                                method.invoke(obj, s);
                            } else if (value.toString().indexOf(".") > 0) {
                                s = value.toString().substring(value.toString().indexOf(".") + 1);
                                int ints = Integer.parseInt(s);
                                if (ints == 0) {
                                    DecimalFormat df = new DecimalFormat("#");
                                    s = df.format(Double.parseDouble(value.toString()));
                                    method.invoke(obj, s);
                                } else {
                                    method.invoke(obj, value.toString());
                                }
                            } else {
                                method.invoke(obj, value.toString());
                            }
                        } else {
                            method.invoke(obj, value);
                        }
                    } else if (field.getType().getName().equals(CommonlyUsedType.Type_Util_Date.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Sql_Date.getValue())) {
                        method = obj.getClass().getMethod("set" + fieldNameFirstUpperCase, Date.class);
                        if (TextDispose.isNumber(value.toString())) {
                            int d = (int) Double.parseDouble(value.toString()) - 2;
                            value = DateDispose.day_calculate_String("1900-1-1", d, DateType.Year_Month_Day);
                        }
                        value = DateDispose.formatting_Date(value.toString(), field.getAnnotation(ExcelField.class).dateType());
                        method.invoke(obj, value);
                    } else if (field.getType().getName().equals(CommonlyUsedType.Type_Integer.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_int.getValue())) {
                        method = obj.getClass().getMethod("set" + fieldNameFirstUpperCase, Integer.class);
                        if (value.toString().indexOf(".") > 0) {
                            Double d = Double.parseDouble(value.toString());
                            method.invoke(obj, d.intValue());
                        } else {
                            method.invoke(obj, Integer.parseInt(value.toString()));
                        }
                    } else if (field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_double.getValue())) {
                        method = obj.getClass().getMethod("set" + fieldNameFirstUpperCase, Double.class);
                        method.invoke(obj, Double.parseDouble(value.toString()));
                    } else if (field.getType().getName().equals(CommonlyUsedType.Type_Boolean.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_boolean.getValue())) {
                        method = obj.getClass().getMethod("set" + fieldNameFirstUpperCase, Boolean.class);
                        method.invoke(obj, Boolean.parseBoolean(value.toString()));
                    } else if (field.getType().getName().equals(CommonlyUsedType.Type_BigDecimal.getValue())) {
                        method = obj.getClass().getMethod("set" + fieldNameFirstUpperCase, BigDecimal.class);
                        method.invoke(obj, new BigDecimal(value.toString()));
                    } else {
                        method = obj.getClass().getMethod("set" + fieldNameFirstUpperCase, Object.class);
                        method.invoke(obj, value);
                    }
                } else {
                    //判断哪个类型.读取set方法拿到结果
                    if (field.getType().getName().equals(CommonlyUsedType.Type_Util_Date.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Sql_Date.getValue())) {
                        if (TextDispose.isNumber(value.toString())) {
                            int d = (int) Double.parseDouble(value.toString()) - 2;
                            value = DateDispose.day_calculate_String("1900-1-1", d, DateType.Year_Month_Day);
                        }
                        value = DateDispose.formatting_Date(value.toString(), field.getAnnotation(ExcelField.class).dateType());
                        field.set(obj, value);
                    } else if (field.getType().getName().equals(CommonlyUsedType.Type_int.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Integer.getValue())) {
                        if (value.toString().indexOf(".") > 0) {
                            Double d = Double.parseDouble(value.toString());
                            field.set(obj, d.intValue());
                        } else {
                            field.set(obj, Integer.parseInt(value.toString()));
                        }
                    } else if (field.getType().getName().equals(CommonlyUsedType.Type_double.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue())) {
                        field.set(obj, Double.parseDouble(value.toString()));
                    } else if (field.getType().getName().equals(CommonlyUsedType.Type_boolean.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Boolean.getValue())) {
                        field.set(obj, Boolean.parseBoolean(value.toString()));
                    } else if (field.getType().getName().equals(CommonlyUsedType.Type_String.getValue())) {
                        String s;
                        if (TextDispose.isNumber(value.toString())) {
                            if (value.toString().indexOf(".") > 0 && value.toString().indexOf("E") > 0) {
                                DecimalFormat df = new DecimalFormat("#");
                                s = df.format(value);
                                field.set(obj, s);
                            } else if (value.toString().indexOf(".") > 0) {
                                s = value.toString().substring(value.toString().indexOf(".") + 1);
                                int ints = Integer.parseInt(s);
                                if (ints == 0) {
                                    DecimalFormat df = new DecimalFormat("#");
                                    s = df.format(Double.parseDouble(value.toString()));
                                    field.set(obj, s);
                                } else {
                                    field.set(obj, value.toString());
                                }
                            } else {
                                field.set(obj, value.toString());
                            }
                        } else {
                            field.set(obj, value);
                        }
                    } else if (field.getType().getName().equals(CommonlyUsedType.Type_BigDecimal.getValue())) {
                        field.set(obj, new BigDecimal(value.toString()));
                    } else {
                        field.set(obj, value);
                    }
                }
            }
        } catch (Exception e) {
            throw new ExcelOperateException("诊断：Excel导入赋值过程错误！", e);
        }
    }
}