package excel;

import dispose.TextDispose;
import excel.annotation.ExcelField;
import excel.exception.ExcelOperateException;
import excel.util.ExcelDisposeUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Excel操作类
 */
public class ExcelOperate {
    /**
     * 地址
     */
    private String path;

    /**
     * 文档对象
     */
    public Workbook workbook;

    public ExcelOperate(String documentPath) throws ExcelOperateException, IOException {
        this.path = documentPath;
        if (TextDispose.isNotEmpty(this.path)) {
            String prefix = this.path.substring(this.path.lastIndexOf(".") + 1);
            if ("xlsx".equals(prefix)) {
                workbook = new XSSFWorkbook(new FileInputStream(new File(this.path)));
            } else if ("xls".equals(prefix)) {
                workbook = new HSSFWorkbook(new FileInputStream(new File(this.path)));
            } else {
                throw new ExcelOperateException("诊断：传入文档格式不正确");
            }
        } else {
            throw new ExcelOperateException("诊断：传入文档地址为空");
        }
    }

    /**
     * 新增
     *
     * @param sheetName 页标签名称
     * @param obj       Excel对象
     * @return 添加后的行号
     */
    public int addRow(String sheetName, Object obj) {
        int result = 0;
        if (TextDispose.isNotEmpty(sheetName)) {
            List<Field> fieldList = ExcelDisposeUtil.getFieldList(obj.getClass());
            if (fieldList != null && fieldList.size() > 0) {
                Sheet sheet = workbook.getSheet(sheetName);
                result = sheet.getLastRowNum() + 1;
                Row row = sheet.createRow(result);
                for (int i = 0; i < fieldList.size(); i++) {
                    row.createCell(i).setCellValue(ExcelDisposeUtil.correspondingValue(
                            fieldList.get(i), obj,
                            fieldList.get(i).getAnnotation(ExcelField.class).dateType(),
                            fieldList.get(i).getAnnotation(ExcelField.class).decimalAfterDigit()));
                }
            } else {
                new ExcelOperateException("诊断：对象中没有匹配的文档内容").printStackTrace();
            }
        } else {
            new ExcelOperateException("诊断：传入文档表名称错误").printStackTrace();
        }
        if (0 < result) {
            this.writeDocument();
        }
        return result;
    }

    /**
     * 修改
     *
     * @param sheetName 页标签名称
     * @param rowNumber 行号
     * @param obj       对象
     * @return 是否修改成功
     */
    public boolean updateRow(String sheetName, int rowNumber, Object obj) {
        boolean result;
        if (TextDispose.isNotEmpty(sheetName)) {
            List<Field> fieldList = ExcelDisposeUtil.getFieldList(obj.getClass());
            if (fieldList != null && fieldList.size() > 0) {
                Sheet sheet = workbook.getSheet(sheetName);
                if (rowNumber >= 0 && rowNumber <= sheet.getLastRowNum()) {
                    Row row = sheet.getRow(rowNumber);
                    for (int i = 0; i < fieldList.size(); i++) {
                        String value = ExcelDisposeUtil.correspondingValue(
                                fieldList.get(i), obj,
                                fieldList.get(i).getAnnotation(ExcelField.class).dateType(),
                                fieldList.get(i).getAnnotation(ExcelField.class).decimalAfterDigit());
                        if (TextDispose.isNotEmpty(value)) {
                            if (row.getCell(i) != null) {
                                row.getCell(i).setCellValue(value);
                            } else {
                                row.createCell(i).setCellValue(value);
                            }
                        }
                    }
                    result = true;
                } else {
                    new ExcelOperateException("诊断：行数不正确非正整数或超过最大行数").printStackTrace();
                    result = false;
                }
            } else {
                new ExcelOperateException("诊断：对象中没有匹配的文档内容").printStackTrace();
                result = false;
            }
        } else {
            new ExcelOperateException("诊断：传入文档表名称错误").printStackTrace();
            result = false;
        }
        if (result) {
            this.writeDocument();
        }
        return result;
    }

    /**
     * 删除
     *
     * @param sheetName 页标签名称
     * @param rowNumber 行号
     * @return 是否删除成功
     */
    public boolean deleteRow(String sheetName, int rowNumber) {
        boolean result;
        if (TextDispose.isNotEmpty(sheetName)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (rowNumber >= 0 && rowNumber <= sheet.getLastRowNum()) {
                sheet.removeRow(sheet.getRow(rowNumber));
                result = true;
            } else {
                new ExcelOperateException("诊断：行数不正确非正整数或超过最大行数").printStackTrace();
                result = false;
            }
        } else {
            new ExcelOperateException("诊断：传入文档表名称错误").printStackTrace();
            result = false;
        }
        if (result) {
            this.writeDocument();
        }
        return result;
    }

    //------------------------------------------------------------------------------------------------------------------内部方法
    private void writeDocument() {
        try {
            if (TextDispose.isNotEmpty(path)) {
                FileOutputStream out = new FileOutputStream(this.path);
                if (workbook != null) {
                    workbook.write(out);
                }
                out.close();
            } else {
                throw new ExcelOperateException("诊断：传入文档地址为空");
            }
        } catch (IOException e) {
            throw new ExcelOperateException("诊断：Excel操作写入文件被占用");
        }
    }

    //------------------------------------------------------------------------------------------------------------------除了数据操作外的方法
    public void destroy() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            throw new ExcelOperateException("诊断：Excel操作类销毁失败");
        }
    }
}
