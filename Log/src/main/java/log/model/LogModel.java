package log.model;


import enumerate.DateType;
import excel.annotation.ExcelField;

import java.util.Date;

/**
 * 日志
 */
public class LogModel {

    @ExcelField(columnName = "访问人", order = 1)
    public String requestName;

    @ExcelField(columnName = "访问IP", order = 2)
    public String requestIP;

    @ExcelField(columnName = "访问时间", order = 3, dateType = DateType.Year_Month_Day_Hour_Minute_Second)
    public Date requestDate;

    @ExcelField(columnName = "访问接口URI", order = 4)
    public String interfaceURI;

    @ExcelField(columnName = "访问接口描述", order = 5)
    public String interfaceDescribe;

    @ExcelField(columnName = "访问接口所带条件", order = 6)
    public String interfaceCondition;

    @ExcelField(columnName = "访问接口URL", order = 7)
    public String interfaceURL;

    @ExcelField(columnName = "执行语句", order = 8)
    public String SQL;


}
