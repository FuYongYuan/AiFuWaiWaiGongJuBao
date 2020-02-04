package enumerate;

/**
 * 常用变量类型
 */
public enum CommonlyUsedType {
    Type_String("java.lang.String"),
    Type_int("int"),
    Type_Integer("java.lang.Integer"),
    Type_Util_Date("java.util.Date"),
    Type_double("double"),
    Type_Double("java.lang.Double"),
    Type_Boolean("java.lang.Boolean"),
    Type_boolean("boolean"),
    Type_BigDecimal("java.math.BigDecimal"),
    Type_Timestamp("java.sql.Timestamp"),
    Type_Sql_Date("java.sql.Date"),
    Type_BigInteger("java.math.BigInteger");


    private String value;

    CommonlyUsedType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
