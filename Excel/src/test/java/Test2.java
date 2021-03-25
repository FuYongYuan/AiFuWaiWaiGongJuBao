import dispose.CopyClass;
import dispose.DateDispose;
import excel.operation.ExcelExport;
import excel.operation.set.SheetSet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        List<TestEntity> list = new ArrayList<>();

        TestEntity t = new TestEntity();

        for (int i = 0; i < 1; i++) {
            t.date1 = DateDispose.monthCalculateDate(new Date(), -3);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.monthCalculateDate(new Date(), -3), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -3);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.monthCalculateDate(new Date(), -3), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -3);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.monthCalculateDate(new Date(), -3), -1);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -3);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.monthCalculateDate(new Date(), -3), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -3);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.monthCalculateDate(new Date(), -3), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -3);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.monthCalculateDate(new Date(), -3), -1);
            list.add(t);

            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -2);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.monthCalculateDate(new Date(), -2), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -2);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.monthCalculateDate(new Date(), -2), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -2);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.monthCalculateDate(new Date(), -2), -1);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -2);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.monthCalculateDate(new Date(), -2), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -2);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.monthCalculateDate(new Date(), -2), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -2);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.monthCalculateDate(new Date(), -2), -1);
            list.add(t);

            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -1);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.monthCalculateDate(new Date(), -1), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -1);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.monthCalculateDate(new Date(), -1), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -1);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.monthCalculateDate(new Date(), -1), -1);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -1);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.monthCalculateDate(new Date(), -1), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -1);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.monthCalculateDate(new Date(), -1), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.monthCalculateDate(new Date(), -1);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.monthCalculateDate(new Date(), -1), -1);
            list.add(t);

            t = new TestEntity();
            t.date1 = new Date();
            t.date2 = DateDispose.hoursCalculateDate(new Date(), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = new Date();
            t.date2 = DateDispose.hoursCalculateDate(new Date(), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = new Date();
            t.date2 = DateDispose.hoursCalculateDate(new Date(), -1);
            list.add(t);
            t = new TestEntity();
            t.date1 = new Date();
            t.date2 = DateDispose.minutesCalculateDate(new Date(), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = new Date();
            t.date2 = DateDispose.minutesCalculateDate(new Date(), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = new Date();
            t.date2 = DateDispose.minutesCalculateDate(new Date(), -1);
            list.add(t);


            t.date1 = DateDispose.dayCalculateDate(new Date(), -3);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.dayCalculateDate(new Date(), -3), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -3);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.dayCalculateDate(new Date(), -3), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -3);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.dayCalculateDate(new Date(), -3), -1);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -3);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.dayCalculateDate(new Date(), -3), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -3);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.dayCalculateDate(new Date(), -3), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -3);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.dayCalculateDate(new Date(), -3), -1);
            list.add(t);

            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -2);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.dayCalculateDate(new Date(), -2), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -2);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.dayCalculateDate(new Date(), -2), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -2);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.dayCalculateDate(new Date(), -2), -1);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -2);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.dayCalculateDate(new Date(), -2), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -2);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.dayCalculateDate(new Date(), -2), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -2);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.dayCalculateDate(new Date(), -2), -1);
            list.add(t);

            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -1);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.dayCalculateDate(new Date(), -1), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -1);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.dayCalculateDate(new Date(), -1), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -1);
            t.date2 = DateDispose.hoursCalculateDate(DateDispose.dayCalculateDate(new Date(), -1), -1);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -1);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.dayCalculateDate(new Date(), -1), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -1);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.dayCalculateDate(new Date(), -1), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = DateDispose.dayCalculateDate(new Date(), -1);
            t.date2 = DateDispose.minutesCalculateDate(DateDispose.dayCalculateDate(new Date(), -1), -1);
            list.add(t);

            t = new TestEntity();
            t.date1 = new Date();
            t.date2 = DateDispose.hoursCalculateDate(new Date(), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = new Date();
            t.date2 = DateDispose.hoursCalculateDate(new Date(), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = new Date();
            t.date2 = DateDispose.hoursCalculateDate(new Date(), -1);
            list.add(t);
            t = new TestEntity();
            t.date1 = new Date();
            t.date2 = DateDispose.minutesCalculateDate(new Date(), -3);
            list.add(t);
            t = new TestEntity();
            t.date1 = new Date();
            t.date2 = DateDispose.minutesCalculateDate(new Date(), -2);
            list.add(t);
            t = new TestEntity();
            t.date1 = new Date();
            t.date2 = DateDispose.minutesCalculateDate(new Date(), -1);
            list.add(t);
        }


        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        float seconds = (endTime - startTime) / 1000F;
        System.out.println("测试数据:" + seconds + "秒.");
        try {
            startTime = System.currentTimeMillis();

            ExcelExport excelExport = new ExcelExport(new XSSFWorkbook());

            List<SheetSet> sheetSets = new ArrayList<>();

            SheetSet ss = SheetSet.create()
                    .setSheetName("测试")
                    .setSheetData(list)
                    .setDataClass(TestEntity.class)
                    .setIsGetMethodFieldValue(false)
                    .build(excelExport.getWorkbook());
            sheetSets.add(ss);

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

    }
}
