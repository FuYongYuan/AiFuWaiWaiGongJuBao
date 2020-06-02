package excel.operation.set;

import dispose.TextDispose;
import excel.exception.ExcelOperateException;

import java.util.List;

/**
 * 值集对象
 */
public class ValueLimit {
    /**
     * 是否是MAP数据类型
     */
    private Boolean isMap;

    /**
     * 是否使用get读取值
     */
    private Boolean isGetMethodFieldValue;

    /**
     * 获取对比值的字段名称
     */
    private String contrastFieldName;

    /**
     * 获取转换值的字段名称
     */
    private String replaceFieldName;

    /**
     * 值集
     */
    private List<?> valueList;

    /**
     * 值集名称
     */
    private String valueListName;

    /**
     * 内部构造
     */
    private ValueLimit() {
    }

    /**
     * 创建
     */
    public static ValueLimit create() {
        return new ValueLimit().setIsMap(false);
    }

    /**
     * 初始化
     */
    public ValueLimit build() {
        if (valueList == null || valueList.isEmpty()) {
            throw new ExcelOperateException("诊断：值集内容为空请查验！", new NullPointerException());
        }
        if (TextDispose.isEmpty(this.valueListName)) {
            throw new ExcelOperateException("诊断：值集未被赋予名称请查验！", new NullPointerException());
        }
        if (TextDispose.isEmpty(this.contrastFieldName)) {
            throw new ExcelOperateException("诊断：值集 " + this.valueListName + " 无提供比较值字段名称，无法进行转换！", new NullPointerException());
        }
        if (TextDispose.isEmpty(this.replaceFieldName)) {
            throw new ExcelOperateException("诊断：值集 " + this.valueListName + " 无提供替换值字段名称，无法进行转换！", new NullPointerException());
        }

        if (this.isMap == null) {
            this.isMap = false;
        }

        if (this.isGetMethodFieldValue == null) {
            this.isGetMethodFieldValue = true;
        }

        return this;
    }

    /**
     * 是否是MAP数据类型
     */
    public Boolean getIsMap() {
        return isMap;
    }

    /**
     * 是否是MAP数据类型
     */
    public ValueLimit setIsMap(Boolean isMap) {
        this.isMap = isMap;
        return this;
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
    public ValueLimit setIsGetMethodFieldValue(Boolean isGetMethodFieldValue) {
        this.isGetMethodFieldValue = isGetMethodFieldValue;
        return this;
    }

    /**
     * 获取对比值的字段名称
     */
    public String getContrastFieldName() {
        return contrastFieldName;
    }

    /**
     * 获取对比值的字段名称
     */
    public ValueLimit setContrastFieldName(String contrastFieldName) {
        this.contrastFieldName = contrastFieldName;
        return this;
    }

    /**
     * 获取转换值的字段名称
     */
    public String getReplaceFieldName() {
        return replaceFieldName;
    }

    /**
     * 获取转换值的字段名称
     */
    public ValueLimit setReplaceFieldName(String replaceFieldName) {
        this.replaceFieldName = replaceFieldName;
        return this;
    }

    /**
     * 值集
     */
    public List<?> getValueList() {
        return valueList;
    }

    /**
     * 值集
     */
    public ValueLimit setValueList(List<?> valueList) {
        this.valueList = valueList;
        return this;
    }

    /**
     * 值集名称
     */
    public String getValueListName() {
        return valueListName;
    }

    /**
     * 值集名称
     */
    public ValueLimit setValueListName(String valueListName) {
        this.valueListName = valueListName;
        return this;
    }
}
