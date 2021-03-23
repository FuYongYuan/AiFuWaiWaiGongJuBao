import enumerate.DateType;
import excel.annotation.ExcelField;

import java.util.Date;

public class TestEntity {
    @ExcelField(columnName = "日期", order = 1, dateType = DateType.Year_Month_Day)
    public Date date1;

    @ExcelField(columnName = "时间", order = 2, dateType = DateType.Year_Month_Day_Hour_Minute_Second)
    public Date date2;
}
