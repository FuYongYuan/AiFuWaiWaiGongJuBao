import dispose.CopyClass;
import excel.operation.ExcelExport;
import excel.operation.ExcelImport;
import excel.operation.set.SheetSet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class Test {
    public static void main(String[] age) {

        try {
            String rulesPath = "D:\\fuyy\\Desktop\\测试.xls";
            HSSFWorkbook work = new HSSFWorkbook(new FileInputStream(new File(rulesPath)));
            List<UserPO> rulesList = ExcelImport.getExcel(work.getSheet("测试"), UserPO.class);
            for (int i = 0; i < rulesList.size(); i++) {
                System.out.println(rulesList.get(i).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



//
//        List<Map<String, Object>> userPOList = new ArrayList<>();
//
//        /**
//         * 在全配置下.跨行多的情况下 XSSF最多 1250 条  HSSF最多 150 条   SXSSF最多 * 条
//         */
//        for (int i = 0; i < 5; i++) {
//            int ix = i / 10;
//            Map<String, Object> omap = new HashMap<>();
//            omap.put("account", "测试" + ix);
//            omap.put("password", "password" + i);
//            omap.put("state", 0);
//            userPOList.add(omap);
//        }
//
//        userPOList.forEach(System.out::println);
//
//        System.out.println("---------------------------------------");
//
//
//        long startTime = System.currentTimeMillis();
//
//
//        long endTime = System.currentTimeMillis();
//        float seconds = (endTime - startTime) / 1000F;
//        System.out.println("测试数据:" + seconds + "秒.");
//
//        try {
//            startTime = System.currentTimeMillis();
//
//            ExcelExport excelExport = new ExcelExport(new XSSFWorkbook());
//
//            List<SheetSet> sheetSets = new ArrayList<>();
//            SheetSet sheetSet = SheetSet.create()
//                    .setWorkbookName("测试")
//                    .setWorkbookData(CopyClass.copyMapGetSet(userPOList, UserPO.class))
//                    .setDataClass(UserPO.class)
//                    .build(excelExport.getWorkbook());
//
//
//            sheetSets.add(sheetSet);
//
//            endTime = System.currentTimeMillis();
//            seconds = (endTime - startTime) / 1000F;
//            System.out.println("赋值页对象:" + seconds + "秒.");
//
//            XSSFWorkbook xssfWorkbook = (XSSFWorkbook) excelExport.setExcel(sheetSets);
//
//            endTime = System.currentTimeMillis();
//            seconds = (endTime - startTime) / 1000F;
//            System.out.println("工作簿生成:" + seconds + "秒.");
//
//            String FileName = "D:\\fuyy\\Desktop\\测试.xlsx";
//            FileOutputStream fos = new FileOutputStream(FileName);
//            xssfWorkbook.write(fos);
//            fos.close();
//
//            endTime = System.currentTimeMillis();
//            seconds = (endTime - startTime) / 1000F;
//            System.out.println("导出完成:" + seconds + "秒.");
//
//            System.exit(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//

//
//
////        List<ExcelEntity> excelEntities = new ArrayList<>();
//
//        List<Map<String, Object>> excelEntities = new ArrayList<Map<String, Object>>();
//
//        /**
//         * 在全配置下.跨行多的情况下 XSSF最多 1250 条  HSSF最多 150 条   SXSSF最多 * 条
//         */
//        for (int i = 0; i < 5; i++) {
//            int ix = i / 10;
//
////            ExcelEntity excelEntity = new ExcelEntity();
////            excelEntity.sT = "测试" + ix;
////            excelEntity.iT = i / 5;
////            excelEntity.DT = new Date();
////            excelEntity.dT = Double.valueOf(Test.randomNumberByWithin(i) + "." + Test.randomNumberByWithin(i));
////            excelEntity.bDT = new BigDecimal(Test.randomNumberByWithin(i) + "." + Test.randomNumberByWithin(i));
////            excelEntity.bT = Test.randomBoolean();
////            excelEntities.add(excelEntity);
//
//            Map<String, Object> omap = new HashMap<>();
//            omap.put("id", i);
//            omap.put("sT", "测试" + ix);
//            omap.put("iT", i / 5);
////            if (i % 2 == 0) {
////                omap.put("DT", new Date());
////            }
//            omap.put("DT", new Date());
//            if (i % 2 != 0) {
//                omap.put("dT", Test.randomNumberByWithin(i) + "." + Test.randomNumberByWithin(i));
//            }
//            if (i % 2 == 0) {
//                omap.put("bDT", Test.randomNumberByWithin(i) + "." + Test.randomNumberByWithin(i));
//            }
////            if (i % 2 != 0) {
//            omap.put("bT", Test.randomBoolean());
////            }
//            excelEntities.add(omap);
//        }
//
//
//        excelEntities.forEach(System.out::println);
//
//        System.out.println("---------------------------------------");
//
//
//
//
//
//
//        long startTime = System.currentTimeMillis();
//
//
//        long endTime = System.currentTimeMillis();
//        float seconds = (endTime - startTime) / 1000F;
//        System.out.println("测试数据:" + seconds + "秒.");
//
//        try {
//            startTime = System.currentTimeMillis();
//
//            ExcelExport excelExport = new ExcelExport(new XSSFWorkbook());
//
//            List<SheetSet> sheetSets = new ArrayList<>();
//            SheetSet sheetSet = SheetSet.create()
//                    .setWorkbookName("测试")
//                    .setWorkbookData(CopyClass.copyMap(excelEntities, ExcelEntity.class))
//                    .setDataClass(ExcelEntity.class)
//                    .setIsGetMethodFieldValue(false)
//                    .build(excelExport.getWorkbook());
//
//            //标题样式
//            sheetSet.getStyle()
//                    .setTitleColor(IndexedColors.LIGHT_ORANGE)
//                    .setTitleBold(true)
//                    .setTitleAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER)
//            ;
//            //小计 - 建造者模式
//            sheetSet.getFunction().getSubTotal()
//                    .setReferenceFieldName("整型")
//                    .setSpanFieldNames("整型")
//                    .setCalculationFieldNames("小数点", "钱")
//                    .setCalculationFieldNameAndOrder("钱", 6)
//                    .setExplainAndOrder("小计：", 2)
//                    .setStyleColor(IndexedColors.CORAL)
//                    .setStyleBold(true)
//                    .setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER)
//            ;
//            //总计 - 方法写法
//            Map<String, Integer> calculationFieldNameAndOrder = new HashMap<>();
//            calculationFieldNameAndOrder.put("小数点", 4);
//            calculationFieldNameAndOrder.put("钱", 2);
//            sheetSet.getFunction().getTotal().setCalculationModel("字符串", new String[]{"整型"}, calculationFieldNameAndOrder, "总计：", 3);
//            sheetSet.getFunction().getTotal().setStyleColor(IndexedColors.SKY_BLUE);
//            sheetSet.getFunction().getTotal().setStyleBold(true);
//            sheetSet.getFunction().getTotal().setStyleMoneyFormat(2);
//            sheetSet.getFunction().getTotal().setHorizontalAlignment(HorizontalAlignment.CENTER);
//            sheetSet.getFunction().getTotal().setVerticalAlignment(VerticalAlignment.CENTER);
//
//            //全部总计 - 建造者模式
//            sheetSet.getFunction().getTotalAll()
//                    .setCalculationFieldNameAndOrder("小数点", 2)
//                    .setCalculationFieldNameAndOrder("钱", 4)
//                    .setExplain("总计所有：")
//                    .setExplainOrder(3)
//                    .setStyleColor(IndexedColors.LIME)
//                    .setStyleBold(true)
//                    .setStyleNumberFormat(2)
//                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
//                    .setVerticalAlignment(VerticalAlignment.CENTER)
//            ;
//
//
//            sheetSets.add(sheetSet);
//
//            endTime = System.currentTimeMillis();
//            seconds = (endTime - startTime) / 1000F;
//            System.out.println("赋值页对象:" + seconds + "秒.");
//
//            XSSFWorkbook xssfWorkbook = (XSSFWorkbook) excelExport.setExcel(sheetSets);
//
//            endTime = System.currentTimeMillis();
//            seconds = (endTime - startTime) / 1000F;
//            System.out.println("工作簿生成:" + seconds + "秒.");
//
//            String FileName = "D:\\fuyy\\Desktop\\测试1.xlsx";
//            FileOutputStream fos = new FileOutputStream(FileName);
//            xssfWorkbook.write(fos);
//            fos.close();
//
//            endTime = System.currentTimeMillis();
//            seconds = (endTime - startTime) / 1000F;
//            System.out.println("导出完成:" + seconds + "秒.");
//
//            System.exit(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//

//        try {
//            String rulesPath = "C:\\Users\\FYY\\Desktop\\测试.xlsx";
//            XSSFWorkbook work = new XSSFWorkbook(new FileInputStream(new File(rulesPath)));
//            @SuppressWarnings("unchecked")
//            List<ExcelEntity> rulesList = ExcelImport.getExcel(work.getSheet("test"), ExcelEntity.class);
//            for (int i = 0; i < rulesList.size(); i++) {
//                System.out.println(rulesList.get(i).toString());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    /**
     * 随机范围数
     */
    public static int randomNumberByWithin(int within) {
        return randomNumberByWithin(0, within);
    }

    /**
     * 随机范围数
     */
    public static int randomNumberByWithin(int startWithin, int endWithin) {
        Random random = new Random();
        return random.nextInt(endWithin - startWithin + 1) + startWithin;
    }

    /**
     * 随机boolean
     */
    public static boolean randomBoolean() {
        Random rd = new Random();
        return rd.nextBoolean();
    }
}
