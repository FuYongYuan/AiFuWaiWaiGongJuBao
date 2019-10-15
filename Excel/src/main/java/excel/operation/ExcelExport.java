package excel.operation;

import dispose.DateDispose;
import dispose.TextDispose;
import enumerate.CommonlyUsedType;
import excel.annotation.ExcelField;
import excel.exception.ExcelOperateException;
import excel.operation.set.*;
import excel.util.ExcelDisposeUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;


/**
 * Excel文件导出
 */
public class ExcelExport {
    /**
     * 工作簿对象
     */
    private Workbook workbook;

    /**
     * 构造
     */
    public ExcelExport(Workbook workbook) {
        this.workbook = workbook;
    }

    /**
     * 工作簿对象
     */
    public Workbook getWorkbook() {
        return workbook;
    }

    /**
     * Excel导出
     *
     * @param sheetSets 需要产生的数据对象
     */
    public Workbook setExcel(List<SheetSet> sheetSets) {
        try {
            if (sheetSets != null && sheetSets.size() > 0) {
                //循环页签数组对象
                for (SheetSet sheetSet : sheetSets) {
                    //创建页签并给页签命名
                    Sheet sheetModel = this.workbook.createSheet(sheetSet.getWorkbookName());
                    //获取要执行的对象中属于Excel的字段
                    ExcelDisposeUtil.initialization(sheetSet.getFunction(), sheetSet.getSheetData(), sheetSet.getDataClass());
                    if (sheetSet.getSheetData().useField != null && sheetSet.getSheetData().useField.size() > 0) {
                        //初始化行数
                        int initRow = 0;
                        if (sheetSet.getExtraRowData() != null && sheetSet.getExtraRowData().size() > 0) {
                            initRow = this.getInitRow(sheetModel, sheetSet.getExtraRowData(), initRow);
                        }
                        //设置当前页签的第一行
                        Row row = sheetModel.createRow(initRow);
                        //循环标题（列名）
                        int titleCellSize = sheetSet.getSheetData().useField.size();
                        for (int i = 0; i < titleCellSize; i++) {
                            //创建列
                            Cell cell = row.createCell(i);
                            //设置样式
                            cell.setCellStyle(sheetSet.getStyle().getTitle());
                            //对应单元格的标题名
                            cell.setCellValue(sheetSet.getSheetData().useField.get(i).getAnnotation(ExcelField.class).columnName());
                            //列样式记录
                            sheetSet.getSheetData().cellStyleMap.put(sheetSet.getSheetData().useField.get(i).getName(), this.setFormat(sheetSet.getSheetData().useField.get(i)));


                            if (sheetSet.getSheetData().useField.get(i).getAnnotation(ExcelField.class).columnWidth() > 0) {
                                //设置列宽
                                sheetModel.setColumnWidth(i, sheetSet.getSheetData().useField.get(i).getAnnotation(ExcelField.class).columnWidth() * 256);
                            } else if (sheetSet.getSheetData().useField.get(i).getAnnotation(ExcelField.class).columnWidth() == 0) {
                                //是否隐藏当前列
                                sheetModel.setColumnHidden(i, true);
                            } else {
                                if (sheetSet.getSheetData().useField.get(i).getAnnotation(ExcelField.class).isHidden()) {
                                    //是否隐藏当前列
                                    sheetModel.setColumnHidden(i, true);
                                }
                                if (sheetSet.getSheetData().useField.get(i).getAnnotation(ExcelField.class).isAutoSize()) {
                                    //自动适应时要先跟踪
                                    if (this.workbook instanceof SXSSFWorkbook) {
                                        ((SXSSFSheet) sheetModel).trackAllColumnsForAutoSizing();
                                    }
                                    //是否自动适应列宽
                                    sheetModel.autoSizeColumn(i, true);
                                }
                            }
                        }

                        //共参
                        Map<String, TotalRowIndex> totalRowIndexMap = new HashMap<>();
                        List<Integer> occupyRows = new ArrayList<>();
                        //额外行
                        int extrai = 0;
                        //循环对象（值）
                        int rowSize = sheetSet.getWorkbookData().size();
                        for (int i = initRow; i < (rowSize + initRow + extrai); i++) {
                            //创建行    （标题的下一行）
                            Row nextrow = sheetModel.createRow(i + 1);

                            if (sheetSet.getExtraRowData() != null && sheetSet.getExtraRowData().size() > 0) {
                                for (ExtraRowData erd : sheetSet.getExtraRowData()) {
                                    if (erd != null) {
                                        if (!erd.getIsMaxRowNumber() && erd.getRowNumber() == nextrow.getRowNum()) {
                                            //抛错处理
                                            throw new ExcelOperateException("诊断：请勿在数据行中添加额外数据！错误行数：" + (erd.getRowNumber() + 1) + " 行", new RuntimeException());
                                        }
                                    }
                                }
                            }

                            //处理列
                            int rowCellSize = sheetSet.getSheetData().useField.size();
                            for (int j = 0; j < rowCellSize; j++) {
                                //创建列
                                Cell cell = nextrow.createCell(j);
                                //获取值和计算夸列
                                String cellValue = this.cellValueRowSpan(i, j, initRow, extrai, sheetModel, sheetSet, totalRowIndexMap);
                                //设置单元格格式
                                cell.setCellStyle(sheetSet.getSheetData().cellStyleMap.get(sheetSet.getSheetData().useField.get(j).getName()));
                                //赋值
                                this.setCellValue(sheetSet.getSheetData().useField.get(j), cell, cellValue);
                            }

                            //计算功能处理
                            //小计
                            if (sheetSet.getSheetData().subTotalReferenceField != null && totalRowIndexMap.get(sheetSet.getSheetData().subTotalReferenceField.getName()) != null) {
                                if (this.calculation(
                                        i,
                                        initRow,
                                        extrai,
                                        sheetModel,
                                        sheetSet,
                                        sheetSet.getFunction().getSubTotal(),
                                        sheetSet.getSheetData().subTotalReferenceField,
                                        sheetSet.getSheetData().subTotalSpanField,
                                        totalRowIndexMap,
                                        sheetSet.getSheetData().subTotalColumnIndex,
                                        occupyRows
                                )) {
                                    //校准变更的数据
                                    extrai = extrai + 1;
                                    i = i + 1;
                                }
                            }

                            //总计
                            if (sheetSet.getSheetData().totalReferenceField != null && totalRowIndexMap.get(sheetSet.getSheetData().totalReferenceField.getName()) != null) {
                                if (this.calculation(
                                        i,
                                        initRow,
                                        extrai,
                                        sheetModel,
                                        sheetSet,
                                        sheetSet.getFunction().getTotal(),
                                        sheetSet.getSheetData().totalReferenceField,
                                        sheetSet.getSheetData().totalSpanField,
                                        totalRowIndexMap,
                                        sheetSet.getSheetData().totalColumnIndex,
                                        occupyRows
                                )) {
                                    //校准变更的数据
                                    extrai = extrai + 1;
                                    i = i + 1;
                                }
                            }

                        }

                        //全部总计
                        if (
                                sheetSet.getFunction().getTotalAll().getCalculationFieldNameAndOrder() != null &&
                                        sheetSet.getFunction().getTotalAll().getCalculationFieldNameAndOrder().size() > 0
                        ) {
                            occupyRows.add(sheetSet.getWorkbookData().size() + initRow + extrai + 1);
                            this.calculationDispose(
                                    sheetSet.getWorkbookData().size() + initRow + extrai - 1,
                                    initRow + 1,
                                    sheetModel.getLastRowNum(),
                                    sheetModel,
                                    sheetSet,
                                    sheetSet.getFunction().getTotalAll(),
                                    sheetSet.getSheetData().totalAllColumnIndex,
                                    occupyRows
                            );
                        }
                    }

                    //额外数据
                    if (sheetSet.getExtraRowData() != null && sheetSet.getExtraRowData().size() > 0) {
                        for (ExtraRowData erd : sheetSet.getExtraRowData()) {
                            if (erd != null) {
                                if (erd.getIsMaxRowNumber()) {
                                    erd.setRowNumber(sheetModel.getLastRowNum() + 2);
                                    Row row = sheetModel.getRow(erd.getRowNumber());
                                    if (row == null) {
                                        row = sheetModel.createRow(erd.getRowNumber());
                                    }
                                    for (ExtraCellData ecd : erd.getExtraCellData()) {
                                        Cell cell = row.getCell(ecd.getCellNumber());
                                        if (cell == null) {
                                            cell = row.createCell(ecd.getCellNumber());
                                        }
                                        this.setCellValue(cell, ecd);
                                    }
                                } else if (erd.getRowNumber() > sheetModel.getLastRowNum()) {
                                    Row row = sheetModel.getRow(erd.getRowNumber());
                                    if (row == null) {
                                        row = sheetModel.createRow(erd.getRowNumber());
                                    }
                                    for (ExtraCellData ecd : erd.getExtraCellData()) {
                                        Cell cell = row.getCell(ecd.getCellNumber());
                                        if (cell == null) {
                                            cell = row.createCell(ecd.getCellNumber());
                                        }
                                        this.setCellValue(cell, ecd);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ExcelOperateException("诊断：Excel导出失败！", e);
        }
        return this.workbook;
    }

    /**
     * 赋值
     */
    private void setCellValue(Field field, Cell cell, String cellValue) {
        //结果转换
        if (cellValue != null) {
            if (TextDispose.isNumber(cellValue)) {
                //结果转换
                if (field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_double.getValue())) {
                    cell.setCellValue(Double.parseDouble(cellValue));
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Integer.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_int.getValue())) {
                    cell.setCellValue(Integer.parseInt(cellValue));
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_BigDecimal.getValue())) {
                    cell.setCellValue(new BigDecimal(cellValue).doubleValue());
                } else {
                    cell.setCellValue(cellValue);
                }
            } else {
                cell.setCellValue(cellValue);
            }
        } else {
            cell.setCellValue("");
        }
    }

    /**
     * 是否进行货币格式化
     */
    private CellStyle setFormat(Field field) {
        CellStyle cellStyle = this.workbook.createCellStyle();

        if (field.getAnnotation(ExcelField.class).isMoney()) {
            DataFormat format = this.workbook.createDataFormat();
            StringBuilder moneyFormat = new StringBuilder("#,##0");
            if ((field.getType().getName().equals(CommonlyUsedType.Type_BigDecimal.getValue())
                    || field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue())
                    || field.getType().getName().equals(CommonlyUsedType.Type_double.getValue()))
                    && field.getAnnotation(ExcelField.class).decimalAfterDigit() > 0
            ) {
                moneyFormat.append(".");
                int decimalAfterDigit = field.getAnnotation(ExcelField.class).decimalAfterDigit();
                for (int i = 0; i < decimalAfterDigit; i++) {
                    moneyFormat.append("0");
                }
            }
            cellStyle.setDataFormat(format.getFormat(moneyFormat.toString()));
        } else if ((field.getType().getName().equals(CommonlyUsedType.Type_BigDecimal.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_double.getValue()))
                && field.getAnnotation(ExcelField.class).decimalAfterDigit() > 0
        ) {
            DataFormat format = this.workbook.createDataFormat();
            StringBuilder sb = new StringBuilder("#0.");
            int decimalAfterDigit = field.getAnnotation(ExcelField.class).decimalAfterDigit();
            for (int i = 0; i < decimalAfterDigit; i++) {
                sb.append("0");
            }
            cellStyle.setDataFormat(format.getFormat(sb.toString()));
        }
        //水平位置
        cellStyle.setAlignment(field.getAnnotation(ExcelField.class).horizontalAlignment());
        //垂直位置
        cellStyle.setVerticalAlignment(field.getAnnotation(ExcelField.class).verticalAlignment());

        return cellStyle;
    }

    /**
     * 赋值
     */
    private void setCellValue(Cell cell, ExtraCellData ecd) {
        //结果转换
        if (ecd != null) {
            //结果转换
            if (ecd.getCellType() == Double.class || ecd.getCellType() == double.class) {
                cell.setCellValue(Double.parseDouble(ecd.getCellValue().toString()));
            } else if (ecd.getCellType() == Integer.class || ecd.getCellType() == int.class) {
                cell.setCellValue(Integer.parseInt(ecd.getCellValue().toString()));
            } else if (ecd.getCellType() == BigDecimal.class) {
                cell.setCellValue(new BigDecimal(ecd.getCellValue().toString()).doubleValue());
            } else if (ecd.getCellType() == Date.class) {
                cell.setCellValue(DateDispose.formatting_Date((Date) ecd.getCellValue(), ecd.getDateType()));
            } else {
                cell.setCellValue(ecd.getCellValue().toString());
            }
            cell.setCellStyle(this.setFormat(ecd));
        } else {
            cell.setCellValue("");
        }
    }

    /**
     * 是否进行货币格式化
     */
    private CellStyle setFormat(ExtraCellData ecd) {
        CellStyle cellStyle = this.workbook.createCellStyle();

        if (ecd.getIsMoney()) {
            DataFormat format = this.workbook.createDataFormat();
            StringBuilder moneyFormat = new StringBuilder("#,##0");
            if ((ecd.getCellType() == BigDecimal.class
                    || ecd.getCellType() == Double.class
                    || ecd.getCellType() == double.class)
                    && ecd.getDecimalAfterDigit() > 0
            ) {
                moneyFormat.append(".");
                int decimalAfterDigit = ecd.getDecimalAfterDigit();
                for (int i = 0; i < decimalAfterDigit; i++) {
                    moneyFormat.append("0");
                }
            }
            cellStyle.setDataFormat(format.getFormat(moneyFormat.toString()));
        } else if ((ecd.getCellType() == BigDecimal.class
                || ecd.getCellType() == Double.class
                || ecd.getCellType() == double.class)
                && ecd.getDecimalAfterDigit() > 0
        ) {
            DataFormat format = this.workbook.createDataFormat();
            StringBuilder sb = new StringBuilder("#0.");
            int decimalAfterDigit = ecd.getDecimalAfterDigit();
            for (int i = 0; i < decimalAfterDigit; i++) {
                sb.append("0");
            }
            cellStyle.setDataFormat(format.getFormat(sb.toString()));
        }
        //水平位置
        cellStyle.setAlignment(ecd.getHorizontalAlignment());
        //垂直位置
        cellStyle.setVerticalAlignment(ecd.getVerticalAlignment());

        return cellStyle;
    }

    /**
     * 获取值和计算夸列
     */
    private String cellValueRowSpan(
            int i,
            int j,
            int initRow,
            int extrai,
            Sheet sheetModel,
            SheetSet sheetSet,
            Map<String, TotalRowIndex> totalRowIndexMap
    ) {
        String cellValue = ExcelDisposeUtil.correspondingValue(
                sheetSet.getSheetData().useField.get(j), sheetSet.getWorkbookData().get(i - initRow - extrai), sheetSet.getIsGetMethodFieldValue(),
                sheetSet.getSheetData().useField.get(j).getAnnotation(ExcelField.class).dateType(),
                sheetSet.getSheetData().useField.get(j).getAnnotation(ExcelField.class).decimalAfterDigit());

        String rowspanAlignCellValue = cellValue;
        if (sheetSet.getSheetData().useField.get(j).getAnnotation(ExcelField.class).rowspanAlignOrder() > 0) {
            rowspanAlignCellValue = ExcelDisposeUtil.correspondingValue(
                    sheetSet.getSheetData().useField.get(sheetSet.getSheetData().useField.get(j).getAnnotation(ExcelField.class).rowspanAlignOrder() - 1), sheetSet.getWorkbookData().get(i - initRow - extrai), sheetSet.getIsGetMethodFieldValue(),
                    sheetSet.getSheetData().useField.get(sheetSet.getSheetData().useField.get(j).getAnnotation(ExcelField.class).rowspanAlignOrder() - 1).getAnnotation(ExcelField.class).dateType(),
                    sheetSet.getSheetData().useField.get(sheetSet.getSheetData().useField.get(j).getAnnotation(ExcelField.class).rowspanAlignOrder() - 1).getAnnotation(ExcelField.class).decimalAfterDigit());
        }

        //跨行处理
        if (sheetSet.getSheetData().useField.get(j).getAnnotation(ExcelField.class).rowspan()) {
            TotalRowIndex totalRowIndex = totalRowIndexMap.get(sheetSet.getSheetData().useField.get(j).getName());
            if (totalRowIndex == null) {
                totalRowIndex = new TotalRowIndex();
                totalRowIndex.field = sheetSet.getSheetData().useField.get(j);
                totalRowIndex.columnNo = j;
            }
            if (totalRowIndex.rowspanStart == 0) {
                totalRowIndex.rowspanStart = i + 1;
                totalRowIndex.oldCellValue = rowspanAlignCellValue;
            } else {
                if (totalRowIndex.oldCellValue != null && totalRowIndex.oldCellValue.equals(rowspanAlignCellValue)) {
                    totalRowIndex.rowspanEnd = i + 1;
                } else {
                    if (totalRowIndex.rowspanEnd > 0 && totalRowIndex.rowspanStart != totalRowIndex.rowspanEnd) {
                        CellRangeAddress region = new CellRangeAddress(totalRowIndex.rowspanStart, totalRowIndex.rowspanEnd, j, j);
                        totalRowIndex.regionIndex = sheetModel.addMergedRegion(region);
                    }

                    totalRowIndex.rowspanStart = i + 1;
                    totalRowIndex.rowspanEnd = i + 1;
                    totalRowIndex.oldCellValue = rowspanAlignCellValue;
                }

                if (i == sheetSet.getWorkbookData().size() + initRow + extrai - 1) {
                    if (totalRowIndex.rowspanStart != totalRowIndex.rowspanEnd) {
                        CellRangeAddress region = new CellRangeAddress(totalRowIndex.rowspanStart, totalRowIndex.rowspanEnd, j, j);
                        totalRowIndex.regionIndex = sheetModel.addMergedRegion(region);
                    }
                }
            }
            totalRowIndexMap.put(sheetSet.getSheetData().useField.get(j).getName(), totalRowIndex);
        }

        return cellValue;
    }

    /**
     * 计算功能
     */
    private boolean calculation(
            int i,
            int initRow,
            int extrai,
            Sheet sheetModel,
            SheetSet sheetSet,
            Function.Calculation calculation,
            Field referenceField,
            List<Field> spanFieldNames,
            Map<String, TotalRowIndex> totalRowIndexMap,
            List<Integer> totalColumnIndexs,
            List<Integer> occupyRows
    ) throws IllegalAccessException {
        if (referenceField != null) {
            TotalRowIndex totalRowIndex = totalRowIndexMap.get(referenceField.getName());
            if ((i - initRow - extrai + 1) < sheetSet.getWorkbookData().size()) {
                Object oldcv = referenceField.get(sheetSet.getWorkbookData().get(i - initRow - extrai));
                Object cv = referenceField.get(sheetSet.getWorkbookData().get(i - initRow - extrai + 1));
                if (oldcv != null && !oldcv.equals(cv)) {
                    if (this.calculationDispose(
                            i,
                            totalRowIndex.rowspanStart,
                            totalRowIndex.rowspanEnd,
                            sheetModel,
                            sheetSet,
                            calculation,
                            totalColumnIndexs,
                            occupyRows
                    )) {
                        occupyRows.add(i + 2);
                        //重新合并
                        if (calculation.getSpanFieldNames() != null && calculation.getSpanFieldNames().size() > 0) {
                            this.againSpan(
                                    i,
                                    initRow,
                                    extrai,
                                    sheetModel,
                                    sheetSet,
                                    spanFieldNames,
                                    totalRowIndexMap
                            );
                        }
                        return true;
                    }
                }
            } else {
                occupyRows.add(i + 2);
                if (this.calculationDispose(
                        i,
                        totalRowIndex.rowspanStart,
                        totalRowIndex.rowspanEnd,
                        sheetModel,
                        sheetSet,
                        calculation,
                        totalColumnIndexs,
                        occupyRows
                )) {
                    //重新合并
                    if (calculation.getSpanFieldNames() != null && calculation.getSpanFieldNames().size() > 0) {
                        this.againSpan(
                                i,
                                initRow,
                                extrai,
                                sheetModel,
                                sheetSet,
                                spanFieldNames,
                                totalRowIndexMap
                        );
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 计算功能处理
     */
    private boolean calculationDispose(
            int i,
            int rowspanStart,
            int rowspanEnd,
            Sheet sheetModel,
            SheetSet sheetSet,
            Function.Builder calculation,
            List<Integer> totalColumnIndexs,
            List<Integer> occupyRows
    ) {
        if (totalColumnIndexs != null) {
            //创建行    （标题的下一行）
            Row nextRow = sheetModel.createRow(i + 2);
            //循环处理要统计的列
            int size = sheetSet.getSheetData().useField.size();
            for (int j = 0; j < size; j++) {
                //创建列
                Cell cell;

                boolean isDeviation = false;

                Integer nameValue = calculation.getCalculationFieldNameAndOrder().get(sheetSet.getSheetData().useField.get(j).getName());
                Integer annotationValue = calculation.getCalculationFieldNameAndOrder().get(sheetSet.getSheetData().useField.get(j).getAnnotation(ExcelField.class).columnName());
                if (annotationValue != null && annotationValue > 0) {
                    cell = nextRow.createCell(annotationValue - 1);
                    isDeviation = true;
                } else if (nameValue != null && nameValue > 0) {
                    //创建列
                    cell = nextRow.createCell(nameValue - 1);
                    isDeviation = true;
                } else {
                    if (nextRow.getCell(j) != null) {
                        cell = nextRow.getCell(j);
                    } else {
                        //创建列
                        cell = nextRow.createCell(j);
                    }
                }

                //设置样式
                cell.setCellStyle(calculation.getStyle());

                //需要计算的列
                if (totalColumnIndexs.contains(j + 1)) {
                    String colString = CellReference.convertNumToColString(j);  //长度转成ABC列

                    StringBuilder sb = new StringBuilder();
                    int rs = rowspanStart;
                    for (Integer occupy : occupyRows) {
                        if ((rs + 1) <= occupy && occupy <= (rowspanEnd + 1)) {
                            while (occupyRows.contains(rs) && rs < rowspanEnd) {
                                rs = rs + 1;
                            }
                            if (!occupyRows.contains(rs)) {
                                boolean rowspan = sheetSet.getSheetData().useField.get(j).getAnnotation(ExcelField.class).rowspan();
                                int o = occupy;
                                if (rowspan) {
                                    o = rs + 1;
                                }
                                sb.append(colString).append(rs + 1).append(":").append(colString).append(o).append(",");

                                int occupyi = occupy + 1;
                                if (occupyi <= rowspanEnd) {
                                    if (occupyi > rs) {
                                        rs = occupyi;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    String sumString;
                    if (sb.length() > 0) {
                        sb.delete(sb.length() - 1, sb.length());
                        sumString = "SUM(" + sb.toString() + ")";
                    } else {
                        boolean rowspan = sheetSet.getSheetData().useField.get(j).getAnnotation(ExcelField.class).rowspan();
                        int o = rowspanEnd;
                        if (rowspan) {
                            o = rowspanStart;
                        }
                        sumString = "SUM(" + colString + (rowspanStart + 1) + ":" + colString + (o + 1) + ")";
                    }
                    cell.setCellFormula(sumString);
                }

                if (isDeviation) {
                    if (!calculation.getCalculationFieldNameAndOrder().containsValue(j + 1)) {
                        //创建列
                        cell = nextRow.createCell(j);
                        //设置样式
                        cell.setCellStyle(calculation.getStyle());
                    }
                }

                //整行标注
                if (j == (calculation.getExplainOrder() - 1)) {
                    this.setCellValue(sheetSet.getSheetData().useField.get(j), cell, calculation.getExplain());
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 重新计算跨行
     */
    private void againSpan(
            int i,
            int initRow,
            int extrai,
            Sheet sheetModel,
            SheetSet sheetSet,
            List<Field> spanFieldNames,
            Map<String, TotalRowIndex> totalRowIndexMap
    ) throws IllegalAccessException {
        for (Field field : spanFieldNames) {
            if (field != null) {
                if ((i - initRow - extrai + 1) < sheetSet.getWorkbookData().size()) {
                    Object oldcv = field.get(sheetSet.getWorkbookData().get(i - initRow - extrai));
                    Object cv = field.get(sheetSet.getWorkbookData().get(i - initRow - extrai + 1));
                    if (oldcv != null && !oldcv.equals(cv)) {
                        totalRowIndexMap.get(field.getName()).rowspanEnd = totalRowIndexMap.get(field.getName()).rowspanEnd + 1;
                    }
                } else {
                    totalRowIndexMap.get(field.getName()).rowspanEnd = totalRowIndexMap.get(field.getName()).rowspanEnd + 1;
                    List<CellRangeAddress> cellRangeAddressList = sheetModel.getMergedRegions();

                    int regionIndex = 0;
                    if (this.workbook instanceof XSSFWorkbook || this.workbook instanceof SXSSFWorkbook) {
                        if (totalRowIndexMap.get(field.getName()).regionIndex > 0) {
                            regionIndex = totalRowIndexMap.get(field.getName()).regionIndex - 1;
                        }
                    } else if (this.workbook instanceof HSSFWorkbook) {
                        regionIndex = totalRowIndexMap.get(field.getName()).regionIndex;
                    }

                    CellRangeAddress cellAddresses = sheetModel.getMergedRegion(regionIndex);
                    if (cellRangeAddressList.contains(cellAddresses)) {
                        sheetModel.removeMergedRegion(cellRangeAddressList.indexOf(cellAddresses));
                    }
                    CellRangeAddress region = new CellRangeAddress(totalRowIndexMap.get(field.getName()).rowspanStart, totalRowIndexMap.get(field.getName()).rowspanEnd, totalRowIndexMap.get(field.getName()).columnNo, totalRowIndexMap.get(field.getName()).columnNo);
                    totalRowIndexMap.get(field.getName()).regionIndex = sheetModel.addMergedRegion(region);
                }
            }
        }
    }

    /**
     * 额外数据新增开始行数
     */
    private int getInitRow(Sheet sheetModel, List<ExtraRowData> extraRowData, int initRow) {
        ExtraRowData erd = null;
        for (ExtraRowData e : extraRowData) {
            if (e != null && e.getRowNumber() != null && e.getRowNumber() == initRow && e.getIsNewRow()) {
                erd = e;
            }
        }
        if (erd != null) {
            Row row = sheetModel.getRow(initRow);
            if (row == null) {
                row = sheetModel.createRow(initRow);
            }
            for (ExtraCellData ecd : erd.getExtraCellData()) {
                Cell cell = row.getCell(ecd.getCellNumber());
                if (cell == null) {
                    cell = row.createCell(ecd.getCellNumber());
                }
                this.setCellValue(cell, ecd);
            }
            initRow = initRow + 1;
            return this.getInitRow(sheetModel, extraRowData, initRow);
        }
        return initRow;
    }
}
