import excel.operation.ExcelImport;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.util.List;

public class T4 {
    public static void main(String[] args) {
        try {
            String rulesPath = "D:\\fuyy\\Desktop\\2.xls";
            HSSFWorkbook work = new HSSFWorkbook(new FileInputStream(rulesPath));
            @SuppressWarnings("unchecked")
            List<Test4> rulesList = ExcelImport.getExcel(work.getSheet("内码更新"), Test4.class);
            for (int i = 0; i < rulesList.size(); i++) {
                String s = "UPDATE admp_standard_region set mdmcode = '" + rulesList.get(i).getCode() + "' where region_id = " + rulesList.get(i).getRegion_id() + ";";
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
