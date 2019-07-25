import dispose.DateDispose;
import enumerate.DateType;

public class Test {
    public static void main(String[] args) {

        System.out.println(
                DateDispose.dateDiff(
                        DateDispose.formatting_Date("2018-06-11 00:00:00", DateType.Year_Month_Day_Hour_Minute_Second),
                        DateDispose.formatting_Date("2018-12-11 23:59:59", DateType.Year_Month_Day_Hour_Minute_Second),
                        DateType.Day
                ));
    }
}
