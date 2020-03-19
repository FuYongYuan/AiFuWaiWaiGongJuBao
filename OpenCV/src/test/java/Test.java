import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

public class Test {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

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
        return Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
    }

    public static void main(String[] args) {
        String basePicPath = "E:\\fuyy\\AiFuWaiWaiGongJuBao\\OpenCV\\src\\main\\resources\\image\\";
        double compareHist = compare_image(basePicPath + "1.jpg", basePicPath + "3.jpg");
        System.out.println(compareHist);
        if (compareHist > 0.72) {
            System.out.println("匹配");
        } else {
            System.out.println("不匹配");
        }
    }
}
