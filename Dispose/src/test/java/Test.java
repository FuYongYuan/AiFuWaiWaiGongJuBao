import dispose.TextDispose;
import enumerate.Format;

import java.util.*;

public class Test {
//    private static void test(Map<String,Object> map) {
//        Map<String,Object> map1= new HashMap<>();
//        map1.put("A","1");
//        map1.put("B","2");
//        map.putAll(map1);
//    }

    private static void geti(Integer i) {
        i = i + 1;
    }

//    private static void sql(String s) {
//        SQLStatementParser sqlStatementParser = new OracleStatementParser(s);
//        SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) sqlStatementParser.parseSelect();
//
//        SQLSelect sqlSelect = sqlSelectStatement.getSelect();
//        SQLSelectQuery sqlSelectQuery = sqlSelect.getQuery();
//        List<String> bs = new ArrayList<>();
//        if (sqlSelectQuery instanceof OracleSelectQueryBlock) {
//            OracleSelectQueryBlock sqlSelectQueryBlock = (OracleSelectQueryBlock) sqlSelectQuery;
//            OracleOutputVisitor where = new OracleOutputVisitor(new StringBuilder());
//            // 获取where 条件
//            if (sqlSelectQueryBlock.getWhere() != null) {
//                sqlSelectQueryBlock.getWhere().accept(where);
//                System.out.println("##########where###############");
//                System.out.println(where.getAppender());
//            }
//            // 获取表名
//            if (sqlSelectQueryBlock.getFrom() != null) {
//                System.out.println("############table_name##############");
//                OracleOutputVisitor tableName = new OracleOutputVisitor(new StringBuilder());
//                sqlSelectQueryBlock.getFrom().accept(tableName);
//                System.out.println(tableName.getAppender());
////                if (tableName.getTables() != null) {
////                    tableName.getTables().forEach(System.out::println);
////                }
//                if (tableName.getAppender().toString().contains("SELECT")) {
//                    String b = tableName.getAppender().toString();
//                    b = b.substring(1, b.length() - 1);
//                    b = b.replaceAll("\n", "");
//                    b = b.replaceAll("\t", " ");
//
//                    String[] bss = b.split("UNION ALL");
//
//                    for (String bsss : bss) {
//                        bs.add(bsss);
//                    }
//                }
//            }
//            //   获取查询字段
//            System.out.println("############查询字段##############");
//            System.out.println(sqlSelectQueryBlock.getSelectList());
//        }
//        System.out.println("--------------------------------------------------");
//        if (!bs.isEmpty()) {
//            for (String b : bs) {
//                sql(b);
//            }
//        }
//    }

    private static String replaceText(String context, String text, Integer wb) {
        String ct = context;
        if (context.contains(text)) {
            int jw = context.indexOf(text) + text.length();
            ct = context.substring(0, jw);
            ct = ct.replaceAll("(?i)" + text, "<span style='color: red'>" + text + "</span>");
            ct += replaceText(context.substring(jw), text, wb);
        }
        return ct;
    }

    public static void main(String[] args) {
//        String ceshi = "abbaabbaaabbaaaabbaaaaabbaaaaaa";
//
//
//        System.out.println(replaceText(ceshi, "bb", 0));

        System.out.println(TextDispose.toRemoverCamelCase("T_User_Action_X_X",Format.Upper, Format.NoFormat));

//        //集合
//        List<String> list = new ArrayList<>();
//        //数组
//        String[] s = new String[]{"", ""};
//        //Map
//        Map<String,String> map = new HashMap<>();
//
//        map.put("1","1");
//
//        System.out.println(map.get("1"));
//
//        System.out.println("结束");
//
//
//
//        for(String s1:list){
//            方法()
//            System.gc();
//        }
//
//
//
//
//        System.out.println(map.get("1"));
//


//        boolean i = false;
//        if(i = true){
//            System.out.println(1);
//        }
//


//        System.gc();
//        System.runFinalization();
//        System.gc();
//
//        System.out.println(map.get("1"));
//        System.out.println(s==null);
//        System.out.println(list==null);
//        do{
//        }while(true);


//        String s = "SELECT acc_type ,acc_ac ,MIN(pmt_desc) pmt_desc ,acc_ic ,acc_ac_desc ,SUM(acc_dr) acc_dr ,SUM(acc_cr) acc_cr ,ord_seq FROM (SELECT substr(flvv.meaning, 2, instr(flvv.meaning, '】') - 2) acc_type ,flvv.description acc_ac ,flvv.attribute9 pmt_desc ,flvv.attribute8 acc_ic ,substr(flvv.meaning, instr(flvv.meaning, '】') + 1) acc_ac_desc ,nvl(gjl.accounted_dr, 0) acc_dr ,nvl(gjl.accounted_cr, 0) acc_cr ,flvv.lookup_code ord_seq FROM gl_je_headers gjh ,gl_je_lines gjl ,gl_code_combinations gcc ,fnd_lookup_values_vl flvv WHERE gjh.je_header_id = gjl.je_header_id AND gjh.currency_code <> 'STAT' AND gjh.actual_flag = 'A' AND gcc.code_combination_id = gjl.code_combination_id AND flvv.lookup_type = 'XY_STAMPTAX_CHECK_ACCOUNT' AND flvv.enabled_flag = 'Y' AND trunc(SYSDATE) BETWEEN nvl(flvv.start_date_active, trunc(SYSDATE)) AND nvl(flvv.end_date_active, trunc(SYSDATE)) AND gcc.segment3 LIKE flvv.description || '%' AND (flvv.attribute8 IS NULL OR (flvv.attribute8 IS NOT NULL AND gcc.segment8 = flvv.attribute8)) AND gjh.default_effective_date BETWEEN to_date('${p_start_date}', 'YYYY-MM-DD') AND to_date('${p_end_date}', 'YYYY-MM-DD') ${if(len(p_org_code) == 0, \"\", \" AND gcc.segment1 IN ('\" + REPLACE(p_org_code, \",\", \"','\") + \"')\") } ${if(len(p_dept_code) == 0, \"\", \" AND gcc.segment2 IN ('\" + REPLACE(p_dept_code, \",\", \"','\") + \"')\") } UNION ALL SELECT substr(flvv.meaning, 2, instr(flvv.meaning, '】') - 2) acc_type ,flvv.description acc_ac ,flvv.attribute9 pmt_desc ,flvv.attribute8 acc_ic ,substr(flvv.meaning, instr(flvv.meaning, '】') + 1) acc_ac_desc ,0 acc_dr ,0 acc_cr ,flvv.lookup_code ord_seq FROM fnd_lookup_values_vl flvv WHERE flvv.lookup_type = 'XY_STAMPTAX_CHECK_ACCOUNT' AND flvv.enabled_flag = 'Y' AND trunc(SYSDATE) BETWEEN nvl(flvv.start_date_active, trunc(SYSDATE)) AND nvl(flvv.end_date_active, trunc(SYSDATE))) GROUP BY acc_type ,acc_ac ,acc_ac_desc ,acc_ic ,ord_seq ORDER BY to_number(ord_seq)";
//
//        s = s.replaceAll("\\$\\{.*?}", "");
//
//        String s2 = "SELECT substr(flvv.meaning, 2, instr(flvv.meaning, '】') - 2) AS acc_type\n" +
//                "\t\t, flvv.description AS acc_ac, flvv.attribute9 AS pmt_desc, flvv.attribute8 AS acc_ic\n" +
//                "\t\t, substr(flvv.meaning, instr(flvv.meaning, '】') + 1) AS acc_ac_desc\n" +
//                "\t\t, nvl(gjl.accounted_dr, 0) AS acc_dr\n" +
//                "\t\t, nvl(gjl.accounted_cr, 0) AS acc_cr, flvv.lookup_code AS ord_seq\n" +
//                "\tFROM gl_je_headers gjh, gl_je_lines gjl, gl_code_combinations gcc, fnd_lookup_values_vl flvv\n" +
//                "\tWHERE gjh.je_header_id = gjl.je_header_id\n" +
//                "\t\tAND gjh.currency_code <> 'STAT'\n" +
//                "\t\tAND gjh.actual_flag = 'A'\n" +
//                "\t\tAND gcc.code_combination_id = gjl.code_combination_id\n" +
//                "\t\tAND flvv.lookup_type = 'XY_STAMPTAX_CHECK_ACCOUNT'\n" +
//                "\t\tAND flvv.enabled_flag = 'Y'\n" +
//                "\t\tAND trunc(SYSDATE) BETWEEN nvl(flvv.start_date_active, trunc(SYSDATE)) AND nvl(flvv.end_date_active, trunc(SYSDATE))\n" +
//                "\t\tAND gcc.segment3 LIKE (flvv.description || '%')\n" +
//                "\t\tAND (flvv.attribute8 IS NULL\n" +
//                "\t\t\tOR (flvv.attribute8 IS NOT NULL\n" +
//                "\t\t\t\tAND gcc.segment8 = flvv.attribute8))\n" +
//                "\t\tAND gjh.default_effective_date BETWEEN to_date(NULL, 'YYYY-MM-DD') AND to_date(NULL, 'YYYY-MM-DD')\n";
//        s2 = s2.replaceAll("\n", "");
//        s2 = s2.replaceAll("\t", " ");
//
//        String s3 ="SELECT org_id, ORG_FULL_NAME,taxid_num,account_id,account_bank FROM (\n" +
//                "SELECT DISTINCT agtl.org_id --公司编码\n" +
//                "      ,org.attribute2 AS ORG_FULL_NAME --公司全称\n" +
//                "      ,org.attribute4--板块\n" +
//                "      ,agtl.fp_tax_registration_number  AS taxid_num --公司纳税登记号\n" +
//                "      ,flv.meaning AS account_id---账号\n" +
//                "      ,flv.description AS account_bank  ----开户银行\n" +
//                "  FROM ar_gta_tax_limits_all agtl --金税传送规则 金额及行数限制表\n" +
//                "      ,ar_gta_type_mappings  agtm --金税传送规则 关联事务处理表\n" +
//                "      ,hr_organization_units org\n" +
//                "      ,fnd_lookup_values          flv\n" +
//                " WHERE /*agtl.org_id = 95\n" +
//                "   AND*/ agtl.org_id = org.organization_id\n" +
//                "   AND agtl.limitation_id = agtm.limitation_id\n" +
//                "   AND flv.lookup_type = 'XY_GTA_BANK_PERSONALIZED'\n" +
//                "   AND flv.language = 'ZHS'\n" +
//                "   AND flv.tag = agtl.org_id\n" +
//                "   )\n" +
//                "  WHERE ORG_FULL_NAME ='${B5}'";
//
//        s3 = s3.replaceAll("--.*?\n", "");
//        s3 = s3.replaceAll("\n", "");
//        s3 = s3.replaceAll("\\s+", " ");
//        s3 = s3.replaceAll("\\/\\*.*?\\*\\/", "");
//        System.out.println(s3);

//        System.out.println(s);
//        System.out.println(s.charAt(1127));


//        String s1 = "SELECT t.org_code ,t.parent_code ,t.org_name FROM TABLE(nfnd_frfavpd_util_pkg.get_orgs('${p_user_name}', '${p_role_name}')) t";
//
////        SqlInjectDefense.get_in_table(s).forEach(System.out::println);
//
//        sql(s);


//        String i ="dasda";
//
//        if("String"==i){
//          System.out.println();
//        }
//
//        System.out.println(Double.parseDouble(String.valueOf(83629750.59)));
//        System.out.println(Double.parseDouble(new String("83629750.59")));
//        System.out.println(new BigDecimal("83629750.59"));
//        DecimalFormat df = new DecimalFormat();
//        df.setMaximumFractionDigits(3);//这里是小数位
//        System.out.println(df.format(new BigDecimal("83629750.59").doubleValue()));

//        String s=".keyID(\"test\")";
//
//        System.out.println(s.replaceAll("\\.keyID\\(\"test\"\\)","\\.keyID(\"test\" + i)"));

//        byte[] b=new byte[]{-128,127};
//
////        String s = "1";
////        int i = 1;
////        int x = 2;
////        int y = i + x;
//
//        try {
//            System.out.println(TextDispose.arabNumberToChineseRMB(5.5));
//            System.out.println(TextDispose.arabNumberToChineseRMB(5.55));
//            System.out.println(TextDispose.arabNumberToChineseRMB(5.555));
//            System.out.println(TextDispose.arabNumberToChineseRMB(5.50));
//            System.out.println(TextDispose.arabNumberToChineseRMB(5.505));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//
//        List<String> list = null;
//        if(list==null){
//
//        }
//        for (String s : list) {
//            if(s){
//
//            }
//        }

//        D d = D.newD();


//        B b=new B();
//
//        System.out.println(b.a);
//        System.out.println(b.b);

//        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("1");
//        list.add("1");
//        list.add("2");
//        list.add("3");
//
////        for(String s: list){
////            System.out.println(s);
////            if(s.equals("2")){
////                list.remove(s);
////            }
////        }
////
////        list.get(0);
//
//
//        Map<Integer, String> map = new HashMap<>();
//        map.put(0, "1");
//        map.put(1, "1");
//        map.put(2, "1");
//        map.put(3, "2");
//        map.put(4, "3");
//
//        map.get(0);
//
////        List<Map<String,Object>>
//
//
////        for (Map.Entry<Integer, String> m : map.entrySet()) {
////            System.out.println(m.getKey());
////            System.out.println(m.getValue());
////        }
//
//        for (Integer i : map.keySet()) {
//            System.out.println(i);
//            System.out.println(map.get(i));
//        }

//        list.forEach(System.out::println);

//        Integer[] i =new Integer[]{};

//        Integer x =1;
//        geti(x);
//        System.out.println(x);

//        String ds = "2019-10-31";
//
//        System.out.println(DateDispose.formattingDate(DateDispose.formattingDate(ds, DateType.Hour_Minute_Second),DateType.Hour_Minute_Second));


//        Map<String,Object> map = new HashMap<>();
//        map.put("A","2");
//        map.put("B","1");
//
//        System.out.println(map.get("A"));
//        System.out.println(map.get("B"));
//
//        test(map);
//
//        System.out.println(map.get("A"));
//        System.out.println(map.get("B"));


//        System.out.println(DateDispose.formattingDateToString(DateDispose.formattingStringToDate("2013-12-12 23:23:23.112324233")));

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

//        Date totalStartDate = DateDispose.formattingDate("2019-07-03", DateType.Year_Month_Day);
//        Date totalEndDate = DateDispose.formattingDate("2019-07-01", DateType.Year_Month_Day);
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
//        Date start_Date = DateDispose.formattingDate("2019-04-30 00:00:00", DateType.Year_Month_Day_Hour_Minute_Second);
//        Date end_Date = DateDispose.formattingDate("2019-07-02 23:59:59", DateType.Year_Month_Day_Hour_Minute_Second);
//        Date totalStartDate;
//        totalStartDate = DateDispose.dayCalculateDate(start_Date, 1);
//
//        System.out.println(DateDispose.formattingDate(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//        map.put("2019-05-01", new BigDecimal(2000));
//        System.out.println("---------------------------------------------------------------------");
//
//        for (Map.Entry<String, BigDecimal> set : m.entrySet()) {
//            if (DateDispose.compareDateSize(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)||
//                    DateDispose.compareDate(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)) {
//                balance = balance.add(set.getValue());
//            }
//        }
//
//        total = total.add(balance);
//
//
//        for (int i = 1; DateDispose.compareDateSize(totalStartDate, end_Date) || DateDispose.compareDate(totalStartDate, end_Date); i++, totalStartDate = DateDispose.dayCalculateDate(start_Date, i)) {
//            System.out.println(DateDispose.formattingDate(totalStartDate, DateType.Year_Month_Day));
//            BigDecimal b = new BigDecimal(0);
//
//            for (Map.Entry<String, BigDecimal> set : map.entrySet()) {
//
//                if (DateDispose.compareDateSize(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                        || DateDispose.compareDate(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                ) {
//                    BigDecimal amount = set.getValue().multiply(new BigDecimal(-1));
//                    b = b.add(amount);
//                }
//
//                if(DateDispose.compareDate(DateDispose.formattingDate("2019-07-01", DateType.Year_Month_Day), totalStartDate) ||
//                        DateDispose.compareDate(DateDispose.formattingDate("2019-07-02", DateType.Year_Month_Day), totalStartDate)){
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
//        Date start_Date = DateDispose.formattingDate("2019-04-30", DateType.Year_Month_Day);
//        Date end_Date = DateDispose.formattingDate("2019-07-02", DateType.Year_Month_Day);
//        Date totalStartDate = start_Date;
//
//        System.out.println(DateDispose.formattingDate(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//
//        map.put("2019-05-01", new BigDecimal(2000));
//        System.out.println("---------------------------------------------------------------------");
//
//        for (Map.Entry<String, BigDecimal> set : m.entrySet()) {
//            if (DateDispose.compareDateSize(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)||
//                    DateDispose.compareDate(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)) {
//                balance = balance.add(set.getValue());
//            }
//        }
//
//        total = total.add(balance);
//        totalStartDate = DateDispose.dayCalculateDate(start_Date, 1);
//
//        System.out.println(DateDispose.formattingDate(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//        System.out.println("---------------------------------------------------------------------");
//
//        for (int i = 1; DateDispose.compareDateSize(totalStartDate, end_Date) || DateDispose.compareDate(totalStartDate, end_Date); i++, totalStartDate = DateDispose.dayCalculateDate(start_Date, i)) {
//            System.out.println(DateDispose.formattingDate(totalStartDate, DateType.Year_Month_Day));
//            BigDecimal b = new BigDecimal(0);
//
//            for (Map.Entry<String, BigDecimal> set : map.entrySet()) {
//
//                if (DateDispose.compareDateSize(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                        || DateDispose.compareDate(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                ) {
//                    BigDecimal amount = set.getValue().multiply(new BigDecimal(-1));
//                    b = b.add(amount);
//                }
//
//                if(DateDispose.compareDate(DateDispose.formattingDate("2019-07-01", DateType.Year_Month_Day), totalStartDate) ||
//                        DateDispose.compareDate(DateDispose.formattingDate("2019-07-02", DateType.Year_Month_Day), totalStartDate)){
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
//        Date start_Date = DateDispose.formattingDate("2019-02-01", DateType.Year_Month_Day);
//        Date end_Date = DateDispose.formattingDate("2019-07-01", DateType.Year_Month_Day);
//        Date totalStartDate = start_Date;
//
//        System.out.println(DateDispose.formattingDate(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//
//        map.put("2019-03-01", new BigDecimal(2432));
//        map.put("2019-04-01", new BigDecimal(1000));
//        System.out.println("---------------------------------------------------------------------");
//
//        for (Map.Entry<String, BigDecimal> set : m.entrySet()) {
//            if (DateDispose.compareDateSize(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)||
//            DateDispose.compareDate(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)) {
//                balance = balance.add(set.getValue());
//            }
//        }
//
//        total = total.add(balance);
//        totalStartDate = DateDispose.dayCalculateDate(start_Date, 1);
//
//        System.out.println(DateDispose.formattingDate(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//        System.out.println("---------------------------------------------------------------------");
//
//        for (int i = 1; DateDispose.compareDateSize(totalStartDate, end_Date) || DateDispose.compareDate(totalStartDate, end_Date); i++, totalStartDate = DateDispose.dayCalculateDate(start_Date, i)) {
//            System.out.println(DateDispose.formattingDate(totalStartDate, DateType.Year_Month_Day));
//            BigDecimal b = new BigDecimal(0);
//
//            for (Map.Entry<String, BigDecimal> set : map.entrySet()) {
//
//                if (DateDispose.compareDateSize(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                        || DateDispose.compareDate(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)
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
//        Date start_Date = DateDispose.formattingDate("2018-06-11", DateType.Year_Month_Day);
//        Date end_Date = DateDispose.formattingDate("2018-12-11", DateType.Year_Month_Day);
//        Date totalStartDate = start_Date;
//        totalStartDate = DateDispose.dayCalculateDate(start_Date, 1);
//
//        System.out.println(DateDispose.formattingDate(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//        map.put("2018-12-11", new BigDecimal(2106000));
//        System.out.println("---------------------------------------------------------------------");
//        for (int i = 1; DateDispose.compareDateSize(totalStartDate, end_Date) || DateDispose.compareDate(totalStartDate, end_Date); i++, totalStartDate = DateDispose.dayCalculateDate(start_Date, i)) {
//            System.out.println(DateDispose.formattingDate(totalStartDate, DateType.Year_Month_Day));
//            BigDecimal b = new BigDecimal(0);
//            for (Map.Entry<String, BigDecimal> set : map.entrySet()) {
//                if (DateDispose.compareDateSize(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                        || DateDispose.compareDate(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)
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
//        Date start_Date = DateDispose.formattingDate("2019-01-01", DateType.Year_Month_Day);
//        Date end_Date = DateDispose.formattingDate("2019-01-02", DateType.Year_Month_Day);
//        Date totalStartDate = start_Date;
//        totalStartDate = DateDispose.dayCalculateDate(start_Date, 1);
//
//        System.out.println(DateDispose.formattingDate(start_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(end_Date, DateType.Year_Month_Day));
//        System.out.println(DateDispose.formattingDate(totalStartDate, DateType.Year_Month_Day));
//        System.out.println(total);
//        System.out.println("---------------------------------------------------------------------");
//        for (int i = 1; DateDispose.compareDateSize(totalStartDate, end_Date) || DateDispose.compareDate(totalStartDate, end_Date); i++, totalStartDate = DateDispose.dayCalculateDate(start_Date, i)) {
//            System.out.println(DateDispose.formattingDate(totalStartDate, DateType.Year_Month_Day));
//            BigDecimal b = new BigDecimal(0);
//            for (Map.Entry<String, BigDecimal> set : map.entrySet()) {
//                if (DateDispose.compareDateSize(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)
//                        || DateDispose.compareDate(DateDispose.formattingDate(set.getKey(), DateType.Year_Month_Day), totalStartDate)
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
