package excel.operation.set;

import enumerate.DateType;
import excel.exception.ExcelOperateException;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Type;

/**
 * 额外列数据
 *
 * @author fyy
 */
public class ExtraCellData {
    /**
     * 列号
     */
    private Integer cellNumber;

    /**
     * 夸列数量 包含自身 默认1
     */
    private Integer colspan;

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
     * 填充颜色类型
     */
    private FillPatternType fillPattern;

    /**
     * 填充颜色
     */
    private short fillForegroundColor;

    /**
     * 水平位置
     */
    private HorizontalAlignment horizontalAlignment;

    /**
     * 上下位置
     */
    private VerticalAlignment verticalAlignment;

    /**
     * 是否加粗
     */
    private Boolean isBold;

    /**
     * 是否是钱类型
     */
    private boolean isMoney;

    /**
     * 是否是中国大写钱类型
     */
    private boolean isChinaMoney;

    /**
     * 列宽
     */
    private int columnWidth;

    /**
     * 是否自动调整列宽
     */
    private boolean isAutoSize;

    /**
     * 边框
     */
    private BorderStyle border;

    /**
     * 边框颜色
     */
    private IndexedColors borderColor;

    /**
     * 上边框
     */
    private BorderStyle borderTop;

    /**
     * 上边框颜色
     */
    private IndexedColors borderTopColor;

    /**
     * 下边框
     */
    private BorderStyle borderBottom;

    /**
     * 下边框颜色
     */
    private IndexedColors borderBottomColor;

    /**
     * 左边框
     */
    private BorderStyle borderLeft;

    /**
     * 左边框颜色
     */
    private IndexedColors borderLeftColor;

    /**
     * 右边框
     */
    private BorderStyle borderRight;

    /**
     * 右边框颜色
     */
    private IndexedColors borderRightColor;

    /**
     * 内部构造
     */
    private ExtraCellData() {
    }

    /**
     * 创建
     */
    public static ExtraCellData create() {
        return new ExtraCellData()
                .setColspan(1)
                .setDecimalAfterDigit(3)
                .setDateType(DateType.Year_Month_Day_Hour_Minute_Second)
                .setFillPattern(FillPatternType.NO_FILL)
                .setFillForegroundColor((short) -1)
                .setHorizontalAlignment(HorizontalAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.CENTER)
                .setIsBold(false)
                .setIsMoney(false)
                .setChinaIsMoney(false)
                .setColumnWidth(-1)
                .setIsAutoSize(false)
                .setBorder(BorderStyle.NONE)
                .setBorderColor(IndexedColors.BLACK)
                .setBorderTop(BorderStyle.NONE)
                .setBorderTopColor(IndexedColors.BLACK)
                .setBorderBottom(BorderStyle.NONE)
                .setBorderBottomColor(IndexedColors.BLACK)
                .setBorderLeft(BorderStyle.NONE)
                .setBorderLeftColor(IndexedColors.BLACK)
                .setBorderRight(BorderStyle.NONE)
                .setBorderRightColor(IndexedColors.BLACK)
                ;
    }

    /**
     * 初始化
     */
    public ExtraCellData build() {
        if (cellNumber == null) {
            throw new ExcelOperateException("诊断：缺少额外列数据列号！", new NullPointerException());
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
     * 夸列数量
     */
    public Integer getColspan() {
        return colspan;
    }

    /**
     * 夸列数量
     */
    public ExtraCellData setColspan(Integer colspan) {
        this.colspan = colspan;
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
     * 填充颜色类型
     */
    public FillPatternType getFillPattern() {
        return fillPattern;
    }

    /**
     * 填充颜色类型
     */
    public ExtraCellData setFillPattern(FillPatternType fillPattern) {
        this.fillPattern = fillPattern;
        return this;
    }

    /**
     * 填充颜色
     */
    public short getFillForegroundColor() {
        return fillForegroundColor;
    }

    /**
     * 填充颜色
     */
    public ExtraCellData setFillForegroundColor(IndexedColors fillForegroundColor) {
        this.fillForegroundColor = fillForegroundColor.getIndex();
        return this;
    }

    /**
     * 填充颜色
     */
    public ExtraCellData setFillForegroundColor(short fillForegroundColor) {
        this.fillForegroundColor = fillForegroundColor;
        return this;
    }

    /**
     * 填充颜色
     */
    public ExtraCellData setFillColor(IndexedColors fillForegroundColor) {
        this.fillPattern = FillPatternType.SOLID_FOREGROUND;
        this.fillForegroundColor = fillForegroundColor.getIndex();
        return this;
    }

    /**
     * 填充颜色
     */
    public ExtraCellData setFillColor(short fillForegroundColor) {
        this.fillPattern = FillPatternType.SOLID_FOREGROUND;
        this.fillForegroundColor = fillForegroundColor;
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
     * 是否加粗
     */
    public Boolean getIsBold() {
        return isBold;
    }

    /**
     * 是否加粗
     */
    public ExtraCellData setIsBold(Boolean bold) {
        isBold = bold;
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
     * 是否是中国大写钱类型
     */
    public boolean getIsChinaMoney() {
        return isChinaMoney;
    }

    /**
     * 是否是中国大写钱类型
     */
    public ExtraCellData setChinaIsMoney(boolean isChinaMoney) {
        this.isChinaMoney = isChinaMoney;
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

    /**
     * 边框
     */
    public BorderStyle getBorder() {
        return border;
    }

    /**
     * 边框
     */
    public ExtraCellData setBorder(BorderStyle border) {
        this.border = border;
        return this;
    }

    /**
     * 边框颜色
     */
    public IndexedColors getBorderColor() {
        return borderColor;
    }

    /**
     * 边框颜色
     */
    public ExtraCellData setBorderColor(IndexedColors borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    /**
     * 上边框
     */
    public BorderStyle getBorderTop() {
        return borderTop;
    }

    /**
     * 上边框
     */
    public ExtraCellData setBorderTop(BorderStyle borderTop) {
        this.borderTop = borderTop;
        return this;
    }

    /**
     * 上边框颜色
     */
    public IndexedColors getBorderTopColor() {
        return borderTopColor;
    }

    /**
     * 上边框颜色
     */
    public ExtraCellData setBorderTopColor(IndexedColors borderTopColor) {
        this.borderTopColor = borderTopColor;
        return this;
    }

    /**
     * 下边框
     */
    public BorderStyle getBorderBottom() {
        return borderBottom;
    }

    /**
     * 下边框
     */
    public ExtraCellData setBorderBottom(BorderStyle borderBottom) {
        this.borderBottom = borderBottom;
        return this;
    }

    /**
     * 下边框颜色
     */
    public IndexedColors getBorderBottomColor() {
        return borderBottomColor;
    }

    /**
     * 下边框颜色
     */
    public ExtraCellData setBorderBottomColor(IndexedColors borderBottomColor) {
        this.borderBottomColor = borderBottomColor;
        return this;
    }

    /**
     * 左边框
     */
    public BorderStyle getBorderLeft() {
        return borderLeft;
    }

    /**
     * 左边框
     */
    public ExtraCellData setBorderLeft(BorderStyle borderLeft) {
        this.borderLeft = borderLeft;
        return this;
    }

    /**
     * 左边框颜色
     */
    public IndexedColors getBorderLeftColor() {
        return borderLeftColor;
    }

    /**
     * 左边框颜色
     */
    public ExtraCellData setBorderLeftColor(IndexedColors borderLeftColor) {
        this.borderLeftColor = borderLeftColor;
        return this;
    }

    /**
     * 右边框
     */
    public BorderStyle getBorderRight() {
        return borderRight;
    }

    /**
     * 右边框
     */
    public ExtraCellData setBorderRight(BorderStyle borderRight) {
        this.borderRight = borderRight;
        return this;
    }

    /**
     * 右边框颜色
     */
    public IndexedColors getBorderRightColor() {
        return borderRightColor;
    }

    /**
     * 右边框颜色
     */
    public ExtraCellData setBorderRightColor(IndexedColors borderRightColor) {
        this.borderRightColor = borderRightColor;
        return this;
    }
}
