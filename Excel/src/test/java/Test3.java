import excel.operation.ExcelImport;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

public class Test3 {
    public static void main(String[] args) {
//        try {
//            String rulesPath = "D:\\fuyy\\Desktop\\1.xlsx";
//            XSSFWorkbook work = new XSSFWorkbook(new FileInputStream(rulesPath));
//            @SuppressWarnings("unchecked")
//            List<TestLoad> rulesList = ExcelImport.getExcel(work.getSheet("1"), TestLoad.class);
//            System.out.println("---------------");
//            for (TestLoad t : rulesList) {
//                System.out.print(t.getA());
//                System.out.print(" | ");
//                System.out.print(t.getB());
//                System.out.print(" | ");
//                System.out.println(t.getC());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            String rulesPath = "D:\\fuyy\\Desktop\\3.xlsx";
            XSSFWorkbook work = new XSSFWorkbook(new FileInputStream(rulesPath));
            List<TreeMap<String, Object>> rulesList = ExcelImport.getExcel(work.getSheet("1"));

//            rulesList.forEach(map -> {
//                map.forEach((k, v) -> {
//                    System.out.print("k " + k + " | " + " n " + excelColStrToNum(k));
//                    System.out.print(" | ");
//                    System.out.print("v " + v);
//                    System.out.print(" | ");
//                });
//                System.out.println();
//            });

            // 补全信息
            List<String[]> list = new ArrayList<>();
            int snum = 0;
            for (TreeMap<String, Object> stringObjectTreeMap : rulesList) {
                if (snum < stringObjectTreeMap.size()) {
                    snum = stringObjectTreeMap.size();
                }
            }

            for (TreeMap<String, Object> stringObjectTreeMap : rulesList) {
                String[] ss = new String[snum];
                stringObjectTreeMap.forEach((k, v) -> {
                    ss[excelColStrToNum(k)-1] = v.toString();
                });
                list.add(ss);
            }
            list.forEach(i -> {
                for (String s : i) {
                    System.out.print(" | ");
                    System.out.print(s);
                    System.out.print(" | ");
                }
               System.out.println();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Excel column index begin 1
     */
    public static int excelColStrToNum(String colStr) {
        int num;
        int result = 0;
        for (int i = 0; i < colStr.length(); i++) {
            char ch = colStr.charAt(colStr.length() - i - 1);
            num = ch - 'A' + 1;
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }
}
