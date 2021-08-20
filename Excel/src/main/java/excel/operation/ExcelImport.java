package excel.operation;

import dispose.DateDispose;
import dispose.TextDispose;
import enumerate.CommonlyUsedType;
import enumerate.DateType;
import excel.annotation.ExcelField;
import excel.exception.ExcelOperateException;
import excel.operation.cache.LoadSpanCache;
import excel.util.ExcelDisposeConstant;
import excel.util.ExcelDisposeUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;


/**
 * Excel导入
 *
 * @author fyy
 */
public class ExcelImport {
    /**
     * 读取每页数据
     *
     * @param sheet 页
     */
    public static List<TreeMap<String, Object>> getExcel(Sheet sheet) {
        List<TreeMap<String, Object>> list = new ArrayList<>();
        try {
            // 当前页一共多少行
            int rows = sheet.getLastRowNum();
            // 公共行对象
            Row row;
            // 公共列对象
            Cell cell = null;
            // 跨行跨列缓存
            List<LoadSpanCache> loadSpanCacheList = new ArrayList<>();
            // 循环之后的行数据
            for (int j = 0; j <= rows; j++) {
                TreeMap<String, Object> map = new TreeMap<>();
                // 得到第j行
                row = sheet.getRow(j);
                if (row != null) {
                    // 当前页一共多少列
                    int columns = row.getLastCellNum();
                    // 循环列
                    for (int i = 0; i < columns; i++) {
                        try {
                            // 获取cell，如果报异常，说明整个row是空的null，直接在catch里面捕获，并赋值为空
                            cell = row.getCell(i);
                        } catch (NullPointerException ignored) {
                        } finally {
                            // 如果没有读到数据行继续循环
                            if (cell != null) {
                                // 获取cell的类型
                                CellType type = cell.getCellType();
                                if (type != CellType.BLANK) {
                                    setObj(sheet, cell, type, map, loadSpanCacheList);
                                }
                            }
                        }
                    }
                }
                // 保存对象
                list.add(map);
            }
            // 处理跨行跨列缓存
            disposeLoadCache(list, loadSpanCacheList);
        } catch (Exception e) {
            throw new ExcelOperateException("诊断：Excel导入失败！", e);
        }
        return list;
    }

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
            // 当前页一共多少行
            int rows = sheet.getLastRowNum();
            // 当前页一共多少列
            int columns = sheet.getRow(0).getLastCellNum();
            // 公共行对象
            Row row;
            // 公共列对象
            Cell cell = null;
            // 公共标题列对象
            Cell cellf = null;
            // 获取正确的字段顺序
            List<Field> fieldList = ExcelDisposeUtil.getFieldList(tClass);
            // 跨行跨列缓存
            List<LoadSpanCache> loadSpanCacheList = new ArrayList<>();
            // 循环之后的行数据
            for (int j = 1; j <= rows; j++) {
                // 得到第j行
                row = sheet.getRow(j);
                // 创建类型对象
                T obj = tClass.newInstance();
                if (row != null) {
                    // 循环列
                    for (int i = 0; i < columns; i++) {
                        try {
                            // 拿到标题列的值
                            cellf = sheet.getRow(0).getCell(i);
                            // 获取cell，如果报异常，说明整个row是空的null，直接在catch里面捕获，并赋值为空
                            cell = row.getCell(i);
                        } catch (NullPointerException ignored) {
                        } finally {
                            // 如果没有读到数据行继续循环
                            if (cell != null && cellf != null) {
                                // 获取cell的类型
                                CellType type = cell.getCellType();
                                // 获取cell的类型
                                CellType typef = cellf.getCellType();
                                // 判断是否为空如果为空则跳过.无论是标题列还是数据列
                                if (typef != CellType.BLANK && type != CellType.BLANK) {
                                    // 循环标题
                                    for (Field field : fieldList) {
                                        // 判断标题列是否和字段一样
                                        if (field.getAnnotation(ExcelField.class).columnName().equals(cellf.getRichStringCellValue().getString())) {
                                            setObj(sheet, cell, type, field, obj, isGetMethodFieldValue, loadSpanCacheList);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // 保存对象
                list.add(obj);
            }
            // 处理跨行跨列缓存
            disposeLoadCache(sheet, list, fieldList, isGetMethodFieldValue, loadSpanCacheList);
        } catch (Exception e) {
            throw new ExcelOperateException("诊断：Excel导入失败！", e);
        }
        return list;
    }

    private static <T> void disposeLoadCache(Sheet sheet, List<T> list, List<Field> fieldList, boolean isGetMethodFieldValue, List<LoadSpanCache> loadSpanCacheList) throws Exception {
        for (LoadSpanCache loadSpanCache : loadSpanCacheList) {
            // 获取当前列值，但要忽略标题行
            T obj = list.get(loadSpanCache.rowSpan - 1);
            // 循环标题
            for (Field field : fieldList) {
                // 判断标题列是否和字段一样
                if (field.getAnnotation(ExcelField.class).columnName().equals(sheet.getRow(0).getCell(loadSpanCache.colSpan).getRichStringCellValue().getString())) {
                    if (isGetMethodFieldValue) {
                        setMethodFieldValue(field, obj, loadSpanCache.value);
                    } else {
                        setFieldValue(field, obj, loadSpanCache.value);
                    }
                }
            }
        }
    }

    private static void disposeLoadCache(List<TreeMap<String, Object>> list, List<LoadSpanCache> loadSpanCacheList) {
        for (LoadSpanCache loadSpanCache : loadSpanCacheList) {
            // 获取当前列值
            TreeMap<String, Object> map = list.get(loadSpanCache.rowSpan);
            //长度转成ABC列
            String colString = CellReference.convertNumToColString(loadSpanCache.colSpan);
            // 结果对象
            map.put(colString, loadSpanCache.value);
        }
    }

    private static void setObj(Sheet sheet, Cell cell, CellType type, TreeMap<String, Object> map, List<LoadSpanCache> loadSpanCacheList) {
        try {
            //长度转成ABC列
            String colString = CellReference.convertNumToColString(cell.getColumnIndex());
            // 结果对象
            Object value = null;
            if (type == CellType.NUMERIC) {
                value = cell.getNumericCellValue();
            } else if (type == CellType.STRING) {
                value = cell.getRichStringCellValue().getString();
            } else if (type == CellType.BOOLEAN) {
                value = cell.getBooleanCellValue();
            }
            if (value != null && !"".equals(value.toString())) {
                map.put(colString, value);
                // 获取跨列 -1 代表忽略自身列
                int colSpan = getColSpan(cell, sheet) - 1;
                // 获取跨行 -1 代表忽略自身行
                int rowSpan = getRowSpan(cell, sheet) - 1;
                // 列
                int columnIndex = cell.getColumnIndex();
                // 行
                int rowIndex = cell.getRowIndex();
                // 处理是否跨行跨列，是则加入缓存
                if (rowSpan > 0 && colSpan > 0) {
                    for (int i = 0; i < colSpan; i++) {
                        columnIndex++;
                        LoadSpanCache loadSpanCache = new LoadSpanCache();
                        loadSpanCache.colSpan = columnIndex;
                        loadSpanCache.rowSpan = rowIndex;
                        loadSpanCache.value = value;
                        loadSpanCacheList.add(loadSpanCache);
                    }
                    for (int j = 0; j < rowSpan; j++) {
                        rowIndex++;
                        // 换行重置列
                        columnIndex = cell.getColumnIndex();
                        LoadSpanCache loadSpanCache = new LoadSpanCache();
                        loadSpanCache.colSpan = columnIndex;
                        loadSpanCache.rowSpan = rowIndex;
                        loadSpanCache.value = value;
                        loadSpanCacheList.add(loadSpanCache);
                        for (int i = 0; i < colSpan; i++) {
                            columnIndex++;
                            loadSpanCache = new LoadSpanCache();
                            loadSpanCache.colSpan = columnIndex;
                            loadSpanCache.rowSpan = rowIndex;
                            loadSpanCache.value = value;
                            loadSpanCacheList.add(loadSpanCache);
                        }
                    }
                } else if (rowSpan > 0) {
                    for (int i = 0; i < rowSpan; i++) {
                        rowIndex++;
                        LoadSpanCache loadSpanCache = new LoadSpanCache();
                        loadSpanCache.colSpan = columnIndex;
                        loadSpanCache.rowSpan = rowIndex;
                        loadSpanCache.value = value;
                        loadSpanCacheList.add(loadSpanCache);
                    }
                } else if (colSpan > 0) {
                    for (int i = 0; i < colSpan; i++) {
                        columnIndex++;
                        LoadSpanCache loadSpanCache = new LoadSpanCache();
                        loadSpanCache.colSpan = columnIndex;
                        loadSpanCache.rowSpan = rowIndex;
                        loadSpanCache.value = value;
                        loadSpanCacheList.add(loadSpanCache);
                    }
                }
            }

        } catch (Exception e) {
            throw new ExcelOperateException("诊断：Excel导入赋值过程错误！", e);
        }
    }

    private static void setObj(Sheet sheet, Cell cell, CellType type, Field field, Object obj, boolean isGetMethodFieldValue, List<LoadSpanCache> loadSpanCacheList) {
        try {
            // 结果对象
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
                    setMethodFieldValue(field, obj, value);
                } else {
                    setFieldValue(field, obj, value);
                }
                // 获取跨列 -1 代表忽略自身列
                int colSpan = getColSpan(cell, sheet) - 1;
                // 获取跨行 -1 代表忽略自身行
                int rowSpan = getRowSpan(cell, sheet) - 1;
                // 列
                int columnIndex = cell.getColumnIndex();
                // 行
                int rowIndex = cell.getRowIndex();
                // 处理是否跨行跨列，是则加入缓存
                if (rowSpan > 0 && colSpan > 0) {
                    for (int i = 0; i < colSpan; i++) {
                        columnIndex++;
                        LoadSpanCache loadSpanCache = new LoadSpanCache();
                        loadSpanCache.colSpan = columnIndex;
                        loadSpanCache.rowSpan = rowIndex;
                        loadSpanCache.value = value;
                        loadSpanCacheList.add(loadSpanCache);
                    }
                    for (int j = 0; j < rowSpan; j++) {
                        rowIndex++;
                        // 换行重置列
                        columnIndex = cell.getColumnIndex();
                        LoadSpanCache loadSpanCache = new LoadSpanCache();
                        loadSpanCache.colSpan = columnIndex;
                        loadSpanCache.rowSpan = rowIndex;
                        loadSpanCache.value = value;
                        loadSpanCacheList.add(loadSpanCache);
                        for (int i = 0; i < colSpan; i++) {
                            columnIndex++;
                            loadSpanCache = new LoadSpanCache();
                            loadSpanCache.colSpan = columnIndex;
                            loadSpanCache.rowSpan = rowIndex;
                            loadSpanCache.value = value;
                            loadSpanCacheList.add(loadSpanCache);
                        }
                    }
                } else if (rowSpan > 0) {
                    for (int i = 0; i < rowSpan; i++) {
                        rowIndex++;
                        LoadSpanCache loadSpanCache = new LoadSpanCache();
                        loadSpanCache.colSpan = columnIndex;
                        loadSpanCache.rowSpan = rowIndex;
                        loadSpanCache.value = value;
                        loadSpanCacheList.add(loadSpanCache);
                    }
                } else if (colSpan > 0) {
                    for (int i = 0; i < colSpan; i++) {
                        columnIndex++;
                        LoadSpanCache loadSpanCache = new LoadSpanCache();
                        loadSpanCache.colSpan = columnIndex;
                        loadSpanCache.rowSpan = rowIndex;
                        loadSpanCache.value = value;
                        loadSpanCacheList.add(loadSpanCache);
                    }
                }
            }
        } catch (Exception e) {
            throw new ExcelOperateException("诊断：Excel导入赋值过程错误！", e);
        }
    }

    private static void setMethodFieldValue(Field field, Object obj, Object value) throws Exception {
        String fieldName = field.getName();
        String fieldNameFirstUpperCase = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
        Method method;
        if (field.getType().getName().equals(CommonlyUsedType.Type_String.getValue())) {
            method = obj.getClass().getMethod("set" + fieldNameFirstUpperCase, String.class);
            String s;
            if (TextDispose.isNumber(value.toString())) {
                if (value.toString().indexOf(ExcelDisposeConstant.DOT) > 0 && value.toString().indexOf(ExcelDisposeConstant.E) > 0) {
                    DecimalFormat df = new DecimalFormat("#");
                    s = df.format(value);
                    method.invoke(obj, s);
                } else if (value.toString().indexOf(ExcelDisposeConstant.DOT) > 0) {
                    s = value.toString().substring(value.toString().indexOf(ExcelDisposeConstant.DOT) + 1);
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
                method.invoke(obj, value.toString());
            }
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_Util_Date.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Sql_Date.getValue())) {
            method = obj.getClass().getMethod("set" + fieldNameFirstUpperCase, Date.class);
            if (TextDispose.isNumber(value.toString())) {
                int d = (int) Double.parseDouble(value.toString()) - 2;
                value = DateDispose.dayCalculateString("1900-1-1", d, DateType.Year_Month_Day);
            }
            value = DateDispose.formattingDate(value.toString(), field.getAnnotation(ExcelField.class).dateType());
            method.invoke(obj, value);
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_Integer.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_int.getValue())) {
            method = obj.getClass().getMethod("set" + fieldNameFirstUpperCase, Integer.class);
            if (value.toString().indexOf(ExcelDisposeConstant.DOT) > 0) {
                double d = Double.parseDouble(value.toString());
                method.invoke(obj, (int) d);
            } else {
                method.invoke(obj, Integer.parseInt(value.toString()));
            }
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_Long.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_long.getValue())) {
            method = obj.getClass().getMethod("set" + fieldNameFirstUpperCase, Long.class);
            if (value.toString().indexOf(ExcelDisposeConstant.DOT) > 0) {
                double d = Double.parseDouble(value.toString());
                method.invoke(obj, (long) d);
            } else {
                method.invoke(obj, Long.parseLong(value.toString()));
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
    }

    private static void setFieldValue(Field field, Object obj, Object value) throws Exception {
        //判断哪个类型.读取set方法拿到结果
        if (field.getType().getName().equals(CommonlyUsedType.Type_Util_Date.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Sql_Date.getValue())) {
            if (TextDispose.isNumber(value.toString())) {
                int d = (int) Double.parseDouble(value.toString()) - 2;
                value = DateDispose.dayCalculateString("1900-1-1", d, DateType.Year_Month_Day);
            }
            value = DateDispose.formattingDate(value.toString(), field.getAnnotation(ExcelField.class).dateType());
            field.set(obj, value);
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_int.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Integer.getValue())) {
            if (value.toString().indexOf(ExcelDisposeConstant.DOT) > 0) {
                double d = Double.parseDouble(value.toString());
                field.set(obj, (int) d);
            } else {
                field.set(obj, Integer.parseInt(value.toString()));
            }
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_long.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Long.getValue())) {
            if (value.toString().indexOf(ExcelDisposeConstant.DOT) > 0) {
                double d = Double.parseDouble(value.toString());
                field.set(obj, (long) d);
            } else {
                field.set(obj, Long.parseLong(value.toString()));
            }
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_double.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue())) {
            field.set(obj, Double.parseDouble(value.toString()));
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_boolean.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Boolean.getValue())) {
            field.set(obj, Boolean.parseBoolean(value.toString()));
        } else if (field.getType().getName().equals(CommonlyUsedType.Type_String.getValue())) {
            String s;
            if (TextDispose.isNumber(value.toString())) {
                if (value.toString().indexOf(ExcelDisposeConstant.DOT) > 0 && value.toString().indexOf(ExcelDisposeConstant.E) > 0) {
                    DecimalFormat df = new DecimalFormat("#");
                    s = df.format(value);
                    field.set(obj, s);
                } else if (value.toString().indexOf(ExcelDisposeConstant.DOT) > 0) {
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

    /**
     * 获取cell跨的行数
     *
     * @param cell  单元格
     * @param sheet 页签
     * @return 跨行数
     */
    public static int getRowSpan(Cell cell, Sheet sheet) {
        int rowSpan = 1;
        List<CellRangeAddress> list = sheet.getMergedRegions();
        for (CellRangeAddress cellRangeAddress : list) {
            if (cellRangeAddress.isInRange(cell)) {
                // +1是因为如果没跨，就算1
                rowSpan = cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow() + 1;
                break;
            }
        }
        return rowSpan;
    }

    /**
     * 获取cell跨的列数
     *
     * @param cell  单元格
     * @param sheet 页签
     * @return 跨列数
     */
    public static int getColSpan(Cell cell, Sheet sheet) {
        int colSpan = 1;
        List<CellRangeAddress> list = sheet.getMergedRegions();
        for (CellRangeAddress cellRangeAddress : list) {
            if (cellRangeAddress.isInRange(cell)) {
                // +1是因为如果没跨，就算1
                colSpan = cellRangeAddress.getLastColumn() - cellRangeAddress.getFirstColumn() + 1;
                break;
            }
        }
        return colSpan;
    }
}