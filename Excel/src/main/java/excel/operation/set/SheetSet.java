package excel.operation.set;

import dispose.TextDispose;
import excel.exception.ExcelOperateException;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Arrays;
import java.util.List;

public class SheetSet {

    /**
     * 工作簿页名称
     */
    private String sheetName;

    /**
     * 导出对象集合
     */
    private List<?> sheetData;

    /**
     * 当前页数据类型
     */
    private Class<?> dataClass;

    /**
     * 读取字段时是否采用的Get方法读取
     */
    private Boolean isGetMethodFieldValue;

    /**
     * 对应当前页样式对象
     */
    private Style style;

    /**
     * 对应当前页功能设置对象
     */
    private Function function;

    /**
     * 对应当前页的数据缓存
     */
    private SheetCache sheetCache;

    /**
     * 额外数据集合
     */
    private List<ExtraRowData> extraRowData;

    /**
     * 转换值集
     */
    private List<ValueLimit> valueLimit;

    /**
     * 构造
     */
    private SheetSet() {
    }

    /**
     * 创建
     */
    public static SheetSet create() {
        return new SheetSet();
    }

    /**
     * 初始化 需要工作簿对象
     */
    public SheetSet build(Workbook workbook) {
        if (workbook == null) {
            throw new ExcelOperateException("诊断：工作簿对象缺失！", new NullPointerException());
        }

        this.style = new Style(workbook);
        this.function = new Function(workbook);

        if (TextDispose.isEmpty(sheetName)) {
            throw new ExcelOperateException("诊断：工作簿页签名称缺失！", new NullPointerException());
        }

        if (dataClass == null) {
            throw new ExcelOperateException("诊断：Excel对应关系缺失！", new NullPointerException());
        } else {
            if (sheetData != null && sheetData.size() > 0) {
                if (sheetData.get(0).getClass() != dataClass) {
                    throw new ExcelOperateException("诊断：Excel数据类型错误！", new NullPointerException());
                }
            }
        }

        this.sheetCache = new SheetCache();

        if (this.isGetMethodFieldValue == null) {
            this.isGetMethodFieldValue = true;
        }

        return this;
    }


    /**
     * 工作簿页名称
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * 工作簿页名称
     */
    public SheetSet setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    /**
     * 导出对象集合
     */
    public List<?> getSheetData() {
        return sheetData;
    }

    /**
     * 导出对象集合
     */
    public SheetSet setSheetData(List<?> sheetData) {
        this.sheetData = sheetData;
        return this;
    }

    /**
     * 当前页数据类型
     */
    public Class<?> getDataClass() {
        return dataClass;
    }

    /**
     * 当前页数据类型
     */
    public SheetSet setDataClass(Class<?> dataClass) {
        this.dataClass = dataClass;
        return this;
    }

    /**
     * 对应当前页样式对象
     */
    public Style getStyle() {
        return style;
    }

    /**
     * 对应当前页功能设置对象
     */
    public Function getFunction() {
        return function;
    }

    /**
     * 对应当前页的数据缓存
     */
    public SheetCache getSheetCache() {
        return sheetCache;
    }

    /**
     * 读取字段时是否采用的Get方法读取
     */
    public Boolean getIsGetMethodFieldValue() {
        return isGetMethodFieldValue;
    }

    /**
     * 读取字段时是否采用的Get方法读取
     */
    public SheetSet setIsGetMethodFieldValue(Boolean isGetMethodFieldValue) {
        this.isGetMethodFieldValue = isGetMethodFieldValue;
        return this;
    }

    /**
     * 额外数据集合
     */
    public List<ExtraRowData> getExtraRowData() {
        return extraRowData;
    }

    /**
     * 额外数据集合
     */
    public SheetSet setExtraRowData(List<ExtraRowData> extraRowData) {
        this.extraRowData = extraRowData;
        return this;
    }

    /**
     * 额外数据集合
     */
    public SheetSet setExtraData(ExtraRowData... extraRowData) {
        this.extraRowData = Arrays.asList(extraRowData);
        return this;
    }

    /**
     * 转换值集
     */
    public List<ValueLimit> getValueLimit() {
        return valueLimit;
    }

    /**
     * 转换值集
     */
    public ValueLimit getValueLimit(String valueListName) {
        for (ValueLimit vl : valueLimit) {
            if (vl.getValueListName().equals(valueListName)) {
                return vl;
            }
        }
        return null;
    }

    /**
     * 转换值集
     */
    public SheetSet setValueLimit(List<ValueLimit> valueLimit) {
        this.valueLimit = valueLimit;
        return this;
    }

    /**
     * 转换值集
     */
    public SheetSet setValueLimit(ValueLimit... valueLimit) {
        this.valueLimit = Arrays.asList(valueLimit);
        return this;
    }
}
