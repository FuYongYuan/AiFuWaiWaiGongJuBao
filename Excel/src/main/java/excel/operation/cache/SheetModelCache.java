package excel.operation.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页签运行缓存
 *
 * @author fyy
 */
public class SheetModelCache {
    /**
     * 初始行数
     */
    public Integer initRow = 0;

    /**
     * 额外行数
     */
    public Integer extraRow = 0;

    /**
     * 行
     */
    public Integer i = 0;

    /**
     * 列
     */
    public Integer j = 0;

    /**
     * 总行号map
     */
    public Map<String, TotalRowIndex> totalRowIndexMap = new HashMap<>();

    /**
     * 占用行
     */
    public List<Integer> occupyRows = new ArrayList<>();

}
