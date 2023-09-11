package dispose;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 股票计算
 *
 * @author fyy
 */
public class StockCalculation {
    /**
     * 获取持平价
     *
     * @param hold    持有数
     * @param buy     买入价
     * @param current 现有价
     */
    public static void getFlat(BigDecimal hold, BigDecimal buy, BigDecimal current) {
        boolean b = true;
        for (BigDecimal i = new BigDecimal(1000); b; i = i.add(BigDecimal.valueOf(1000))) {
            BigDecimal number = hold.add(i);
            BigDecimal cost = current.multiply(i);
            BigDecimal average = (buy.multiply(hold).add(current.multiply(i))).divide(number, RoundingMode.HALF_UP);
            System.out.println(
                    "在购买：" + i + "股，" + "买共：" + number + "股，购入后总数:持有数：" + (number.divide(hold, 2, RoundingMode.HALF_UP)) + "倍，均价：" + average + "元，花共：" + cost + "," + TextDispose.arabNumberToChinese(cost.doubleValue()) + "元在买入"
            );
            if (average.compareTo(current) <= 0) {
                b = false;
            }
        }
    }
}
