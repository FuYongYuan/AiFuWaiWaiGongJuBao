import dispose.DateDispose;
import enumerate.DateType;
import opencv.CalcHistCompare;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


//E:\JetBrains\IntelliJ IDEA\plugins\maven\lib\maven3\bin> mvn install:install-file -DgroupId=com.opencv -DartifactId=opencv -Dversion=4.2.0 -Dpackaging=jar -Dfile=E:\OpenCV\opencv\build\java\opencv-420.jar

//-ea -Djava.library.path=E:\OpenCV\opencv\build\java\x64 -Djava.awt.headless=false

//<!-- OpenCV -->
//<dependency>
//<groupId>com.opencv</groupId>
//<artifactId>opencv</artifactId>
//<version>4.2.0</version>
//</dependency>

public class Test {
    private static String basePicPath = "D:\\Project\\FYY\\AiFuWaiWaiGongJuBao\\OpenCV\\src\\main\\resources\\image\\";


    public static void main(String[] args) {
        Date sd = new Date();

        System.out.println(DateDispose.formattingDate(sd, DateType.Year_Month_Day_Hour_Minute_Second_MS));

        CalcHistCompare calcHistCompare = CalcHistCompare.create()
                .setCalcHistSize(10)
                .setThreshold(0.993)
                .build();

        LinkedHashMap<String, Double> map = calcHistCompare.compare(basePicPath + "24158.jpg", basePicPath);

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.println("图片：" + entry.getKey() + " 对比值：" + entry.getValue());
        }

//        orb();

//        OrbSearch orbSearch = OrbSearch.create()
//                .build();
//
//        LinkedHashMap<String, Double> map = orbSearch.search(basePicPath + "24158.jpg", basePicPath);
//
//        for (Map.Entry<String, Double> entry : map.entrySet()) {
//            System.out.println("图片：" + entry.getKey() + " 对比值：" + entry.getValue());
//        }
//
//        Date ed = new Date();
//        System.out.println(DateDispose.formattingDate(ed, DateType.Year_Month_Day_Hour_Minute_Second_MS));
//        System.out.println("执行时间为：" + (sd.getTime() - ed.getTime()) + "毫秒");

//        double compareHist = compare_image(basePicPath + "1.jpg", basePicPath + "3.jpg");
//        System.out.println(compareHist);
//        if (compareHist > 0.72) {
//            System.out.println("匹配");
//        } else {
//            System.out.println("不匹配");
//        }
    }


//    // 特征点匹配，值越大匹配度越高
//    public void imgMatching2() throws Exception {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        Mat src_base = Imgcodecs.imread("D:\\test\\test5.jpg");
//        Mat src_test = Imgcodecs.imread("D:\\test\\test3.jpg");
//        Mat gray_base = new Mat();
//        Mat gray_test = new Mat();
//        // 转换为灰度
//        Imgproc.cvtColor(src_base, gray_base, Imgproc.COLOR_RGB2GRAY);
//        Imgproc.cvtColor(src_test, gray_test, Imgproc.COLOR_RGB2GRAY);
//        // 初始化ORB检测描述子
//        // 初始化ORB检测描述子
//        FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.ORB);//特别提示下这里opencv暂时不支持SIFT、SURF检测方法，这个好像是opencv(windows) java版的一个bug,本人在这里被坑了好久。
//        DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
//        // 关键点及特征描述矩阵声明
//        MatOfKeyPoint keyPoint1 = new MatOfKeyPoint(), keyPoint2 = new MatOfKeyPoint();
//        Mat descriptorMat1 = new Mat(), descriptorMat2 = new Mat();
//        // 计算ORB特征关键点
//        featureDetector.detect(gray_base, keyPoint1);
//        featureDetector.detect(gray_test, keyPoint2);
//        // 计算ORB特征描述矩阵
//        descriptorExtractor.compute(gray_base, keyPoint1, descriptorMat1);
//        descriptorExtractor.compute(gray_test, keyPoint2, descriptorMat2);
//        float result = 0;
//        // 特征点匹配
//        System.out.println("test5：" + keyPoint1.size());
//        System.out.println("test3：" + keyPoint2.size());
//        if (!keyPoint1.size().empty() && !keyPoint2.size().empty()) {
//            // FlannBasedMatcher matcher = new FlannBasedMatcher();
//            DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_L1);
//            MatOfDMatch matches = new MatOfDMatch();
//            matcher.match(descriptorMat1, descriptorMat2, matches);
//            // 最优匹配判断
//            double minDist = 100;
//            DMatch[] dMatchs = matches.toArray();
//            int num = 0;
//            for (int i = 0; i < dMatchs.length; i++) {
//                if (dMatchs[i].distance <= 2 * minDist) {
//                    result += dMatchs[i].distance * dMatchs[i].distance;
//                    num++;
//                }
//            }
//            // 匹配度计算
//            result /= num;
//        }
//        System.out.println(result);
//    }


    public static void orb() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src_base = Imgcodecs.imread(basePicPath + "24158.jpg");
        Mat src_test = Imgcodecs.imread(basePicPath + "24315.jpg");
        Mat gray_base = new Mat();
        Mat gray_test = new Mat();
        // 转换为灰度
        Imgproc.cvtColor(src_base, gray_base, Imgproc.COLOR_RGB2GRAY);
        Imgproc.cvtColor(src_test, gray_test, Imgproc.COLOR_RGB2GRAY);
        // 初始化ORB检测描述子
        // 初始化ORB检测描述子
        ORB orb = ORB.create();
        // 关键点及特征描述矩阵声明
        MatOfKeyPoint keyPoint1 = new MatOfKeyPoint(), keyPoint2 = new MatOfKeyPoint();
        Mat descriptorMat1 = new Mat(), descriptorMat2 = new Mat();
        // 计算ORB特征关键点
        orb.detect(gray_base, keyPoint1);
        orb.detect(gray_test, keyPoint2);
        // 计算ORB特征描述矩阵
        orb.compute(gray_base, keyPoint1, descriptorMat1);
        orb.compute(gray_test, keyPoint2, descriptorMat2);
        // 特征点匹配
        if (!keyPoint1.size().empty() && !keyPoint2.size().empty()) {
            DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_L1);
            MatOfDMatch matches = new MatOfDMatch();
            matcher.match(descriptorMat1, descriptorMat2, matches);

            DMatch[] mats = matches.toArray();

//            float totalValue = 0f;
//            for (DMatch dMatch : mats) {
//                float distance = dMatch.distance <= 1 ? 1 : dMatch.distance;
//                totalValue += 2200 / distance + 0.6f;
//            }
//
//            int value = (int) (totalValue * 10);
//            System.out.println(value > 25 ? value : 0);


//            // 计算匹配值
//            LinkedList<DMatch> goodMatchesList = new LinkedList<>();
//
//            //对匹配结果进行筛选，依据distance进行筛选
//            for (DMatch m : mats) {
//                for (DMatch n : mats) {
//                    if (m.distance <= n.distance * 0.7f) {
//                        goodMatchesList.addLast(m);
//                    }
//                }
//            }
//
//            System.out.println(goodMatchesList.size());

//            // 计算匹配值
//            double maxDist = Double.MIN_VALUE;
//            double minDist = Double.MAX_VALUE;
//            for (DMatch mat : mats) {
//                double dist = mat.distance;
//                if (dist < minDist) {
//                    minDist = dist;
//                }
//                if (dist > maxDist) {
//                    maxDist = dist;
//                }
//            }
//            System.out.println("Max Distance:" + maxDist);
//            System.out.println("Min Distance:" + minDist);
//            System.out.println(minDist / maxDist);
//            System.out.println(maxDist - minDist);
        }
    }
}
