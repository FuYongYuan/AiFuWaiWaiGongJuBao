package excel.operation.set;

import org.apache.poi.ss.usermodel.*;

/**
 * 样式设置类
 *
 * @author fyy
 */
public class Style {
    /**
     * 标题样式
     */
    private CellStyle title;

    /**
     * 全局数据是否加边框
     */
    private BorderStyle contextBorder;

    /**
     * 全局数据是否加边框颜色
     */
    private IndexedColors contextBorderColor;

    //------------------------------------------------------------------------------------------------------------------内部参数
    /**
     * 字体
     */
    private final Font font;

    //------------------------------------------------------------------------------------------------------------------构造

    /**
     * 构造
     *
     * @param workbook 导出工作簿对象
     */
    public Style(Workbook workbook) {
        this.title = workbook.createCellStyle();
        this.font = workbook.createFont();
    }

    //------------------------------------------------------------------------------------------------------------------GetSet

    /**
     * 标题样式
     */
    public CellStyle getTitle() {
        return title;
    }

    /**
     * 标题样式
     */
    public Style setTitle(CellStyle title) {
        this.title = title;
        return this;
    }

    /**
     * 标题颜色
     */
    public Style setTitleColor(short titleColor) {
        title.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        title.setFillForegroundColor(titleColor);
        return this;
    }

    /**
     * 标题颜色
     */
    public Style setTitleColor(IndexedColors indexedColors) {
        title.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        title.setFillForegroundColor(indexedColors.getIndex());
        return this;
    }

    /**
     * 水平位置设置
     */
    public Style setTitleHorizontalAlignment(HorizontalAlignment alignment) {
        title.setAlignment(alignment);
        return this;
    }

    /**
     * 垂直位置设置
     */
    public Style setTitleVerticalAlignment(VerticalAlignment alignment) {
        title.setVerticalAlignment(alignment);
        return this;
    }

    /**
     * 内容位置设置
     */
    public Style setTitleAlignment(HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
        title.setAlignment(horizontalAlignment);
        title.setVerticalAlignment(verticalAlignment);
        return this;
    }

    /**
     * 标题是否加粗
     */
    public Style setTitleBold(boolean bold) {
        font.setBold(true);
        title.setFont(font);
        return this;
    }

    /**
     * 边框
     */
    public Style setTitleBorder(BorderStyle borderStyle) {
        title.setBorderTop(borderStyle);
        title.setBorderBottom(borderStyle);
        title.setBorderLeft(borderStyle);
        title.setBorderRight(borderStyle);
        return this;
    }

    /**
     * 边框颜色
     */
    public Style setTitleBorderColor(IndexedColors indexedColors) {
        title.setTopBorderColor(indexedColors.getIndex());
        title.setBottomBorderColor(indexedColors.getIndex());
        title.setLeftBorderColor(indexedColors.getIndex());
        title.setRightBorderColor(indexedColors.getIndex());
        return this;
    }

    /**
     * 上边框
     */
    public Style setTitleBorderTop(BorderStyle borderStyle) {
        title.setBorderTop(borderStyle);
        return this;
    }

    /**
     * 上边框颜色
     */
    public Style setTitleBorderTopColor(IndexedColors indexedColors) {
        title.setTopBorderColor(indexedColors.getIndex());
        return this;
    }

    /**
     * 下边框
     */
    public Style setTitleBorderBottom(BorderStyle borderStyle) {
        title.setBorderBottom(borderStyle);
        return this;
    }

    /**
     * 下边框颜色
     */
    public Style setTitleBorderBottomColor(IndexedColors indexedColors) {
        title.setBottomBorderColor(indexedColors.getIndex());
        return this;
    }

    /**
     * 左边框
     */
    public Style setTitleBorderLeft(BorderStyle borderStyle) {
        title.setBorderLeft(borderStyle);
        return this;
    }

    /**
     * 左边框颜色
     */
    public Style setTitleBorderLeftColor(IndexedColors indexedColors) {
        title.setLeftBorderColor(indexedColors.getIndex());
        return this;
    }

    /**
     * 右边框
     */
    public Style setTitleBorderRight(BorderStyle borderStyle) {
        title.setBorderRight(borderStyle);
        return this;
    }

    /**
     * 右边框颜色
     */
    public Style setTitleBorderRightColor(IndexedColors indexedColors) {
        title.setRightBorderColor(indexedColors.getIndex());
        return this;
    }

    /**
     * 全局数据是否加边框
     */
    public BorderStyle getContextBorder() {
        return contextBorder;
    }

    /**
     * 全局数据是否加边框
     */
    public Style setContextBorder(BorderStyle contextBorder) {
        this.contextBorder = contextBorder;
        return this;
    }

    /**
     * 全局数据是否加边框颜色
     */
    public IndexedColors getContextBorderColor() {
        return contextBorderColor;
    }

    /**
     * 全局数据是否加边框颜色
     */
    public Style setContextBorderColor(IndexedColors contextBorderColor) {
        this.contextBorderColor = contextBorderColor;
        return this;
    }
}
