package excel.operation.cache;

/**
 * 读Excel跨行跨列缓存
 * @author fyy
 */
public class LoadSpanCache {
    /**
     * 跨列数
     */
    public int colSpan;
    /**
     * 跨行数
     */
    public int rowSpan;
    /**
     * 值
     */
    public Object value;

    @Override
    public String toString() {
        return "LoadSpanCache{" +
                "colSpan=" + colSpan +
                ", rowSpan=" + rowSpan +
                ", value=" + value +
                '}';
    }
}
