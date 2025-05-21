package excel.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Map;

/**
 * Excel复制工作表
 *
 * @author fyy
 */
public class ExcelCopySheets {

    /**
     * 复制工作表
     *
     * @param srcWorkbook  源工作簿
     * @param destWorkbook 目标工作簿
     */
    public static void copySheets(XSSFWorkbook srcWorkbook, XSSFWorkbook destWorkbook) {
        for (int i = 0; i < srcWorkbook.getNumberOfSheets(); i++) {
            Sheet srcSheet = srcWorkbook.getSheetAt(i);
            Sheet destSheet = destWorkbook.createSheet(srcSheet.getSheetName());
            copySheet(srcSheet, destSheet);
        }
    }

    /**
     * 复制工作表
     *
     * @param srcSheet  源工作表
     * @param destSheet 目标工作表
     */
    private static void copySheet(Sheet srcSheet, Sheet destSheet) {
        int maxColumnNum = 0;
        Map<Integer, XSSFCellStyle> styleMap = new HashMap<>();

        for (int i = srcSheet.getFirstRowNum(); i <= srcSheet.getLastRowNum(); i++) {
            Row srcRow = srcSheet.getRow(i);
            Row destRow = destSheet.createRow(i);
            if (srcRow != null) {
                copyRow(srcRow, destRow, styleMap);
                if (srcRow.getLastCellNum() > maxColumnNum) {
                    maxColumnNum = srcRow.getLastCellNum();
                }
            }
        }

        for (int i = 0; i <= maxColumnNum; i++) {
            destSheet.setColumnWidth(i, srcSheet.getColumnWidth(i));
        }

        for (int i = 0; i < srcSheet.getNumMergedRegions(); i++) {
            destSheet.addMergedRegion(srcSheet.getMergedRegion(i));
        }
    }

    /**
     * 复制行
     *
     * @param srcRow   源行
     * @param destRow  目标行
     * @param styleMap 样式映射
     */
    private static void copyRow(Row srcRow, Row destRow, Map<Integer, XSSFCellStyle> styleMap) {
        destRow.setHeight(srcRow.getHeight());

        for (int j = srcRow.getFirstCellNum(); j < srcRow.getLastCellNum(); j++) {
            Cell srcCell = srcRow.getCell(j);
            Cell destCell = destRow.createCell(j);

            if (srcCell != null) {
                if (styleMap != null) {
                    int stHashCode = srcCell.getCellStyle().hashCode();
                    XSSFCellStyle newCellStyle = styleMap.get(stHashCode);
                    if (newCellStyle == null) {
                        newCellStyle = (XSSFCellStyle) destCell.getSheet().getWorkbook().createCellStyle();
                        newCellStyle.cloneStyleFrom(srcCell.getCellStyle());
                        styleMap.put(stHashCode, newCellStyle);
                    }
                    destCell.setCellStyle(newCellStyle);
                }
                switch (srcCell.getCellType()) {
                    case STRING:
                        destCell.setCellValue(srcCell.getRichStringCellValue());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(srcCell)) {
                            destCell.setCellValue(srcCell.getDateCellValue());
                        } else {
                            destCell.setCellValue(srcCell.getNumericCellValue());
                        }
                        break;
                    case BOOLEAN:
                        destCell.setCellValue(srcCell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        destCell.setCellFormula(srcCell.getCellFormula());
                        break;
                    case BLANK:
                        destCell.setBlank();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
