package excel.operation.set;

import org.apache.poi.ss.usermodel.*;

/**
 * 样式设置类
 */
public class Style {
    /**
     * 标题样式
     */
    private CellStyle title;

    //------------------------------------------------------------------------------------------------------------------内部参数
    /**
     * 字体
     */
    private Font font;

    //------------------------------------------------------------------------------------------------------------------构造
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
}
