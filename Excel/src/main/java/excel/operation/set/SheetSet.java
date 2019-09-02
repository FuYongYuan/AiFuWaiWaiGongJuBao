package excel.operation.set;

import dispose.TextDispose;
import excel.exception.ExcelOperateException;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public class SheetSet {

    /**
     * 工作簿页名称
     */
    private String workbookName;

    /**
     * 导出对象集合
     */
    private List<?> workbookData;

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
    private SheetData sheetData;

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
            throw new ExcelOperateException("诊断：工作簿对象缺失");
        }

        this.style = new Style(workbook);
        this.function = new Function(workbook);

        if (TextDispose.isEmpty(workbookName)) {
            throw new ExcelOperateException("诊断：工作簿名称缺失");
        }

        if (dataClass == null) {
            throw new ExcelOperateException("诊断：Excel对应关系缺失");
        } else {
            if (workbookData != null && workbookData.size() > 0) {
                if (workbookData.get(0).getClass() != dataClass) {
                    throw new ExcelOperateException("诊断：Excel数据类型错误");
                }
            }
        }

        this.sheetData = new SheetData();

        if (this.isGetMethodFieldValue == null) {
            this.isGetMethodFieldValue = true;
        }

        return this;
    }


    /**
     * 工作簿页名称
     */
    public String getWorkbookName() {
        return workbookName;
    }

    /**
     * 工作簿页名称
     */
    public SheetSet setWorkbookName(String workbookName) {
        this.workbookName = workbookName;
        return this;
    }

    /**
     * 导出对象集合
     */
    public List<?> getWorkbookData() {
        return workbookData;
    }

    /**
     * 导出对象集合
     */
    public SheetSet setWorkbookData(List<?> workbookData) {
        this.workbookData = workbookData;
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
    public SheetData getSheetData() {
        return sheetData;
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
}
