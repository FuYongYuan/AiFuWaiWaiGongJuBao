package excel.operation.set;

import enumerate.DateType;
import excel.exception.ExcelOperateException;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.reflect.Type;

/**
 * 额外列数据
 */
public class ExtraCellData {
    /**
     * 列号
     */
    private Integer cellNumber;

    /**
     * 列值
     */
    private Object cellValue;

    /**
     * 列类型
     */
    private Type cellType;

    /**
     * 保留位数
     */
    private int decimalAfterDigit;

    /**
     * 日期格式
     */
    private DateType dateType;

    /**
     * 水平位置
     */
    private HorizontalAlignment horizontalAlignment;

    /**
     * 上下位置
     */
    private VerticalAlignment verticalAlignment;

    /**
     * 是否是钱类型
     */
    private boolean isMoney;

    /**
     * 列宽
     */
    private int columnWidth;

    /**
     * 是否自动调整列宽
     */
    private boolean isAutoSize;

    /**
     * 创建
     */
    public static ExtraCellData create() {
        return new ExtraCellData()
                .setDecimalAfterDigit(3)
                .setDateType(DateType.Year_Month_Day_Hour_Minute_Second)
                .setHorizontalAlignment(HorizontalAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.CENTER)
                .setIsMoney(false)
                .setColumnWidth(-1)
                .setIsAutoSize(false)
                ;
    }

    /**
     * 初始化
     */
    public ExtraCellData build() {
        if (cellNumber == null) {
            throw new ExcelOperateException("诊断：缺少额外列数据列号！", new NullPointerException());
        }
        if (cellValue == null) {
            throw new ExcelOperateException("诊断：缺少额外列数据值！", new NullPointerException());
        }
        if (cellType == null) {
            throw new ExcelOperateException("诊断：缺少额外列数据类型！", new NullPointerException());
        }
        return this;
    }

    /**
     * 列号
     */
    public Integer getCellNumber() {
        return cellNumber;
    }

    /**
     * 列号
     */
    public ExtraCellData setCellNumber(Integer cellNumber) {
        this.cellNumber = cellNumber - 1;
        return this;
    }

    /**
     * 列值
     */
    public Object getCellValue() {
        return cellValue;
    }

    /**
     * 列值
     */
    public ExtraCellData setCellValue(Object cellValue) {
        this.cellValue = cellValue;
        return this;
    }

    /**
     * 列类型
     */
    public Type getCellType() {
        return cellType;
    }

    /**
     * 列类型
     */
    public ExtraCellData setCellType(Type cellType) {
        this.cellType = cellType;
        return this;
    }

    /**
     * 保留位数
     */
    public int getDecimalAfterDigit() {
        return decimalAfterDigit;
    }

    /**
     * 保留位数
     */
    public ExtraCellData setDecimalAfterDigit(int decimalAfterDigit) {
        this.decimalAfterDigit = decimalAfterDigit;
        return this;
    }

    /**
     * 日期格式
     */
    public DateType getDateType() {
        return dateType;
    }

    /**
     * 日期格式
     */
    public ExtraCellData setDateType(DateType dateType) {
        this.dateType = dateType;
        return this;
    }

    /**
     * 水平位置
     */
    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    /**
     * 水平位置
     */
    public ExtraCellData setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        return this;
    }

    /**
     * 上下位置
     */
    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    /**
     * 上下位置
     */
    public ExtraCellData setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        return this;
    }

    /**
     * 是否是钱类型
     */
    public boolean getIsMoney() {
        return isMoney;
    }

    /**
     * 是否是钱类型
     */
    public ExtraCellData setIsMoney(boolean isMoney) {
        this.isMoney = isMoney;
        return this;
    }

    /**
     * 列宽
     */
    public int getColumnWidth() {
        return columnWidth;
    }

    /**
     * 列宽
     */
    public ExtraCellData setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
        return this;
    }

    /**
     * 是否自动调整列宽
     */
    public boolean getIsAutoSize() {
        return isAutoSize;
    }

    /**
     * 是否自动调整列宽
     */
    public ExtraCellData setIsAutoSize(boolean isAutoSize) {
        this.isAutoSize = isAutoSize;
        return this;
    }
}
