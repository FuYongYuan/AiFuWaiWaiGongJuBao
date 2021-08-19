import excel.annotation.ExcelField;

public class Test4 {
    @ExcelField(columnName = "REGION_ID", order = 1)
    private Integer region_id;
    @ExcelField(columnName = "资金系统更新的内码值", order = 2)
    private String code;

    public Integer getRegion_id() {
        return region_id;
    }

    public Test4 setRegion_id(Integer region_id) {
        this.region_id = region_id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Test4 setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String toString() {
        return "Test4{" +
                "region_id=" + region_id +
                ", code='" + code + '\'' +
                '}';
    }
}



