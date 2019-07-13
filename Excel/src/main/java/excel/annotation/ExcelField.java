package excel.annotation;

import enumerate.DateType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ExcelField 到处导入Excel时使用的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelField {
    /**
     * 列名称
     */
    String columnName();

    /**
     * 排序
     */
    int order();

    /**
     * 日期格式
     */
    DateType dateType() default DateType.Year_Month_Day_Hour_Minute_Second;

    /**
     * 保留位数
     */
    int decimalAfterDigit() default 3;

    /**
     * 水平位置
     */
    HorizontalAlignment horizontalAlignment() default HorizontalAlignment.LEFT;

    /**
     * 上下位置
     */
    VerticalAlignment verticalAlignment() default VerticalAlignment.CENTER;

    /**
     * 列宽
     */
    int columnWidth() default -1;

    /**
     * 是否是钱类型
     */
    boolean isMoney() default false;

    /**
     * 是否合并相同值的列
     */
    boolean rowspan() default false;

    /**
     * 是否隐藏该列
     */
    boolean isHidden() default false;

    /**
     * 是否自动调整列宽
     */
    boolean isAutoSize() default false;

    /**
     * 合并参考列序号
     */
    int rowspanAlignOrder() default 0;
}
