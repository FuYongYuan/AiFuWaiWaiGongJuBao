package demo;

import enumerate.DateType;
import excel.annotation.ExcelField;

import java.math.BigDecimal;
import java.util.Date;

public class Jsb {
    @ExcelField(columnName = "小组", order = 1, rowspan = true)
    private String xz;
    @ExcelField(columnName = "姓名", order = 2, rowspan = true)
    private String xm;
    @ExcelField(columnName = "出生年月", order = 3, dateType = DateType.Year_Month_Day, columnWidth = 15)
    private Date csny;
    @ExcelField(columnName = "年龄", order = 4, decimalAfterDigit = 0)
    private int nl;
    @ExcelField(columnName = "性别", order = 5, decimalAfterDigit = 0, valueLimit = "xblist")
    private int xb;
    @ExcelField(columnName = "身高", order = 6, decimalAfterDigit = 1)
    private double sg;
    @ExcelField(columnName = "体重", order = 7, decimalAfterDigit = 1)
    private double tz;
    @ExcelField(columnName = "余额", order = 8, decimalAfterDigit = 2)
    private BigDecimal ye;

    public String getXz() {
        return xz;
    }

    public Jsb setXz(String xz) {
        this.xz = xz;
        return this;
    }

    public String getXm() {
        return xm;
    }

    public Jsb setXm(String xm) {
        this.xm = xm;
        return this;
    }

    public Date getCsny() {
        return csny;
    }

    public Jsb setCsny(Date csny) {
        this.csny = csny;
        return this;
    }

    public int getNl() {
        return nl;
    }

    public Jsb setNl(int nl) {
        this.nl = nl;
        return this;
    }

    public int getXb() {
        return xb;
    }

    public Jsb setXb(int xb) {
        this.xb = xb;
        return this;
    }

    public double getSg() {
        return sg;
    }

    public Jsb setSg(double sg) {
        this.sg = sg;
        return this;
    }

    public double getTz() {
        return tz;
    }

    public Jsb setTz(double tz) {
        this.tz = tz;
        return this;
    }

    public BigDecimal getYe() {
        return ye;
    }

    public Jsb setYe(BigDecimal ye) {
        this.ye = ye;
        return this;
    }

    @Override
    public String toString() {
        return "Jsb{" +
                "xz='" + xz + '\'' +
                ", xm='" + xm + '\'' +
                ", csny=" + csny +
                ", nl=" + nl +
                ", xb=" + xb +
                ", sg=" + sg +
                ", tz=" + tz +
                ", ye=" + ye +
                '}';
    }
}
