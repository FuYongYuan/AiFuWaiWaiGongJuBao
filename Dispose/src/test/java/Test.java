import dispose.StockCalculation;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        StockCalculation.getFlat(
                new BigDecimal("16100"),
                new BigDecimal("9.27"),
                new BigDecimal("7")
        );
    }

}
