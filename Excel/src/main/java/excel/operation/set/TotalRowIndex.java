package excel.operation.set;

import java.lang.reflect.Field;

/**
 * 总计需要统计的行数对象
 */
public class TotalRowIndex {
    /**
     * 当前记录列
     */
    public Field field;
    /**
     * 开始行
     */
    public int rowspanStart;
    /**
     * 结束行
     */
    public int rowspanEnd;
    /**
     * 记录内容
     */
    public String oldCellValue;

    /**
     * 合并ID
     */
    public int regionIndex;

    /**
     * 列数
     */
    public int columnNo;

}
