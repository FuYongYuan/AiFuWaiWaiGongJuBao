package excel.operation.set;

/**
 * 额外数据
 */
public class ExtraData {
    /**
     * 行号
     */
    private Integer rowNumber;
    /**
     * 列号
     */
    private Integer cellNumber;
    /**
     * 列值
     */
    private Object cellValue;
    /**
     * 是否新增行
     */
    private Boolean isNewRow;

    /**
     * 创建
     */
    public static ExtraData create() {
        return new ExtraData().setNewRow(true);
    }

    /**
     * 初始化
     */
    public ExtraData build() {
        return this;
    }

    /**
     * 行号
     */
    public Integer getRowNumber() {
        return rowNumber;
    }

    /**
     * 行号
     */
    public ExtraData setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber - 1;
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
    public ExtraData setCellNumber(Integer cellNumber) {
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
    public ExtraData setCellValue(Object cellValue) {
        this.cellValue = cellValue;
        return this;
    }

    /**
     * 是否新增行
     */
    public Boolean getNewRow() {
        return isNewRow;
    }

    /**
     * 是否新增行
     */
    public ExtraData setNewRow(Boolean newRow) {
        isNewRow = newRow;
        return this;
    }
}
