package demo;

import dispose.DateDispose;
import encrypt.RandomUtil;
import enumerate.DateType;
import excel.operation.ExcelExport;
import excel.operation.set.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;

public class Test {
    public static void main(String[] args) throws Exception {
        List<Jsb> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {


            Jsb jsb = new Jsb();

            if (i < 10) {
                jsb.setXz("小组1");
            } else if (i < 20) {
                jsb.setXz("小组2");
            } else if (i < 30) {
                jsb.setXz("小组3");
            } else if (i < 40) {
                jsb.setXz("小组4");
            } else if (i < 50) {
                jsb.setXz("小组5");
            } else if (i < 60) {
                jsb.setXz("小组6");
            } else if (i < 70) {
                jsb.setXz("小组7");
            } else if (i < 80) {
                jsb.setXz("小组8");
            } else if (i < 90) {
                jsb.setXz("小组9");
            } else if (i < 100) {
                jsb.setXz("小组0");
            }


            jsb.setXm("姓名" + (i / 5));


            jsb.setCsny(DateDispose.randomDate("1980-01-01 00:00:00", "2000-12-31 00:00:00"));
            jsb.setNl(RandomUtil.randomNumberByWithin(20));
            jsb.setXb(RandomUtil.randomNumberByWithin(2));
            jsb.setSg(RandomUtil.randomNumberByWithin(200));
            jsb.setTz(RandomUtil.randomNumberByWithin(200));
            jsb.setYe(new BigDecimal(RandomUtil.randomNumberByWithin(10000)));

            list.add(jsb);

        }


//        List<Map<String, String>> xblist = new ArrayList<>();
//        Map<String,String> map1 =new HashMap<>();
//        map1.put("id","0");
//        map1.put("name","保密");
//        xblist.add(map1);
//        Map<String,String> map2 =new HashMap<>();
//        map2.put("id","1");
//        map2.put("name","男");
//        xblist.add(map2);
//        Map<String,String> map3 =new HashMap<>();
//        map3.put("id","2");
//        map3.put("name","女");
//        xblist.add(map3);

//        ValueLimit valueLimit = ValueLimit.create()
//        .setValueList(xblist)
//        .setValueListName("xblist")
//        .setContrastFieldName("id")
//        .setReplaceFieldName("name")
//        .setIsMap(true)
//        .build();

        List<Xb> xblist = new ArrayList<>();
        Xb xb1 = new Xb();
        xb1.setId("0");
        xb1.setName("保密");
        xblist.add(xb1);
        Xb xb2 = new Xb();
        xb2.setId("1");
        xb2.setName("男");
        xblist.add(xb2);
        Xb xb3 = new Xb();
        xb3.setId("2");
        xb3.setName("女");
        xblist.add(xb3);

        ValueLimit valueLimit = ValueLimit.create()
        .setValueList(xblist)
        .setValueListName("xblist")
        .setContrastFieldName("id")
        .setReplaceFieldName("name")
        .build();


//        list.forEach(System.out::println);


        ExcelExport excelExport = new ExcelExport(new XSSFWorkbook());

        List<SheetSet> sheetSets = new ArrayList<>();


        SheetSet sheetSet = SheetSet.create()
                .setSheetName("技术部")
                .setSheetData(list)
                .setDataClass(Jsb.class)
                .setExtraData(
                        ExtraRowData.create().setRowNumber(1).setExtraCellData(
                                ExtraCellData.create().setCellNumber(1).setColspan(2).setCellValue("测试").setCellType(String.class).setHorizontalAlignment(HorizontalAlignment.CENTER).build()
                        ).setIsNewRow(true).build()
                )
                .build(excelExport.getWorkbook());


        //小计
        sheetSet.getFunction()
                .getSubTotal()
                .setReferenceFieldName("姓名")
                .setSpanFieldNames("小组","姓名")
                .setCalculationFieldNameAndOrder("余额", 3)
                .setExplainAndOrder("小计：", 8)
                .setStyleColor(IndexedColors.CORAL)
                .setRowExtraData(
                        Function.Builder.RowExtraData.create()
                                .setOrder(4)
                                .setValue("测试：")
                                .build(),
                        Function.Builder.RowExtraData.create()
                                .setOrder(5)
//                                .setValue("[this.4]-([this.6]+[this.7])*[this.8]")
                                    .setValue("D3-D4")
                                .setIsFormula(true)
                                .build()
                )
        ;
        //总计
        sheetSet.getFunction()
                .getTotal()
                .setReferenceFieldName("小组")
                .setSpanFieldNames("小组","姓名")
                .setCalculationFieldNameAndOrder("余额", 8)
                .setExplainAndOrder("总计：", 7)
                .setStyleColor(IndexedColors.SKY_BLUE)
        ;
        //全部总计
        sheetSet.getFunction()
                .getTotalAll()
                .setCalculationFieldNameAndOrder("余额", 8)
                .setExplainAndOrder("总计：", 7)
                .setStyleColor(IndexedColors.GREEN)
        ;


        sheetSet.getStyle()
                .setTitleColor(IndexedColors.GREY_25_PERCENT)
                .setContextBorder(BorderStyle.THIN);

        sheetSets.add(sheetSet);

        sheetSet.setValueLimit(valueLimit);


        XSSFWorkbook xssfWorkbook = (XSSFWorkbook) excelExport.setExcel(sheetSets);


        String FileName = "D:\\fuyy\\Desktop\\测试1.xlsx";
        FileOutputStream fos = new FileOutputStream(FileName);
        xssfWorkbook.write(fos);
        fos.close();
        System.exit(0);

    }
}
