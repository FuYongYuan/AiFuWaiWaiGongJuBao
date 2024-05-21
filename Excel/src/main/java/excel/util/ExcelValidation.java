package excel.util;

import excel.exception.ExcelOperateException;
import excel.operation.ExcelExport;
import excel.operation.set.SheetSet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel校验帮助类
 *
 * @author fyy
 */
public class ExcelValidation {

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
                if (!file.mkdirs()) {
                    throw new ExcelOperateException("创建文件失败！", new IOException());
                }
            }
            file = new File(path);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new ExcelOperateException("创建文件失败！", new IOException());
                }
                isNow = true;
            }
            if (isNow) {
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
            throw new ExcelOperateException("诊断：验证文档不存在则创建过程失败！", e);
        }
    }

}
