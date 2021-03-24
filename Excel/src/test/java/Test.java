import dispose.CopyClass;
import enumerate.DateType;
import excel.operation.ExcelExport;
import excel.operation.set.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;

public class Test {
    public static void main(String[] age) {
         long startTime = System.currentTimeMillis();


        long endTime = System.currentTimeMillis();
        float seconds = (endTime - startTime) / 1000F;
        System.out.println("测试数据:" + seconds + "秒.");

        try {
            startTime = System.currentTimeMillis();

            ExcelExport excelExport = new ExcelExport(new XSSFWorkbook());

            List<SheetSet> sheetSets = new ArrayList<>();

//            List<ValueLimitTest> st2 = new ArrayList<>();
//            ValueLimitTest test1 = new ValueLimitTest();
//            test1.setName("TYPE01");
//            test1.setValue("类型1");
//            st2.add(test1);
//            ValueLimitTest test2 = new ValueLimitTest();
//            test2.setName("TYPE02");
//            test2.setValue("类型2");
//            st2.add(test2);


            List<Map<String,String>> st2 = new ArrayList<>();
            Map<String,String> test1 = new HashMap<>();
            test1.put("name","TYPE01");
            test1.put("value","类型1");
            st2.add(test1);
            Map<String,String> test2 = new HashMap<>();
            test2.put("name","TYPE02");
            test2.put("value","类型2");
            st2.add(test2);

            List<Map<String, Object>> excelEntities = new ArrayList<>();
            setData(excelEntities);


            SheetSet sheetSet = SheetSet.create()
                    .setSheetName("测试")
                    .setSheetData(CopyClass.copyMap(excelEntities, ExcelEntity.class))
                    .setDataClass(ExcelEntity.class)
                    .setIsGetMethodFieldValue(false)
                    .setValueLimit(ValueLimit.create().setValueList(st2).setValueListName("sT2").setContrastFieldName("name").setReplaceFieldName("value").setIsMap(true).build())
                    .setExtraData(null
                            , ExtraRowData.create().setRowNumber(3).setExtraCellData(
//                                    ExtraCellData.create().setCellNumber(2).setCellValue("ceshi").setCellType(String.class).build(),
//                                    ExtraCellData.create().setCellNumber(3).setCellValue(1).setCellType(Integer.class).build(),
                                    ExtraCellData.create().setCellNumber(2).setColspan(4).setCellValue(new Date()).setCellType(Date.class).setColumnWidth(100).setBorder(BorderStyle.THIN).setBorderColor(IndexedColors.BLACK).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.CENTER).setFillColor(IndexedColors.LIGHT_ORANGE).setIsBold(true).build()
                            ).setIsNewRow(true).build()
                            , ExtraRowData.create().setRowNumber(4).setExtraCellData(
                                    ExtraCellData.create().setCellNumber(1).setCellValue("测试ce").setCellType(String.class).build(),
                                    ExtraCellData.create().setCellNumber(2).setCellValue(1).setCellType(Integer.class).build(),
                                    ExtraCellData.create().setCellNumber(3).setCellValue(new Date()).setCellType(Date.class).build(),
                                    ExtraCellData.create().setCellNumber(4).setCellType(BigDecimal.class).setBorder(BorderStyle.THIN).setBorderColor(IndexedColors.BLACK).setFillColor(IndexedColors.LIGHT_ORANGE).setIsMoney(true).build()
                            ).setIsNewRow(true).build()
                            , ExtraRowData.create().setRowNumber(1).setExtraCellData(
                                    ExtraCellData.create().setCellNumber(3).setCellValue("测试shi").setCellType(String.class).setHorizontalAlignment(HorizontalAlignment.CENTER).build(),
                                    ExtraCellData.create().setCellNumber(2).setCellValue(1).setCellType(Integer.class).build(),
                                    ExtraCellData.create().setCellNumber(1).setCellValue(new Date()).setCellType(Date.class).setDateType(DateType.Year_Month_Day).build(),
                                    ExtraCellData.create().setCellNumber(4).setCellValue(new BigDecimal(12311)).setCellType(BigDecimal.class).build()
                            ).setIsNewRow(true).build()
                            , ExtraRowData.create().setRowNumber(2).setExtraCellData(
                                    ExtraCellData.create().setCellNumber(3).setCellValue("测试2").setCellType(String.class).setVerticalAlignment(VerticalAlignment.TOP).build(),
                                    ExtraCellData.create().setCellNumber(2).setCellValue(1).setCellType(Integer.class).build(),
                                    ExtraCellData.create().setCellNumber(1).setCellValue(new Date()).setCellType(Date.class).setDateType(DateType.Hour_Minute_Second).build(),
                                    ExtraCellData.create().setCellNumber(4).setCellValue(1.22222222333).setCellType(Double.class).build()
                            ).setIsNewRow(true).build()
                            , ExtraRowData.create().setIsMaxRowNumber(true).setExtraCellData(
                                    ExtraCellData.create().setCellNumber(3).setCellValue("测试").setCellType(String.class).build(),
                                    ExtraCellData.create().setCellNumber(2).setCellValue(1).setCellType(Integer.class).build(),
                                    ExtraCellData.create().setCellNumber(1).setCellValue(new Date()).setCellType(Date.class).build()
                            ).setIsNewRow(true).build()
                    )
                    .build(excelExport.getWorkbook());

            //标题样式
            sheetSet.getStyle()
                    .setTitleColor(IndexedColors.LIGHT_ORANGE)
                    .setTitleBold(true)
                    .setTitleAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER)
            ;

//            //设置整体边框
//            sheetSet.getStyle()
//                    .setContextBorder(BorderStyle.THIN)
//                    .setContextBorderColor(IndexedColors.BLACK)
//            ;


//            //小计 - 无限set写法
//            sheetSet.getFunction().getSubTotal()
//                    .setReferenceFieldName("整型")
//                    .setSpanFieldNames("整型")
//                    .setCalculationFieldNames("小数点", "钱")
//                    .setCalculationFieldNameAndOrder("钱", 6)
//                    .setExplainAndOrder("小计：", 2)
//                    .setStyleColor(IndexedColors.CORAL)
//                    .setStyleBold(true).setStyleMoneyFormat(2)
//                    .setAlignment(HorizontalAlignment.CENTER,VerticalAlignment.CENTER)
//            ;
//            //总计 - 方法写法
//            Map<String, Integer> calculationFieldNameAndOrder = new HashMap<String, Integer>();
//            calculationFieldNameAndOrder.put("小数点", 4);
//            calculationFieldNameAndOrder.put("钱", 2);
//            sheetSet.getFunction().getTotal().setCalculationModel(
//                    "字符串",
//                    new String[]{"整型"},
//                    calculationFieldNameAndOrder,
//                    "总计：",
//                    3
//            );
//            sheetSet.getFunction().getTotal().setStyleColor(IndexedColors.SKY_BLUE);
//            sheetSet.getFunction().getTotal().setStyleBold(true);
//            sheetSet.getFunction().getTotal().setStyleMoneyFormat(2);
//            sheetSet.getFunction().getTotal().setHorizontalAlignment(HorizontalAlignment.CENTER);
//            sheetSet.getFunction().getTotal().setVerticalAlignment(VerticalAlignment.CENTER);
//            sheetSet.getFunction().getTotal().setRowExtraData(
//                    Function.Builder.RowExtraData.create()
//                            .setOrder(5)
//                            .setValue("[this.2]-([this.4]+[this.2])*[this.4]")
//                            .setIsFormula(true)
//                            .build()
//            );

//            //全部总计 - 无限set写法
//            sheetSet.getFunction().getTotalAll()
//                    .setCalculationFieldNameAndOrder("小数点", 2)
//                    .setCalculationFieldNameAndOrder("钱", 4)
//                    .setRowExtraData(
//                            Function.Builder.RowExtraData.create()
//                                    .setOrder(3)
//                                    .setValue("总计所有：")
//                                    .build(),
//                            Function.Builder.RowExtraData.create()
//                                    .setOrder(5)
//                                    .setValue("[this.2]-([this.4]+[this.2])*[this.4]")
////                                    .setValue("D22-B22")
//                                    .setIsFormula(true)
//                                    .build()
//                    )
//                    .setStyleColor(IndexedColors.LIME)
//                    .setStyleBold(true)
//                    .setStyleNumberFormat(2)
//                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
//                    .setVerticalAlignment(VerticalAlignment.CENTER)
//            ;

            sheetSets.add(sheetSet);

            endTime = System.currentTimeMillis();
            seconds = (endTime - startTime) / 1000F;
            System.out.println("赋值页对象:" + seconds + "秒.");

            XSSFWorkbook xssfWorkbook = (XSSFWorkbook) excelExport.setExcel(sheetSets);

            endTime = System.currentTimeMillis();
            seconds = (endTime - startTime) / 1000F;
            System.out.println("工作簿生成:" + seconds + "秒.");

            String FileName = "D:\\fuyy\\Desktop\\测试1.xlsx";
            FileOutputStream fos = new FileOutputStream(FileName);
            xssfWorkbook.write(fos);
            fos.close();

            endTime = System.currentTimeMillis();
            seconds = (endTime - startTime) / 1000F;
            System.out.println("导出完成:" + seconds + "秒.");

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }


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



    private static void setData(List<Map<String, Object>> excelEntities) {

//        List<ExcelEntity> excelEntities = new ArrayList<>();

        /**
         * 在全配置下.跨行多的情况下 XSSF最多 1250 条  HSSF最多 150 条   SXSSF最多 * 条
         */
        for (int i = 0; i < 50000; i++) {
            int ix = i / 10;

//            ExcelEntity excelEntity = new ExcelEntity();
//            excelEntity.sT = "测试" + ix;
//            excelEntity.iT = i / 5;
//            excelEntity.DT = new Date();
//            excelEntity.dT = Double.valueOf(RandomUtil.randomNumberByWithin(i) + "." + RandomUtil.randomNumberByWithin(i));
//            excelEntity.bDT = new BigDecimal(RandomUtil.randomNumberByWithin(i) + "." + RandomUtil.randomNumberByWithin(i));
//            excelEntity.bT = RandomUtil.randomBoolean();
//            excelEntities.add(excelEntity);

            Map<String, Object> omap = new HashMap<String, Object>();
            omap.put("sT", "测试" + ix);
            omap.put("iT", i / 5);
//            if (i % 2 == 0) {
//                omap.put("DT", new Date());
//            }
            omap.put("DT", new Date());
            if (i % 2 != 0) {
                omap.put("dT", randomNumberByWithin(i) + "." + randomNumberByWithin(i)+ randomNumberByWithin(i)+ randomNumberByWithin(i)+ randomNumberByWithin(i)+ randomNumberByWithin(i));
            }
//            if (i % 2 == 0) {
//                omap.put("bDT", RandomUtil.randomNumberByWithin(i) + "." + RandomUtil.randomNumberByWithin(i));
//            }
//            if (i / 5 != 0) {
//                omap.put("bDT", i / 5);
//            }
//            if ((i / 5) % 2 == 0) {
//            if (i != 50 && i != 51 && i != 70 && i != 71 && i != 72 && i != 73 && i != 77 && i != 78) {
            omap.put("bDT", 1);
//            }

//            } else {
//                omap.put("bDT", 2.2);
//            }
//            if (i % 2 != 0) {
            omap.put("bT", randomBoolean());
//            }

            if (i % 2 == 0) {
                omap.put("sT2", "TYPE01");
            } else {
                omap.put("sT2", "TYPE02");
            }
            excelEntities.add(omap);
        }

    }
}
