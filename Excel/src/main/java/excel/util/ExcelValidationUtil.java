package excel.util;

import excel.exception.ExcelOperateException;
import excel.operation.ExcelExport;
import excel.operation.set.SheetSet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel校验帮助类
 */
public class ExcelValidationUtil {

    /**
     * 验证
     *
     * @param path 文件路径
     * @return 是否存在
     */
    public static boolean validationDocument(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 验证文档不存在则创建
     *
     * @param path      地址
     * @param sheetName 页名
     * @param tClass    Excel数据类型
     * @param <T>       类型
     */
    public static <T> void validationDocument(String path, String sheetName, Class<T> tClass) {
        try {
            boolean isNow = false;
            String filePath = path.substring(0, path.lastIndexOf("\\"));
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
                isNow = true;
            }
            if (isNow) {
                Map<String, List<T>> map = new HashMap<>();
                map.put(sheetName, new ArrayList<>());
                ExcelExport excelExport = new ExcelExport(new XSSFWorkbook());
                SheetSet sheetSet = SheetSet.create().build(excelExport.getWorkbook());
                List<SheetSet> sheetSets = new ArrayList<>();
                sheetSets.add(sheetSet);
                XSSFWorkbook workbook = (XSSFWorkbook) excelExport.setExcel(sheetSets);
                FileOutputStream fileOut = new FileOutputStream(path);
                workbook.write(fileOut);
                fileOut.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcelOperateException("诊断：验证文档不存在则创建过程失败");
        }
    }

}
