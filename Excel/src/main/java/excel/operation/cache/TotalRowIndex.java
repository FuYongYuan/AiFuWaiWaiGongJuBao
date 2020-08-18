package excel.operation.cache;

import java.lang.reflect.Field;

/**
 * 总计需要统计的行数对象
 *
 * @author fyy
 */
public class TotalRowIndex {
    /**
     * 当前记录列
     */
    public Field field;

    /**
     * 开始行
     */
    public Integer rowspanStart = 0;

    /**
     * 结束行
     */
    public Integer rowspanEnd = 0;

    /**
     * 记录内容
     */
    public String oldCellValue;

    /**
     * 合并ID
     */
    public Integer regionIndex;

    /**
     * 列数
     */
    public Integer columnNo;

    /**
     * 接下来是否会合并
     */
    public Boolean nextRowspan = false;

}
