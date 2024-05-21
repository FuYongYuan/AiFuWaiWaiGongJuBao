package excel.operation.set;

import excel.exception.ExcelOperateException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 额外行数据
 *
 * @author fyy
 */
public class ExtraRowData {
    /**
     * 行号
     */
    private Integer rowNumber;

    /**
     * 是否取最后一行做为行号
     */
    private Boolean isMaxRowNumber;

    /**
     * 是否新增行
     */
    private Boolean isNewRow;

    /**
     * 列集合
     */
    private List<ExtraCellData> extraCellData;

    /**
     * 内部构造
     */
    private ExtraRowData() {
    }

    /**
     * 创建
     */
    public static ExtraRowData create() {
        return new ExtraRowData().setIsNewRow(true).setIsMaxRowNumber(false);
    }

    /**
     * 初始化
     */
    public ExtraRowData build() {
        if (this.rowNumber == null && !this.isMaxRowNumber) {
            throw new ExcelOperateException("诊断：缺少额外行数据行号！", new NullPointerException());
        }

        List<Integer> occupy = new ArrayList<>();
        for (ExtraCellData cell : extraCellData) {
            if (cell.getColspan() != null && cell.getColspan() > 1) {
                for (int i = 1; i <= cell.getColspan(); i++) {
                    occupy.add(cell.getCellNumber() + (i - 1));
                }
            } else {
                occupy.add(cell.getCellNumber());
            }
        }
        boolean isRepeat = occupy.size() != new HashSet<>(occupy).size();
        if (isRepeat) {
            throw new ExcelOperateException("诊断：存在夸列重叠请检查列号及夸列数设置！", new IndexOutOfBoundsException());
        }

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
    public ExtraRowData setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber - 1;
        return this;
    }

    /**
     * 是否取最后一行做为行号
     */
    public Boolean getIsMaxRowNumber() {
        return isMaxRowNumber;
    }

    /**
     * 是否取最后一行做为行号
     */
    public ExtraRowData setIsMaxRowNumber(Boolean isMaxRowNumber) {
        this.isMaxRowNumber = isMaxRowNumber;
        return this;
    }

    /**
     * 是否新增行
     */
    public Boolean getIsNewRow() {
        return isNewRow;
    }

    /**
     * 是否新增行
     */
    public ExtraRowData setIsNewRow(Boolean isNewRow) {
        this.isNewRow = isNewRow;
        return this;
    }

    /**
     * 列集合
     */
    public List<ExtraCellData> getExtraCellData() {
        return extraCellData;
    }

    /**
     * 列集合
     */
    public ExtraRowData setExtraCellData(List<ExtraCellData> extraCellData) {
        this.extraCellData = extraCellData;
        return this;
    }

    /**
     * 列集合
     */
    public ExtraRowData setExtraCellData(ExtraCellData... extraCellData) {
        this.extraCellData = Arrays.asList(extraCellData);
        return this;
    }
}

