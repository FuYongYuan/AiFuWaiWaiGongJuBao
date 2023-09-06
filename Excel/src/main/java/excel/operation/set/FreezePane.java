package excel.operation.set;

/**
 * 冻结参数
 *
 * @author fyy
 */
public class FreezePane {
    /**
     * 列拆分
     */
    private int colSplit;
    /**
     * 行拆分
     */
    private int rowSplit;
    /**
     * 左侧最大列
     */
    private int leftmostColumn;
    /**
     * 顶部行
     */
    private int topRow;

    /**
     * 构造
     */
    private FreezePane() {
    }

    /**
     * 创建
     */
    public static FreezePane create() {
        return new FreezePane();
    }

    /**
     * 初始化 需要工作簿对象
     */
    public FreezePane build() {
        return this;
    }


    /**
     * 列拆分
     */
    public int getColSplit() {
        return colSplit;
    }

    /**
     * 列拆分
     */
    public FreezePane setColSplit(int colSplit) {
        this.colSplit = colSplit;
        return this;
    }

    /**
     * 行拆分
     */
    public int getRowSplit() {
        return rowSplit;
    }

    /**
     * 行拆分
     */
    public FreezePane setRowSplit(int rowSplit) {
        this.rowSplit = rowSplit;
        return this;
    }

    /**
     * 左侧最大列
     */
    public int getLeftmostColumn() {
        return leftmostColumn;
    }

    /**
     * 左侧最大列
     */
    public FreezePane setLeftmostColumn(int leftmostColumn) {
        this.leftmostColumn = leftmostColumn;
        return this;
    }

    /**
     * 顶部行
     */
    public int getTopRow() {
        return topRow;
    }

    /**
     * 顶部行
     */
    public FreezePane setTopRow(int topRow) {
        this.topRow = topRow;
        return this;
    }
}
