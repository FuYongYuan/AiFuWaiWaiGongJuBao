package couchbase.standard;


import enumerate.DateType;

/**
 * 代码行为规范
 */
public class CodeConductStandard {
    //------------------------------------------------------------------------------------------------------------------固定的字段写法
    /**
     * 文档对象ID属性名称
     */
    public static final String Document_Id = "Id";
    /**
     * 文档对象类类型属性名称
     */
    public static final String Document_ClassTypeName = "ClassTypeName";
    /**
     * 文档对象更新时间属性名称
     */
    public static final String Document_UpdateTime = "UpdateTime";
    /**
     * 文档对象创建时间属性名称
     */
    public static final String Document_CreateTime = "CreateTime";
    /**
     * 文档对象创建人ID属性名称
     */
    public static final String Document_CreateManId = "CreateManId";
    /**
     * 文档对象修改人ID属性名称
     */
    public static final String Document_UpdateManId = "UpdateManId";
    /**
     * 文档对象数据有效性属性名称
     */
    public static final String Document_IsValidity = "IsValidity";
    //------------------------------------------------------------------------------------------------------------------固定的语句写法
    /**
     * 删除语句的标识
     */
    public static final String Delete = "DELETE";
    /**
     * 查询语句的标识
     */
    public static final String Select = "SELECT";
    /**
     * 成功返回
     */
    public static final String Success = "success";
    /**
     * 错误返回
     */
    public static final String Errors = "errors";

    //------------------------------------------------------------------------------------------------------------------时间格式
    /**
     * 文档对象时间数据格式
     */
    public static final String Document_DateFormat = "yyyy-MM-dd HH:mm:ss";
    /**
     * 文档对象时间数据格式
     */
    public static final DateType Document_DateFormatType = DateType.Year_Month_Day_Hour_Minute_Second;
    /**
     * 文档对象时间数据格式
     */
    public static final String Document_TimeZone = "GMT+8";
}
