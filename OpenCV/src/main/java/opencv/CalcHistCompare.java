package opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.*;

/**
 * 直方图比较
 */
public class CalcHistCompare {
    /**
     * 颜色范围开始值
     */
    private float startRanges = 0f;

    /**
     * 颜色范围结束值
     */
    private float endRanges = 256f;

    /**
     * 直方图大小， 越大匹配越精确 (越慢)
     */
    private int calcHistSize = 1000;

    /**
     * 筛选阈值
     */
    private double threshold = 0.72;

    /**
     * 筛选数量
     */
    private Integer number = 20;

    /**
     * 构造
     */
    private CalcHistCompare() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * 创建
     */
    public static CalcHistCompare create() {
        return new CalcHistCompare();
    }

    /**
     * 初始化 需要工作簿对象
     */
    public CalcHistCompare build() {
        return this;
    }

    /**
     * 直方图对比查找最接近的图片
     *
     * @param imagePath 图片目录
     * @param filePath  文件夹地址
     * @return LinkedHashMap<String, Double> Key文件目录  Value对比值 对比值超过0.72越多越相近
     */
    public LinkedHashMap<String, Double> compare(String imagePath, String filePath) {
        //结果集
        Map<String, Double> map = new HashMap<>();
        //需要比对的图片
        Mat imageMat = Imgcodecs.imread(imagePath);
        //被比对的文件目录下的所有文件
        File file = new File(filePath);
        File[] fileList = file.listFiles();
        //有文件集
        if (fileList != null) {
            for (File f : fileList) {
                //被对比的图片
                Mat fileMat = Imgcodecs.imread(f.getPath());
                Mat imageHist = new Mat();
                Mat fileHist = new Mat();
                //颜色范围
                MatOfFloat ranges = new MatOfFloat(startRanges, endRanges);
                //直方图大小， 越大匹配越精确 (越慢)
                MatOfInt histSize = new MatOfInt(calcHistSize);

                //绘制直方图
                Imgproc.calcHist(Collections.singletonList(imageMat), new MatOfInt(0), new Mat(), imageHist, histSize, ranges);
                Imgproc.calcHist(Collections.singletonList(fileMat), new MatOfInt(0), new Mat(), fileHist, histSize, ranges);

                //结果
                double compareResult = Imgproc.compareHist(imageHist, fileHist, Imgproc.CV_COMP_CORREL);

                //超过阈值则返回
                if (compareResult >= threshold) {
                    map.put(f.getPath(), compareResult);
                }
            }
        }

        //排序
        List<Map.Entry<String, Double>> infoIds = new ArrayList<>(map.entrySet());
        infoIds.sort((o1, o2) -> {
            //降序排序
            return o2.getValue().compareTo(o1.getValue());
        });
        //转换成新map输出
        LinkedHashMap<String, Double> result = new LinkedHashMap<>();
        //返回数量
        int num = 0;
        for (Map.Entry<String, Double> entry : infoIds) {
            //限制返回数量
            num++;
            if (num > number) {
                break;
            }
            //返回内容
            result.put(entry.getKey(), entry.getValue());
        }

        //返回结果
        return result;
    }

    /**
     * 颜色范围开始值
     */
    public CalcHistCompare setStartRanges(float startRanges) {
        this.startRanges = startRanges;
        return this;
    }

    /**
     * 颜色范围结束值
     */
    public CalcHistCompare setEndRanges(float endRanges) {
        this.endRanges = endRanges;
        return this;
    }

    /**
     * 直方图大小， 越大匹配越精确 (越慢)
     */
    public CalcHistCompare setCalcHistSize(int calcHistSize) {
        this.calcHistSize = calcHistSize;
        return this;
    }

    /**
     * 筛选阈值
     */
    public CalcHistCompare setThreshold(double threshold) {
        this.threshold = threshold;
        return this;
    }

    /**
     * 筛选数量
     */
    public CalcHistCompare setNumber(Integer number) {
        this.number = number;
        return this;
    }
}
