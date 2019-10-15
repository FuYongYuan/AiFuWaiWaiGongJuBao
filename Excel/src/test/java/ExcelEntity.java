import dispose.DateDispose;
import enumerate.DateType;
import excel.annotation.ExcelField;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Excel导出导入
 */
public class ExcelEntity {

    public ExcelEntity() {
    }

    public ExcelEntity(String sT, int iT, Date DT, double dT, BigDecimal bDT, boolean bT) {
        this.sT = sT;
        this.iT = iT;
        this.DT = DT;
        this.dT = dT;
        this.bDT = bDT;
        this.bT = bT;
    }

    @ExcelField(columnName = "字符串", order = 3, rowspan = true, columnWidth = 20)
    public String sT;

    @ExcelField(columnName = "整型", order = 1, rowspan = true)
    public Integer iT;

    @ExcelField(columnName = "日期", order = 5, dateType = DateType.Year_Month_Day, columnWidth = 20, rowspan = true, rowspanAlignOrder = 3)
    public Date DT;

    @ExcelField(columnName = "小数点", order = 4, decimalAfterDigit = 5, isMoney = true, columnWidth = 30)
    public Double dT;

    @ExcelField(columnName = "钱", order = 2, isMoney = true, horizontalAlignment = HorizontalAlignment.CENTER, columnWidth = 40,rowspan = true)
    public BigDecimal bDT;

    @ExcelField(columnName = "是否", order = 6, horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER, rowspan = true)
    public Boolean bT;


    public String sT2;
    public int iT2;
    public Date dT2;
    public BigDecimal bBD2;
    public double dD2;

    @Override
    public String toString() {
        return "ExcelEntity{" +
                "sT='" + sT + '\'' +
                ", iT=" + iT +
                ", DT=" + DateDispose.formatting_DateToString(DT) +
                ", dT=" + dT +
                ", bDT=" + bDT +
                ", bT=" + bT +
//                ", sT2='" + sT2 + '\'' +
//                ", iT2=" + iT2 +
//                ", dT2=" + dT2 +
//                ", bBD2=" + bBD2 +
//                ", dD2=" + dD2 +
                '}';
    }
}
