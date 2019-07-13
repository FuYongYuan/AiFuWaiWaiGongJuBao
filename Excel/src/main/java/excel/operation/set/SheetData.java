package excel.operation.set;

import org.apache.poi.ss.usermodel.CellStyle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运转基础类
 */
public class SheetData {

    /**
     * 使用到的字段
     */
    public List<Field> useField = new ArrayList<>();

    /**
     * 小计参照列
     */
    public Field subTotalReferenceField;

    /**
     * 总计参照列
     */
    public Field totalReferenceField;

    /**
     * 小计统计行下标
     */
    public List<Integer> subTotalColumnIndex = new ArrayList<>();

    /**
     * 总计统计行下标
     */
    public List<Integer> totalColumnIndex = new ArrayList<>();

    /**
     * 总计全部统计行下标
     */
    public List<Integer> totalAllColumnIndex = new ArrayList<>();

    /**
     * 小计重新合并列
     */
    public List<Field> subTotalSpanField = new ArrayList<>();

    /**
     * 总计重新合并列
     */
    public List<Field> totalSpanField = new ArrayList<>();

    /**
     * 列样式记录
     */
    public Map<String, CellStyle> cellStyleMap = new HashMap<>();

}
