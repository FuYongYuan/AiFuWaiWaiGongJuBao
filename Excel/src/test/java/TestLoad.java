import excel.annotation.ExcelField;


public class TestLoad {
    @ExcelField(columnName = "a",order = 1)
    private String a;
    @ExcelField(columnName = "b",order = 2)
    private String b;
    @ExcelField(columnName = "c",order = 3)
    private String c;

    public String getA() {
        return a;
    }

    public TestLoad setA(String a) {
        this.a = a;
        return this;
    }

    public String getB() {
        return b;
    }

    public TestLoad setB(String b) {
        this.b = b;
        return this;
    }

    public String getC() {
        return c;
    }

    public TestLoad setC(String c) {
        this.c = c;
        return this;
    }
}
