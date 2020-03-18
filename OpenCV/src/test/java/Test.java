import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

public class Test {
    public static double compare_image(String img_1, String img_2) {
        Mat mat_1 = Imgcodecs.imread(img_1);
        Mat mat_2 = Imgcodecs.imread(img_2);
        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();

        //颜色范围
        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        //直方图大小， 越大匹配越精确 (越慢)
        MatOfInt histSize = new MatOfInt(1000);

        Imgproc.calcHist(Arrays.asList(mat_1), new MatOfInt(0), new Mat(), hist_1, histSize, ranges);
        Imgproc.calcHist(Arrays.asList(mat_2), new MatOfInt(0), new Mat(), hist_2, histSize, ranges);

        // CORREL 相关系数
        double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
        return res;
    }

    public static void main(String[] args) {
        String basePicPath = "F:\\workTest\\FaceOpenCVData\\";
        double compareHist = compare_image(basePicPath + "1.jpg", basePicPath + "000.jpg");
        System.out.println(compareHist);
        if (compareHist > 0.72) {
            System.out.println("人脸匹配");
        } else {
            System.out.println("人脸不匹配");
        }
    }
}
