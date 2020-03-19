import dispose.DateDispose;
import enumerate.DateType;
import opencv.CalcHistCompare;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Test {
    private static String basePicPath = "D:\\Project\\AiFuWaiWaiGongJuBao\\OpenCV\\src\\main\\resources\\image\\";


    public static void main(String[] args) {
        Date sd = new Date();

        System.out.println(DateDispose.formatting_Date(sd, DateType.Year_Month_Day_Hour_Minute_Second_MS));

        CalcHistCompare calcHistCompare = CalcHistCompare.create()
                .setThreshold(0.9)
                .build();

        LinkedHashMap<String, Double> map = calcHistCompare.compare(basePicPath + "2.jpg", basePicPath);

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.println("图片：" + entry.getKey() + " 对比值：" + entry.getValue());
        }

        Date ed = new Date();
        System.out.println(DateDispose.formatting_Date(ed, DateType.Year_Month_Day_Hour_Minute_Second_MS));
        System.out.println("执行时间为：" + (sd.getTime() - ed.getTime()) + "毫秒");

//        double compareHist = compare_image(basePicPath + "1.jpg", basePicPath + "3.jpg");
//        System.out.println(compareHist);
//        if (compareHist > 0.72) {
//            System.out.println("匹配");
//        } else {
//            System.out.println("不匹配");
//        }
    }
}
