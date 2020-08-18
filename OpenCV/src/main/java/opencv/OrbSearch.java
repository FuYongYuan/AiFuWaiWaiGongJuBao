package opencv;

import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.*;

/**
 * 根据特征点数来搜索相似图片
 *
 * @author fyy
 */
public class OrbSearch {
    /**
     * 筛选数量
     */
    private Integer number = 20;

    /**
     * 构造
     */
    private OrbSearch() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * 创建
     */
    public static OrbSearch create() {
        return new OrbSearch();
    }

    /**
     * 初始化 需要工作簿对象
     */
    public OrbSearch build() {
        return this;
    }


    /**
     * 直方图对比查找最接近的图片
     *
     * @param imagePath 图片目录
     * @param filePath  文件夹地址
     * @return LinkedHashMap<String, Double> Key文件目录  Value对比值 对比值超过0.72越多越相近
     */
    public LinkedHashMap<String, Double> search(String imagePath, String filePath) {
        //结果集
        Map<String, Double> map = new HashMap<>(16);
        //需要比对的图片
        Mat imageMat = Imgcodecs.imread(imagePath);
        //被比对的文件目录下的所有文件
        File file = new File(filePath);
        File[] fileList = file.listFiles();
        //有文件集
        if (fileList != null) {
            //转换为灰度
            Mat imageMatColor = new Mat();
            Imgproc.cvtColor(imageMat, imageMatColor, Imgproc.COLOR_RGB2GRAY);
            for (File f : fileList) {
                //被对比的图片
                Mat fileMat = Imgcodecs.imread(f.getPath());
                //转换为灰度
                Mat fileMatColor = new Mat();
                Imgproc.cvtColor(fileMat, fileMatColor, Imgproc.COLOR_RGB2GRAY);

                //创建比对
                ORB orb = ORB.create();
                // 关键点及特征描述矩阵声明
                MatOfKeyPoint imageMatOfKeyPoint = new MatOfKeyPoint(), fileMatOfKeyPoint = new MatOfKeyPoint();
                Mat imageMatDescriptorMatcher = new Mat(), fileMatDescriptorMatcher = new Mat();
                // 计算ORB特征关键点
                orb.detect(imageMatColor, imageMatOfKeyPoint);
                orb.detect(fileMatColor, fileMatOfKeyPoint);
                // 计算ORB特征描述矩阵
                orb.compute(imageMatColor, imageMatOfKeyPoint, imageMatDescriptorMatcher);
                orb.compute(fileMatColor, fileMatOfKeyPoint, fileMatDescriptorMatcher);
                // 特征点匹配
                if (!imageMatOfKeyPoint.size().empty() && !fileMatOfKeyPoint.size().empty()) {
                    DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_L1);
                    MatOfDMatch matches = new MatOfDMatch();
                    matcher.match(imageMatDescriptorMatcher, fileMatDescriptorMatcher, matches);
                    DMatch[] mats = matches.toArray();

                    float totalValue = 0f;
                    for (DMatch dMatch : mats) {
                        float distance = dMatch.distance <= 1 ? 1 : dMatch.distance;
                        totalValue += 2200 / distance + 0.6f;
                    }

                    int value = (int) (totalValue * 10);

                    map.put(f.getPath(), (double) (value > 25 ? value : 0));
                }
            }
        }

        //转换成新map输出
        LinkedHashMap<String, Double> result = new LinkedHashMap<>();

        if (!map.isEmpty()) {
            //排序
            List<Map.Entry<String, Double>> infoIds = new ArrayList<>(map.entrySet());
            infoIds.sort((o1, o2) -> {
                //降序排序
                return o2.getValue().compareTo(o1.getValue());
            });

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
        }

        //返回结果
        return result;
    }

    /**
     * 筛选数量
     */
    public OrbSearch setNumber(Integer number) {
        this.number = number;
        return this;
    }
}
