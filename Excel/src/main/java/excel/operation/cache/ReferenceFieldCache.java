package excel.operation.cache;

import excel.annotation.ExcelField;

import java.lang.reflect.Field;

/**
 * 参考字段缓存
 *
 * @author fyy
 */
public class ReferenceFieldCache {
    /**
     * 获取参考字段
     */
    public Field referenceField;

    /**
     * 获取字段注解
     */
    public ExcelField referenceExcelField;

    /**
     * 参考列值
     */
    public String rowspanAlignCellValue;

    /**
     * 对比列下一行值
     */
    public String nextCellValue;
}
