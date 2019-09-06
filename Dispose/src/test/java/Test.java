import dispose.DateDispose;
import enumerate.DateType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {

        System.out.println(Double.parseDouble(""));

//        String s = "CN=xnyan,OU=象屿物流,OU=象屿股份,O=XiangYuZiJi,C=CN";
//
//        s = s.replaceAll("CN=", "");
//
//        s = s.substring(0, s.indexOf(",OU=")).toUpperCase();
//
//        System.out.println(s);
//
//        System.out.println(s.contains("Y"));

//        Date totalStartDate = DateDispose.formatting_Date("2019-07-03", DateType.Year_Month_Day);
//        Date totalEndDate = DateDispose.formatting_Date("2019-07-01", DateType.Year_Month_Day);
//        if(DateDispose.compareDateSize(totalEndDate, totalStartDate) || DateDispose.compareDate(totalEndDate, totalStartDate)) {
//            System.out.println("1");
//        }else{
//            System.out.println("2");
//        }

//        Map<String, BigDecimal> m = new HashMap<>();
//        m.put("2019-04-01", new BigDecimal(4000));
//
//
//        Map<String, BigDecimal> map = new HashMap<>();
//        BigDecimal balance = new BigDecimal(0);
//        BigDecimal total = new BigDecimal(0);
//
//        Date start_Date = DateDispose.formatting_Date("2019-04-30 00:00:00", DateType.Year_Month_Day_Hour_Minute_Second);
//        Date end_Date = DateDispose.formatting_Date("2019-07-02 23:59:59", DateType.Year_Month_Day_Hour_Minute_Second);
//        Date totalStartDate;
//        totalStartDate = DateDispose.day_calculate_Date(start_Date, 1);
//
//        System.out.println(DateDispose.formatting_Date(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//        map.put("2019-05-01", new BigDecimal(2000));
//        System.out.println("---------------------------------------------------------------------");
//
//        for (Map.Entry<String, BigDecimal> set : m.entrySet()) {
//            if (DateDispose.compareDateSize(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)||
//                    DateDispose.compareDate(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)) {
//                balance = balance.add(set.getValue());
//            }
//        }
//
//        total = total.add(balance);
//
//
//        for (int i = 1; DateDispose.compareDateSize(totalStartDate, end_Date) || DateDispose.compareDate(totalStartDate, end_Date); i++, totalStartDate = DateDispose.day_calculate_Date(start_Date, i)) {
//            System.out.println(DateDispose.formatting_Date(totalStartDate, DateType.Year_Month_Day));
//            BigDecimal b = new BigDecimal(0);
//
//            for (Map.Entry<String, BigDecimal> set : map.entrySet()) {
//
//                if (DateDispose.compareDateSize(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                        || DateDispose.compareDate(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                ) {
//                    BigDecimal amount = set.getValue().multiply(new BigDecimal(-1));
//                    b = b.add(amount);
//                }
//
//                if(DateDispose.compareDate(DateDispose.formatting_Date("2019-07-01", DateType.Year_Month_Day), totalStartDate) ||
//                        DateDispose.compareDate(DateDispose.formatting_Date("2019-07-02", DateType.Year_Month_Day), totalStartDate)){
//                    b = balance.multiply(new BigDecimal(-1));
//                }
//            }
//            System.out.println(balance.add(b));
//            total = total.add(balance.add(b));
//        }
//
//        long bizDay = DateDispose.dateDiff(start_Date, end_Date, DateType.Day) + 1;
//
//        System.out.println(total.divide(new BigDecimal(bizDay), 2, BigDecimal.ROUND_HALF_UP));
//


//        Map<String, BigDecimal> m = new HashMap<>();
//        m.put("2019-04-01", new BigDecimal(4000));
//
//
//        Map<String, BigDecimal> map = new HashMap<>();
//        BigDecimal balance = new BigDecimal(0);
//        BigDecimal total = new BigDecimal(0);
//
//        Date start_Date = DateDispose.formatting_Date("2019-04-30", DateType.Year_Month_Day);
//        Date end_Date = DateDispose.formatting_Date("2019-07-02", DateType.Year_Month_Day);
//        Date totalStartDate = start_Date;
//
//        System.out.println(DateDispose.formatting_Date(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//
//        map.put("2019-05-01", new BigDecimal(2000));
//        System.out.println("---------------------------------------------------------------------");
//
//        for (Map.Entry<String, BigDecimal> set : m.entrySet()) {
//            if (DateDispose.compareDateSize(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)||
//                    DateDispose.compareDate(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)) {
//                balance = balance.add(set.getValue());
//            }
//        }
//
//        total = total.add(balance);
//        totalStartDate = DateDispose.day_calculate_Date(start_Date, 1);
//
//        System.out.println(DateDispose.formatting_Date(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//        System.out.println("---------------------------------------------------------------------");
//
//        for (int i = 1; DateDispose.compareDateSize(totalStartDate, end_Date) || DateDispose.compareDate(totalStartDate, end_Date); i++, totalStartDate = DateDispose.day_calculate_Date(start_Date, i)) {
//            System.out.println(DateDispose.formatting_Date(totalStartDate, DateType.Year_Month_Day));
//            BigDecimal b = new BigDecimal(0);
//
//            for (Map.Entry<String, BigDecimal> set : map.entrySet()) {
//
//                if (DateDispose.compareDateSize(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                        || DateDispose.compareDate(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                ) {
//                    BigDecimal amount = set.getValue().multiply(new BigDecimal(-1));
//                    b = b.add(amount);
//                }
//
//                if(DateDispose.compareDate(DateDispose.formatting_Date("2019-07-01", DateType.Year_Month_Day), totalStartDate) ||
//                        DateDispose.compareDate(DateDispose.formatting_Date("2019-07-02", DateType.Year_Month_Day), totalStartDate)){
//                    b = balance.multiply(new BigDecimal(-1));
//                }
//            }
//            System.out.println(balance.add(b));
//            total = total.add(balance.add(b));
//        }
//
//        Long bizDay = DateDispose.dateDiff(start_Date, end_Date, DateType.Day) + 1;
//
//        System.out.println(total.divide(new BigDecimal(bizDay), 2, BigDecimal.ROUND_HALF_UP));


//        Map<String, BigDecimal> m = new HashMap<>();
//        m.put("2019-02-01", new BigDecimal(5432));
//
//
//        Map<String, BigDecimal> map = new HashMap<>();
//        BigDecimal balance = new BigDecimal(0);
//        BigDecimal total = new BigDecimal(0);
//
//        Date start_Date = DateDispose.formatting_Date("2019-02-01", DateType.Year_Month_Day);
//        Date end_Date = DateDispose.formatting_Date("2019-07-01", DateType.Year_Month_Day);
//        Date totalStartDate = start_Date;
//
//        System.out.println(DateDispose.formatting_Date(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//
//        map.put("2019-03-01", new BigDecimal(2432));
//        map.put("2019-04-01", new BigDecimal(1000));
//        System.out.println("---------------------------------------------------------------------");
//
//        for (Map.Entry<String, BigDecimal> set : m.entrySet()) {
//            if (DateDispose.compareDateSize(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)||
//            DateDispose.compareDate(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)) {
//                balance = balance.add(set.getValue());
//            }
//        }
//
//        total = total.add(balance);
//        totalStartDate = DateDispose.day_calculate_Date(start_Date, 1);
//
//        System.out.println(DateDispose.formatting_Date(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//        System.out.println("---------------------------------------------------------------------");
//
//        for (int i = 1; DateDispose.compareDateSize(totalStartDate, end_Date) || DateDispose.compareDate(totalStartDate, end_Date); i++, totalStartDate = DateDispose.day_calculate_Date(start_Date, i)) {
//            System.out.println(DateDispose.formatting_Date(totalStartDate, DateType.Year_Month_Day));
//            BigDecimal b = new BigDecimal(0);
//
//            for (Map.Entry<String, BigDecimal> set : map.entrySet()) {
//
//                if (DateDispose.compareDateSize(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                        || DateDispose.compareDate(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                ) {
//                    BigDecimal amount = set.getValue().multiply(new BigDecimal(-1));
//                    b = b.add(amount);
//                }
//            }
//            System.out.println(balance.add(b));
//            total = total.add(balance.add(b));
//        }
//
//        Long bizDay = DateDispose.dateDiff(start_Date, end_Date, DateType.Day) + 1;
//
//        System.out.println(total.divide(new BigDecimal(bizDay), 2, BigDecimal.ROUND_HALF_UP));


//        Map<String, BigDecimal> map = new HashMap<>();
//        BigDecimal balance = new BigDecimal(2106000);
//        BigDecimal total = new BigDecimal(0);
//
//        total = total.add(balance);
//
//        Date start_Date = DateDispose.formatting_Date("2018-06-11", DateType.Year_Month_Day);
//        Date end_Date = DateDispose.formatting_Date("2018-12-11", DateType.Year_Month_Day);
//        Date totalStartDate = start_Date;
//        totalStartDate = DateDispose.day_calculate_Date(start_Date, 1);
//
//        System.out.println(DateDispose.formatting_Date(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//        map.put("2018-12-11", new BigDecimal(2106000));
//        System.out.println("---------------------------------------------------------------------");
//        for (int i = 1; DateDispose.compareDateSize(totalStartDate, end_Date) || DateDispose.compareDate(totalStartDate, end_Date); i++, totalStartDate = DateDispose.day_calculate_Date(start_Date, i)) {
//            System.out.println(DateDispose.formatting_Date(totalStartDate, DateType.Year_Month_Day));
//            BigDecimal b = new BigDecimal(0);
//            for (Map.Entry<String, BigDecimal> set : map.entrySet()) {
//                if (DateDispose.compareDateSize(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                        || DateDispose.compareDate(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                ) {
//                    BigDecimal amount = set.getValue().multiply(new BigDecimal(-1));
//                    b = b.add(amount);
//                }
//            }
//            System.out.println(balance.add(b));
//            total = total.add(balance.add(b));
//        }
//
//        Long bizDay = DateDispose.dateDiff(start_Date, end_Date, DateType.Day) + 1;
//
//        System.out.println(total.divide(new BigDecimal(bizDay), 2, BigDecimal.ROUND_HALF_UP));


//        Map<String, BigDecimal> map = new HashMap<>();
//        BigDecimal balance = new BigDecimal(123123123);
//        BigDecimal total = new BigDecimal(0);
//
//        total = total.add(balance);
//
//        Date start_Date = DateDispose.formatting_Date("2019-01-01", DateType.Year_Month_Day);
//        Date end_Date = DateDispose.formatting_Date("2019-01-02", DateType.Year_Month_Day);
//        Date totalStartDate = start_Date;
//        totalStartDate = DateDispose.day_calculate_Date(start_Date, 1);
//
//        System.out.println(DateDispose.formatting_Date(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formatting_Date(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//        System.out.println("---------------------------------------------------------------------");
//        for (int i = 1; DateDispose.compareDateSize(totalStartDate, end_Date) || DateDispose.compareDate(totalStartDate, end_Date); i++, totalStartDate = DateDispose.day_calculate_Date(start_Date, i)) {
//            System.out.println(DateDispose.formatting_Date(totalStartDate, DateType.Year_Month_Day));
//            BigDecimal b = new BigDecimal(0);
//            for (Map.Entry<String, BigDecimal> set : map.entrySet()) {
//                if (DateDispose.compareDateSize(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                        || DateDispose.compareDate(DateDispose.formatting_Date(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                ) {
//                    BigDecimal amount = set.getValue().multiply(new BigDecimal(-1));
//                    b = b.add(amount);
//                }
//            }
//            System.out.println(balance.add(b));
//            total = total.add(balance.add(b));
//        }
//
//        Long bizDay = DateDispose.dateDiff(start_Date, end_Date, DateType.Day) + 1;
//
//        System.out.println(total.divide(new BigDecimal(bizDay), 2, BigDecimal.ROUND_HALF_UP));

    }
}
