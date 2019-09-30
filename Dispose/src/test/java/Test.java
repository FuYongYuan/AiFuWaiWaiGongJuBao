import dispose.DateDispose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    private static void test(Map<String,Object> map) {
        Map<String,Object> map1= new HashMap<>();
        map1.put("A","1");
        map1.put("B","2");
        map.putAll(map1);
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("A","2");
        map.put("B","1");

        System.out.println(map.get("A"));
        System.out.println(map.get("B"));

        test(map);

        System.out.println(map.get("A"));
        System.out.println(map.get("B"));


//        System.out.println(DateDispose.formatting_DateToString(DateDispose.formatting_StringToDate("2013-12-12 23:23:23.112324233")));

//        String s = "UPDATE BP3_BANK_ACCOUNT_TX_CONTROL A SET A.POLLING_SCHEDULE='{[0900,2330],900}',A.POLLING_SCHEDULE='{[0900,2330],900}',A.POLLING_SCHEDULE='{[0900,2330],900}' WHERE a.ACCOUNT_ID IN (1571,1572) AND A.BTX_CODE IN ('QBA','QRC')";
//
//        System.out.println(s.substring(s.indexOf("SET"), s.indexOf("WHERE")));
//
//        System.out.println(s.substring(s.indexOf("WHERE")));
//
//        s = "update gdt_contract_lmsconnect set OCCUPY_QUATO=0 where contractid_lms in (3978,3997,3979,3644,3859,3643,2681,3861,2551,3645,2513,2540,2598.3646,3862,2621,3647,3860,3648)";
//
//        System.out.println(s.substring(s.indexOf("SET"), s.indexOf("WHERE")));
//
//        System.out.println(s.substring(s.indexOf("WHERE")));
//
//        s = "delete from bp3_bank_account_record a where a.record_id in (select a.record_id from bp3_bank_account_record a  WHERE not exists （select * from aims_account_record b where b.bp_id = a.record_id） and a.record_date = to_date ('2019-09-09','yyyy-mm-dd'))";
//
//
//        System.out.println(s.substring(s.indexOf("WHERE")));

//        String s = "UPDATE BP3_BANK_ACCOUNT_TX_CONTROL A set A.POLLING_SCHEDULE='{[0900,2330],900}',A.POLLING_SCHEDULE='{[0900,2330],900}',A.POLLING_SCHEDULE='{[0900,2330],900}' where a.ACCOUNT_ID IN (1571,1572) AND A.BTX_CODE IN ('QBA','QRC');" +
//                "update gdt_contract_lmsconnect set OCCUPY_QUATO=0 where contractid_lms in (3978,3997,3979,3644,3859,3643,2681,3861,2551,3645,2513,2540,2598.3646,3862,2621,3647,3860,3648);" +
//                "delete from bp3_bank_account_record a where a.record_id in (select a.record_id from bp3_bank_account_record a  WHERE not exists （select * from aims_account_record b where b.bp_id = a.record_id） and a.record_date = to_date ('2019-09-09','yyyy-mm-dd'));" +
//                "UPDATE SYS_DATA_REPAIR SET STATE = 4 WHERE SYS_ID = 1;";
//
//        getBack(s);

//        System.out.println(
//                DateDispose.dateDiff(
//                        "2019-09-25 10:24:00",
//                        "2019-09-24 16:30:12",
//                        DateType.Year_Month_Day_Hour_Minute_Second,
//                        DateType.Minute
//                ));
//
//        System.out.println(
//                DateDispose.dateDiff(
//                        "2019-09-25 10:24:00",
//                        "2019-09-24 16:30:12",
//                        DateType.Year_Month_Day_Hour_Minute_Second,
//                        DateType.Hour
//                ));


//        String i = "1";
//        test(i);
//        System.out.println(i);

//
//        for (int i = 0; i < 10; i++) {
//            try {
//                if (i % 2 == 0) {
//                    throw new RuntimeException("" + i);
//                }
//            } catch (RuntimeException e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println(1111);
//
//        try {
//            for (int i = 0; i < 10; i++) {
//
//                if (i % 2 == 0) {
//                    throw new Exception("" + i);
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(1111);


//        System.out.println(Double.parseDouble(""));

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


    public static String getBack(String sqlContent) {
        String back = "";
        String[] sqls = sqlContent.split(";");
        for (String sql : sqls) {
            if (sql != null && !"".equals(sql)) {
                if (
                        sql.contains("UPDATE ") || sql.contains("update ") ||
                                sql.contains("updatE ") || sql.contains("updaTe ") ||
                                sql.contains("updaTE ") || sql.contains("updAte ") ||
                                sql.contains("updAtE ") || sql.contains("updATe ") ||
                                sql.contains("updATE ") || sql.contains("uPdate ") ||
                                sql.contains("uPdatE ") || sql.contains("uPdaTe ") ||
                                sql.contains("uPdaTE ") || sql.contains("uPdAte ") ||
                                sql.contains("uPdAtE ") || sql.contains("uPdATe ") ||
                                sql.contains("uPdATE ") || sql.contains("uPDate ") ||
                                sql.contains("uPDatE ") || sql.contains("uPDaTe ") ||
                                sql.contains("uPDaTE ") || sql.contains("uPDAte ") ||
                                sql.contains("uPDAtE ") || sql.contains("uPDATe ") ||
                                sql.contains("uPDATE ") || sql.contains("Update ") ||
                                sql.contains("UpdatE ") || sql.contains("UpdaTe ") ||
                                sql.contains("UpdaTE ") || sql.contains("UpdATe ") ||
                                sql.contains("UpdATE ") || sql.contains("UpDate ") ||
                                sql.contains("UpDatE ") || sql.contains("UpDaTe ") ||
                                sql.contains("UpDaTE ") || sql.contains("UpDAte ") ||
                                sql.contains("UpDAtE ") || sql.contains("UpDATe ") ||
                                sql.contains("UpDATE ") || sql.contains("UPdate ") ||
                                sql.contains("UPdatE ") || sql.contains("UPdaTe ") ||
                                sql.contains("UPdaTE ") || sql.contains("UPdAte ") ||
                                sql.contains("UPdAtE ") || sql.contains("UPdATe ") ||
                                sql.contains("UPdATE ") || sql.contains("UPDate ") ||
                                sql.contains("UPDatE ") || sql.contains("UPDaTe ") ||
                                sql.contains("UPDaTE ") || sql.contains("UPDAte ") ||
                                sql.contains("UPDAtE ") || sql.contains("UPDATe ")
                ) {
                    sql = update(sql);
                    sql = set(sql);
                    sql = where(sql);
                    String[] sets = sql.substring(sql.indexOf("SET ") + 4, sql.indexOf(" WHERE ")).split(",");

                    List<String> setName = new ArrayList<>();
                    for (String set : sets) {
                        if (set.contains("=")) {
                            String sn = set.split("=")[0];
                            sn = sn.substring(sn.indexOf(".") + 1);
                            setName.add(sn.trim());
                        }
                    }

                    setName.forEach(System.out::println);

                    String tabel = sql.substring(sql.indexOf("UPDATE ") + 7, sql.indexOf(" SET"));
                    if (tabel.contains(" ")) {
                        tabel = tabel.split(" ")[0];
                    }
                    System.out.println(tabel);

                    System.out.println(sql.substring(sql.indexOf("WHERE ") + 6));

                } else if (
                        sql.contains("DELETE ") || sql.contains("delete ") ||
                                sql.contains("deletE ") || sql.contains("deleTe ") ||
                                sql.contains("deleTE ") || sql.contains("delEte ") ||
                                sql.contains("delEtE ") || sql.contains("delETe ") ||
                                sql.contains("delETE ") || sql.contains("dElete ") ||
                                sql.contains("dEletE ") || sql.contains("dEleTe ") ||
                                sql.contains("dEleTE ") || sql.contains("dElEte ") ||
                                sql.contains("dElEtE ") || sql.contains("dElETe ") ||
                                sql.contains("dElETE ") || sql.contains("dELete ") ||
                                sql.contains("dELetE ") || sql.contains("dELeTe ") ||
                                sql.contains("dELeTE ") || sql.contains("dELEte ") ||
                                sql.contains("dELEtE ") || sql.contains("dELETe ") ||
                                sql.contains("dELETE ") || sql.contains("Delete ") ||
                                sql.contains("DeletE ") || sql.contains("DeleTe ") ||
                                sql.contains("DeleTE ") || sql.contains("DelETe ") ||
                                sql.contains("DelETE ") || sql.contains("DeLete ") ||
                                sql.contains("DeLetE ") || sql.contains("DeLeTe ") ||
                                sql.contains("DeLeTE ") || sql.contains("DeLEte ") ||
                                sql.contains("DeLEtE ") || sql.contains("DeLETe ") ||
                                sql.contains("DeLETE ") || sql.contains("DElete ") ||
                                sql.contains("DEletE ") || sql.contains("DEleTe ") ||
                                sql.contains("DEleTE ") || sql.contains("DElEte ") ||
                                sql.contains("DElEtE ") || sql.contains("DElETe ") ||
                                sql.contains("DElETE ") || sql.contains("DELete ") ||
                                sql.contains("DELetE ") || sql.contains("DELeTe ") ||
                                sql.contains("DELeTE ") || sql.contains("DELEte ") ||
                                sql.contains("DELEtE ") || sql.contains("DELETe ")
                ) {
                    sql = delete(sql);
                    sql = from(sql);
                    sql = where(sql);
                    System.out.println(sql.substring(sql.indexOf("WHERE ") + 6));
                    String tabel = sql.substring(sql.indexOf("DELETE FROM ") + 12, sql.indexOf(" WHERE"));
                    if (tabel.contains(" ")) {
                        tabel = tabel.split(" ")[0];
                    }
                    System.out.println(tabel);


                }
            }
        }
        return null;
    }

    public static String update(String sql) {
        if (sql.contains("UPDATE ")) {
            sql = sql.replaceAll("UPDATE ", "UPDATE ");
        } else if (sql.contains("update ")) {
            sql = sql.replaceAll("update ", "UPDATE ");
        } else if (sql.contains("updatE ")) {
            sql = sql.replaceAll("updatE ", "UPDATE ");
        } else if (sql.contains("updaTe ")) {
            sql = sql.replaceAll("updaTe ", "UPDATE ");
        } else if (sql.contains("updaTE ")) {
            sql = sql.replaceAll("updaTE ", "UPDATE ");
        } else if (sql.contains("updAte ")) {
            sql = sql.replaceAll("updAte ", "UPDATE ");
        } else if (sql.contains("updAtE ")) {
            sql = sql.replaceAll("updAtE ", "UPDATE ");
        } else if (sql.contains("updATe ")) {
            sql = sql.replaceAll("updATe ", "UPDATE ");
        } else if (sql.contains("updATE ")) {
            sql = sql.replaceAll("updATE ", "UPDATE ");
        } else if (sql.contains("uPdate ")) {
            sql = sql.replaceAll("uPdate ", "UPDATE ");
        } else if (sql.contains("uPdatE ")) {
            sql = sql.replaceAll("uPdatE ", "UPDATE ");
        } else if (sql.contains("uPdaTe ")) {
            sql = sql.replaceAll("uPdaTe ", "UPDATE ");
        } else if (sql.contains("uPdaTE ")) {
            sql = sql.replaceAll("uPdaTE ", "UPDATE ");
        } else if (sql.contains("uPdAte ")) {
            sql = sql.replaceAll("uPdAte ", "UPDATE ");
        } else if (sql.contains("uPdAtE ")) {
            sql = sql.replaceAll("uPdAtE ", "UPDATE ");
        } else if (sql.contains("uPdATe ")) {
            sql = sql.replaceAll("uPdATe ", "UPDATE ");
        } else if (sql.contains("uPdATE ")) {
            sql = sql.replaceAll("uPdATE ", "UPDATE ");
        } else if (sql.contains("uPDate ")) {
            sql = sql.replaceAll("uPDate ", "UPDATE ");
        } else if (sql.contains("uPDatE ")) {
            sql = sql.replaceAll("uPDatE ", "UPDATE ");
        } else if (sql.contains("uPDaTe ")) {
            sql = sql.replaceAll("uPDaTe ", "UPDATE ");
        } else if (sql.contains("uPDaTE ")) {
            sql = sql.replaceAll("uPDaTE ", "UPDATE ");
        } else if (sql.contains("uPDAte ")) {
            sql = sql.replaceAll("uPDAte ", "UPDATE ");
        } else if (sql.contains("uPDAtE ")) {
            sql = sql.replaceAll("uPDAtE ", "UPDATE ");
        } else if (sql.contains("uPDATe ")) {
            sql = sql.replaceAll("uPDATe ", "UPDATE ");
        } else if (sql.contains("uPDATE ")) {
            sql = sql.replaceAll("uPDATE ", "UPDATE ");
        } else if (sql.contains("Update ")) {
            sql = sql.replaceAll("Update ", "UPDATE ");
        } else if (sql.contains("UpdatE ")) {
            sql = sql.replaceAll("UpdatE ", "UPDATE ");
        } else if (sql.contains("UpdaTe ")) {
            sql = sql.replaceAll("UpdaTe ", "UPDATE ");
        } else if (sql.contains("UpdaTE ")) {
            sql = sql.replaceAll("UpdaTE ", "UPDATE ");
        } else if (sql.contains("UpdATe ")) {
            sql = sql.replaceAll("UpdATe ", "UPDATE ");
        } else if (sql.contains("UpdATE ")) {
            sql = sql.replaceAll("UpdATE ", "UPDATE ");
        } else if (sql.contains("UpDate ")) {
            sql = sql.replaceAll("UpDate ", "UPDATE ");
        } else if (sql.contains("UpDatE ")) {
            sql = sql.replaceAll("UpDatE ", "UPDATE ");
        } else if (sql.contains("UpDaTe ")) {
            sql = sql.replaceAll("UpDaTe ", "UPDATE ");
        } else if (sql.contains("UpDaTE ")) {
            sql = sql.replaceAll("UpDaTE ", "UPDATE ");
        } else if (sql.contains("UpDAte ")) {
            sql = sql.replaceAll("UpDAte ", "UPDATE ");
        } else if (sql.contains("UpDAtE ")) {
            sql = sql.replaceAll("UpDAtE ", "UPDATE ");
        } else if (sql.contains("UpDATe ")) {
            sql = sql.replaceAll("UpDATe ", "UPDATE ");
        } else if (sql.contains("UpDATE ")) {
            sql = sql.replaceAll("UpDATE ", "UPDATE ");
        } else if (sql.contains("UPdate ")) {
            sql = sql.replaceAll("UPdate ", "UPDATE ");
        } else if (sql.contains("UPdatE ")) {
            sql = sql.replaceAll("UPdatE ", "UPDATE ");
        } else if (sql.contains("UPdaTe ")) {
            sql = sql.replaceAll("UPdaTe ", "UPDATE ");
        } else if (sql.contains("UPdaTE ")) {
            sql = sql.replaceAll("UPdaTE ", "UPDATE ");
        } else if (sql.contains("UPdAte ")) {
            sql = sql.replaceAll("UPdAte ", "UPDATE ");
        } else if (sql.contains("UPdAtE ")) {
            sql = sql.replaceAll("UPdAtE ", "UPDATE ");
        } else if (sql.contains("UPdATe ")) {
            sql = sql.replaceAll("UPdATe ", "UPDATE ");
        } else if (sql.contains("UPdATE ")) {
            sql = sql.replaceAll("UPdATE ", "UPDATE ");
        } else if (sql.contains("UPDate ")) {
            sql = sql.replaceAll("UPDate ", "UPDATE ");
        } else if (sql.contains("UPDatE ")) {
            sql = sql.replaceAll("UPDatE ", "UPDATE ");
        } else if (sql.contains("UPDaTe ")) {
            sql = sql.replaceAll("UPDaTe ", "UPDATE ");
        } else if (sql.contains("UPDaTE ")) {
            sql = sql.replaceAll("UPDaTE ", "UPDATE ");
        } else if (sql.contains("UPDAte ")) {
            sql = sql.replaceAll("UPDAte ", "UPDATE ");
        } else if (sql.contains("UPDAtE ")) {
            sql = sql.replaceAll("UPDAtE ", "UPDATE ");
        } else if (sql.contains("UPDATe ")) {
            sql = sql.replaceAll("UPDATe ", "UPDATE ");
        }
        return sql;
    }

    public static String delete(String sql) {
        if (sql.contains("DELETE ")) {
            sql = sql.replaceAll("DELETE ", "DELETE ");
        } else if (sql.contains("delete ")) {
            sql = sql.replaceAll("delete ", "DELETE ");
        } else if (sql.contains("deletE ")) {
            sql = sql.replaceAll("deletE ", "DELETE ");
        } else if (sql.contains("deleTe ")) {
            sql = sql.replaceAll("deleTe ", "DELETE ");
        } else if (sql.contains("deleTE ")) {
            sql = sql.replaceAll("deleTE ", "DELETE ");
        } else if (sql.contains("delEte ")) {
            sql = sql.replaceAll("delEte ", "DELETE ");
        } else if (sql.contains("delEtE ")) {
            sql = sql.replaceAll("delEtE ", "DELETE ");
        } else if (sql.contains("delETe ")) {
            sql = sql.replaceAll("delETe ", "DELETE ");
        } else if (sql.contains("delETE ")) {
            sql = sql.replaceAll("delETE ", "DELETE ");
        } else if (sql.contains("dElete ")) {
            sql = sql.replaceAll("dElete ", "DELETE ");
        } else if (sql.contains("dEletE ")) {
            sql = sql.replaceAll("dEletE ", "DELETE ");
        } else if (sql.contains("dEleTe ")) {
            sql = sql.replaceAll("dEleTe ", "DELETE ");
        } else if (sql.contains("dEleTE ")) {
            sql = sql.replaceAll("dEleTE ", "DELETE ");
        } else if (sql.contains("dElEte ")) {
            sql = sql.replaceAll("dElEte ", "DELETE ");
        } else if (sql.contains("dElEtE ")) {
            sql = sql.replaceAll("dElEtE ", "DELETE ");
        } else if (sql.contains("dElETe ")) {
            sql = sql.replaceAll("dElETe ", "DELETE ");
        } else if (sql.contains("dElETE ")) {
            sql = sql.replaceAll("dElETE ", "DELETE ");
        } else if (sql.contains("dELete ")) {
            sql = sql.replaceAll("dELete ", "DELETE ");
        } else if (sql.contains("dELetE ")) {
            sql = sql.replaceAll("dELetE ", "DELETE ");
        } else if (sql.contains("dELeTe ")) {
            sql = sql.replaceAll("dELeTe ", "DELETE ");
        } else if (sql.contains("dELeTE ")) {
            sql = sql.replaceAll("dELeTE ", "DELETE ");
        } else if (sql.contains("dELEte ")) {
            sql = sql.replaceAll("dELEte ", "DELETE ");
        } else if (sql.contains("dELEtE ")) {
            sql = sql.replaceAll("dELEtE ", "DELETE ");
        } else if (sql.contains("dELETe ")) {
            sql = sql.replaceAll("dELETe ", "DELETE ");
        } else if (sql.contains("dELETE ")) {
            sql = sql.replaceAll("dELETE ", "DELETE ");
        } else if (sql.contains("Delete ")) {
            sql = sql.replaceAll("Delete ", "DELETE ");
        } else if (sql.contains("DeletE ")) {
            sql = sql.replaceAll("DeletE ", "DELETE ");
        } else if (sql.contains("DeleTe ")) {
            sql = sql.replaceAll("DeleTe ", "DELETE ");
        } else if (sql.contains("DeleTE ")) {
            sql = sql.replaceAll("DeleTE ", "DELETE ");
        } else if (sql.contains("DelETe ")) {
            sql = sql.replaceAll("DelETe ", "DELETE ");
        } else if (sql.contains("DelETE ")) {
            sql = sql.replaceAll("DelETE ", "DELETE ");
        } else if (sql.contains("DeLete ")) {
            sql = sql.replaceAll("DeLete ", "DELETE ");
        } else if (sql.contains("DeLetE ")) {
            sql = sql.replaceAll("DeLetE ", "DELETE ");
        } else if (sql.contains("DeLeTe ")) {
            sql = sql.replaceAll("DeLeTe ", "DELETE ");
        } else if (sql.contains("DeLeTE ")) {
            sql = sql.replaceAll("DeLeTE ", "DELETE ");
        } else if (sql.contains("DeLEte ")) {
            sql = sql.replaceAll("DeLEte ", "DELETE ");
        } else if (sql.contains("DeLEtE ")) {
            sql = sql.replaceAll("DeLEtE ", "DELETE ");
        } else if (sql.contains("DeLETe ")) {
            sql = sql.replaceAll("DeLETe ", "DELETE ");
        } else if (sql.contains("DeLETE ")) {
            sql = sql.replaceAll("DeLETE ", "DELETE ");
        } else if (sql.contains("DElete ")) {
            sql = sql.replaceAll("DElete ", "DELETE ");
        } else if (sql.contains("DEletE ")) {
            sql = sql.replaceAll("DEletE ", "DELETE ");
        } else if (sql.contains("DEleTe ")) {
            sql = sql.replaceAll("DEleTe ", "DELETE ");
        } else if (sql.contains("DEleTE ")) {
            sql = sql.replaceAll("DEleTE ", "DELETE ");
        } else if (sql.contains("DElEte ")) {
            sql = sql.replaceAll("DElEte ", "DELETE ");
        } else if (sql.contains("DElEtE ")) {
            sql = sql.replaceAll("DElEtE ", "DELETE ");
        } else if (sql.contains("DElETe ")) {
            sql = sql.replaceAll("DElETe ", "DELETE ");
        } else if (sql.contains("DElETE ")) {
            sql = sql.replaceAll("DElETE ", "DELETE ");
        } else if (sql.contains("DELete ")) {
            sql = sql.replaceAll("DELete ", "DELETE ");
        } else if (sql.contains("DELetE ")) {
            sql = sql.replaceAll("DELetE ", "DELETE ");
        } else if (sql.contains("DELeTe ")) {
            sql = sql.replaceAll("DELeTe ", "DELETE ");
        } else if (sql.contains("DELeTE ")) {
            sql = sql.replaceAll("DELeTE ", "DELETE ");
        } else if (sql.contains("DELEte ")) {
            sql = sql.replaceAll("DELEte ", "DELETE ");
        } else if (sql.contains("DELEtE ")) {
            sql = sql.replaceAll("DELEtE ", "DELETE ");
        } else if (sql.contains("DELETe ")) {
            sql = sql.replaceAll("DELETe ", "DELETE ");
        }
        return sql;
    }

    public static String set(String sql) {
        if (sql.contains(" seT ")) {
            sql = sql.replaceAll(" seT ", " SET ");
        } else if (sql.contains(" set ")) {
            sql = sql.replaceAll(" set ", " SET ");
        } else if (sql.contains(" sET ")) {
            sql = sql.replaceAll(" sET ", " SET ");
        } else if (sql.contains(" sEt ")) {
            sql = sql.replaceAll(" sEt ", " SET ");
        } else if (sql.contains(" SET ")) {
            sql = sql.replaceAll(" SET ", " SET ");
        } else if (sql.contains(" SEt ")) {
            sql = sql.replaceAll(" SEt ", " SET ");
        } else if (sql.contains(" SeT ")) {
            sql = sql.replaceAll(" SeT ", " SET ");
        } else if (sql.contains(" Set ")) {
            sql = sql.replaceAll(" Set ", " SET ");
        }
        return sql;
    }

    public static String where(String sql) {
        if (sql.contains(" where ")) {
            sql = sql.replaceAll(" where ", " WHERE ");
        } else if (sql.contains(" wherE ")) {
            sql = sql.replaceAll(" wherE ", " WHERE ");
        } else if (sql.contains(" wheRe ")) {
            sql = sql.replaceAll(" wheRe ", " WHERE ");
        } else if (sql.contains(" wheRE ")) {
            sql = sql.replaceAll(" wheRE ", " WHERE ");
        } else if (sql.contains(" whEre ")) {
            sql = sql.replaceAll(" whEre ", " WHERE ");
        } else if (sql.contains(" whErE ")) {
            sql = sql.replaceAll(" whErE ", " WHERE ");
        } else if (sql.contains(" whERe ")) {
            sql = sql.replaceAll(" whERe ", " WHERE ");
        } else if (sql.contains(" whERE ")) {
            sql = sql.replaceAll(" whERE ", " WHERE ");
        } else if (sql.contains(" wHere ")) {
            sql = sql.replaceAll(" wHere ", " WHERE ");
        } else if (sql.contains(" wHerE ")) {
            sql = sql.replaceAll(" wHerE ", " WHERE ");
        } else if (sql.contains(" wHeRe ")) {
            sql = sql.replaceAll(" wHeRe ", " WHERE ");
        } else if (sql.contains(" wHeRE ")) {
            sql = sql.replaceAll(" wHeRE ", " WHERE ");
        } else if (sql.contains(" wHEre ")) {
            sql = sql.replaceAll(" wHEre ", " WHERE ");
        } else if (sql.contains(" wHErE ")) {
            sql = sql.replaceAll(" wHErE ", " WHERE ");
        } else if (sql.contains(" wHERe ")) {
            sql = sql.replaceAll(" wHERe ", " WHERE ");
        } else if (sql.contains(" wHERE ")) {
            sql = sql.replaceAll(" wHERE ", " WHERE ");
        } else if (sql.contains(" Where ")) {
            sql = sql.replaceAll(" Where ", " WHERE ");
        } else if (sql.contains(" WherE ")) {
            sql = sql.replaceAll(" WherE ", " WHERE ");
        } else if (sql.contains(" WheRe ")) {
            sql = sql.replaceAll(" WheRe ", " WHERE ");
        } else if (sql.contains(" WheRE ")) {
            sql = sql.replaceAll(" WheRE ", " WHERE ");
        } else if (sql.contains(" WhEre ")) {
            sql = sql.replaceAll(" WhEre ", " WHERE ");
        } else if (sql.contains(" WhErE ")) {
            sql = sql.replaceAll(" WhErE ", " WHERE ");
        } else if (sql.contains(" WhERE ")) {
            sql = sql.replaceAll(" WhERE ", " WHERE ");
        } else if (sql.contains(" WHERe ")) {
            sql = sql.replaceAll(" WHERe ", " WHERE ");
        } else if (sql.contains(" WHERE ")) {
            sql = sql.replaceAll(" WHERE ", " WHERE ");
        }
        return sql;
    }

    public static String from(String sql) {
        if (sql.contains(" FROM ")) {
            sql = sql.replaceAll(" FROM ", " FROM ");
        } else if (sql.contains(" from ")) {
            sql = sql.replaceAll(" from ", " FROM ");
        } else if (sql.contains(" froM ")) {
            sql = sql.replaceAll(" froM ", " FROM ");
        } else if (sql.contains(" frOm ")) {
            sql = sql.replaceAll(" frOm ", " FROM ");
        } else if (sql.contains(" frOM ")) {
            sql = sql.replaceAll(" frOM ", " FROM ");
        } else if (sql.contains(" fRom ")) {
            sql = sql.replaceAll(" fRom ", " FROM ");
        } else if (sql.contains(" fRoM ")) {
            sql = sql.replaceAll(" fRoM ", " FROM ");
        } else if (sql.contains(" fROM ")) {
            sql = sql.replaceAll(" fROM ", " FROM ");
        } else if (sql.contains(" From ")) {
            sql = sql.replaceAll(" From ", " FROM ");
        } else if (sql.contains(" FroM ")) {
            sql = sql.replaceAll(" FroM ", " FROM ");
        } else if (sql.contains(" FrOm ")) {
            sql = sql.replaceAll(" FrOm ", " FROM ");
        } else if (sql.contains(" FrOM ")) {
            sql = sql.replaceAll(" FrOM ", " FROM ");
        } else if (sql.contains(" FRom ")) {
            sql = sql.replaceAll(" FRom ", " FROM ");
        } else if (sql.contains(" FRoM ")) {
            sql = sql.replaceAll(" FRoM ", " FROM ");
        } else if (sql.contains(" FROm ")) {
            sql = sql.replaceAll(" FROm ", " FROM ");
        }
        return sql;
    }

}
