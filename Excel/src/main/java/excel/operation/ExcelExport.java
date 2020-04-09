package excel.operation;

import dispose.DateDispose;
import dispose.TextDispose;
import enumerate.CommonlyUsedType;
import excel.annotation.ExcelField;
import excel.exception.ExcelOperateException;
import excel.operation.set.*;
import excel.util.ExcelDisposeUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
            if (sheetSets != null && !sheetSets.isEmpty()) {
                //循环页签数组对象
                for (SheetSet sheetSet : sheetSets) {
                    //创建页签并给页签命名
                    Sheet sheetModel = this.workbook.createSheet(sheetSet.getSheetName());
                    //获取要执行的对象中属于Excel的字段
                    ExcelDisposeUtil.initialization(sheetSet);
                    if (sheetSet.getSheetCache().useField != null && !sheetSet.getSheetCache().useField.isEmpty()) {
                        //初始化行数
                        int initRow = 0;
                        if (sheetSet.getExtraRowData() != null && !sheetSet.getExtraRowData().isEmpty()) {
                            initRow = this.getInitRow(sheetModel, sheetSet.getExtraRowData(), initRow, sheetSet.getStyle());
                        }
                        //初始化样式
                        if (sheetSet.getStyle().getContextBorder() != null && sheetSet.getStyle().getContextBorder() != BorderStyle.NONE) {
                            sheetSet.getStyle().setTitleBorder(sheetSet.getStyle().getContextBorder());
                        }
                        if (sheetSet.getStyle().getContextBorderColor() != null) {
                            sheetSet.getStyle().setTitleBorderColor(sheetSet.getStyle().getContextBorderColor());
                        }
                        //设置当前页签的第一行
                        Row row = sheetModel.createRow(initRow);
                        //循环标题（列名）
                        int titleCellSize = sheetSet.getSheetCache().useField.size();
                        for (int i = 0; i < titleCellSize; i++) {
                            //获取字段
                            Field field = sheetSet.getSheetCache().useField.get(i);
                            //获取字段注解
                            ExcelField excelField = field.getAnnotation(ExcelField.class);
                            //创建列
                            Cell cell = row.createCell(i);
                            //设置样式
                            cell.setCellStyle(sheetSet.getStyle().getTitle());
                            //对应单元格的标题名
                            cell.setCellValue(excelField.columnName());
                            //列样式记录
                            sheetSet.getSheetCache().cellStyleMap.put(field.getName(), this.getCellStyle(field, excelField, sheetSet.getStyle()));


                            if (excelField.columnWidth() > 0) {
                                //设置列宽
                                sheetModel.setColumnWidth(i, excelField.columnWidth() * 256);
                            } else if (excelField.columnWidth() == 0) {
                                //是否隐藏当前列
                                sheetModel.setColumnHidden(i, true);
                            } else {
                                if (excelField.isHidden()) {
                                    //是否隐藏当前列
                                    sheetModel.setColumnHidden(i, true);
                                }
                                if (excelField.isAutoSize()) {
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
                        int rowSize = sheetSet.getSheetData().size();
                        for (int i = initRow; i < (rowSize + initRow + extrai); i++) {
                            //创建行    （标题的下一行）
                            Row nextrow = sheetModel.createRow(i + 1);

                            if (sheetSet.getExtraRowData() != null && !sheetSet.getExtraRowData().isEmpty()) {
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
                            int rowCellSize = sheetSet.getSheetCache().useField.size();
                            for (int j = 0; j < rowCellSize; j++) {
                                //获取字段
                                Field field = sheetSet.getSheetCache().useField.get(j);
                                //获取字段注解
                                ExcelField excelField = field.getAnnotation(ExcelField.class);
                                //创建列
                                Cell cell = nextrow.createCell(j);
                                //获取值和计算夸列
                                String cellValue = this.cellValueRowSpan(i, j, initRow, extrai, sheetModel, sheetSet, totalRowIndexMap, field, excelField);
                                //设置单元格格式
                                cell.setCellStyle(sheetSet.getSheetCache().cellStyleMap.get(field.getName()));
                                //判断是否需要赋值
                                boolean isSetValue = true;
                                if (!totalRowIndexMap.isEmpty()) {
                                    TotalRowIndex totalRowIndex = totalRowIndexMap.get(field.getName());
                                    if (totalRowIndex != null) {
                                        if (totalRowIndex.rowspanStart != (i + 1) && totalRowIndex.rowspanEnd != 0 && totalRowIndex.rowspanStart < (i + 1) && (i + 1) <= totalRowIndex.rowspanEnd) {
                                            isSetValue = false;
                                        }
                                    }
                                }
                                if (isSetValue) {
                                    //赋值
                                    this.setCellValue(field, cell, cellValue);
                                }
                            }

                            //计算功能处理
                            //小计
                            if (sheetSet.getSheetCache().subTotalReferenceField != null && totalRowIndexMap.get(sheetSet.getSheetCache().subTotalReferenceField.getName()) != null) {
                                if (this.calculation(
                                        i,
                                        initRow,
                                        extrai,
                                        sheetModel,
                                        sheetSet,
                                        sheetSet.getFunction().getSubTotal(),
                                        sheetSet.getSheetCache().subTotalReferenceField,
                                        sheetSet.getSheetCache().subTotalSpanField,
                                        totalRowIndexMap,
                                        sheetSet.getSheetCache().subTotalColumnIndex,
                                        occupyRows
                                )) {
                                    //校准变更的数据
                                    extrai = extrai + 1;
                                    i = i + 1;
                                }
                            }

                            //总计
                            if (sheetSet.getSheetCache().totalReferenceField != null && totalRowIndexMap.get(sheetSet.getSheetCache().totalReferenceField.getName()) != null) {
                                if (this.calculation(
                                        i,
                                        initRow,
                                        extrai,
                                        sheetModel,
                                        sheetSet,
                                        sheetSet.getFunction().getTotal(),
                                        sheetSet.getSheetCache().totalReferenceField,
                                        sheetSet.getSheetCache().totalSpanField,
                                        totalRowIndexMap,
                                        sheetSet.getSheetCache().totalColumnIndex,
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
                            occupyRows.add(sheetSet.getSheetData().size() + initRow + extrai + 1);
                            this.calculationDispose(
                                    sheetSet.getSheetData().size() + initRow + extrai - 1,
                                    initRow + 1,
                                    sheetModel.getLastRowNum(),
                                    sheetModel,
                                    sheetSet,
                                    sheetSet.getFunction().getTotalAll(),
                                    sheetSet.getSheetCache().totalAllColumnIndex,
                                    occupyRows
                            );
                        }
                    }

                    //额外数据
                    if (sheetSet.getExtraRowData() != null && !sheetSet.getExtraRowData().isEmpty()) {
                        for (ExtraRowData erd : sheetSet.getExtraRowData()) {
                            if (erd != null) {
                                if (erd.getIsMaxRowNumber()) {
                                    erd.setRowNumber(sheetModel.getLastRowNum() + 2);
                                    this.setExtraData(sheetModel, erd, sheetSet.getStyle());
                                } else if (erd.getRowNumber() > sheetModel.getLastRowNum()) {
                                    this.setExtraData(sheetModel, erd, sheetSet.getStyle());
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
     * 获取单元格样式
     */
    private CellStyle getCellStyle(Field field, ExcelField excelField, Style globalStyle) {
        CellStyle cellStyle = this.workbook.createCellStyle();

        if (excelField.isMoney()) {
            DataFormat format = this.workbook.createDataFormat();
            StringBuilder moneyFormat = new StringBuilder("#,##0");
            if ((field.getType().getName().equals(CommonlyUsedType.Type_BigDecimal.getValue())
                    || field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue())
                    || field.getType().getName().equals(CommonlyUsedType.Type_double.getValue()))
                    && excelField.decimalAfterDigit() > 0
            ) {
                moneyFormat.append(".");
                int decimalAfterDigit = excelField.decimalAfterDigit();
                for (int i = 0; i < decimalAfterDigit; i++) {
                    moneyFormat.append("0");
                }
            }
            cellStyle.setDataFormat(format.getFormat(moneyFormat.toString()));
        } else if ((field.getType().getName().equals(CommonlyUsedType.Type_BigDecimal.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_double.getValue()))
                && excelField.decimalAfterDigit() > 0
        ) {
            DataFormat format = this.workbook.createDataFormat();
            StringBuilder sb = new StringBuilder("#0.");
            int decimalAfterDigit = excelField.decimalAfterDigit();
            for (int i = 0; i < decimalAfterDigit; i++) {
                sb.append("0");
            }
            cellStyle.setDataFormat(format.getFormat(sb.toString()));
        }
        //水平位置
        cellStyle.setAlignment(excelField.horizontalAlignment());
        //垂直位置
        cellStyle.setVerticalAlignment(excelField.verticalAlignment());
        //边框
        if (globalStyle.getContextBorder() != null && globalStyle.getContextBorder() != BorderStyle.NONE) {
            cellStyle.setBorderTop(globalStyle.getContextBorder());
            cellStyle.setBorderBottom(globalStyle.getContextBorder());
            cellStyle.setBorderLeft(globalStyle.getContextBorder());
            cellStyle.setBorderRight(globalStyle.getContextBorder());

            if (globalStyle.getContextBorderColor() != null) {
                cellStyle.setTopBorderColor(globalStyle.getContextBorderColor().getIndex());
                cellStyle.setBottomBorderColor(globalStyle.getContextBorderColor().getIndex());
                cellStyle.setLeftBorderColor(globalStyle.getContextBorderColor().getIndex());
                cellStyle.setRightBorderColor(globalStyle.getContextBorderColor().getIndex());
            }
        } else {
            if (excelField.border() != BorderStyle.NONE) {
                cellStyle.setBorderTop(excelField.border());
                cellStyle.setBorderBottom(excelField.border());
                cellStyle.setBorderLeft(excelField.border());
                cellStyle.setBorderRight(excelField.border());

                cellStyle.setTopBorderColor(excelField.borderColor().getIndex());
                cellStyle.setBottomBorderColor(excelField.borderColor().getIndex());
                cellStyle.setLeftBorderColor(excelField.borderColor().getIndex());
                cellStyle.setRightBorderColor(excelField.borderColor().getIndex());
            } else {
                if (excelField.borderTop() != BorderStyle.NONE) {
                    cellStyle.setBorderTop(excelField.borderTop());
                    cellStyle.setTopBorderColor(excelField.borderTopColor().getIndex());
                }
                if (excelField.borderBottom() != BorderStyle.NONE) {
                    cellStyle.setBorderBottom(excelField.borderBottom());
                    cellStyle.setBottomBorderColor(excelField.borderBottomColor().getIndex());
                }
                if (excelField.borderLeft() != BorderStyle.NONE) {
                    cellStyle.setBorderLeft(excelField.borderLeft());
                    cellStyle.setLeftBorderColor(excelField.borderLeftColor().getIndex());
                }
                if (excelField.borderRight() != BorderStyle.NONE) {
                    cellStyle.setBorderRight(excelField.borderRight());
                    cellStyle.setRightBorderColor(excelField.borderRightColor().getIndex());
                }
            }
        }

        return cellStyle;
    }

    /**
     * 赋值
     */
    private void setCellValue(Sheet sheetModel, Cell cell, ExtraCellData ecd, Style globalStyle, CellRangeAddress cellAddresses) {
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
            cell.setCellStyle(this.getCellStyle(sheetModel, ecd, globalStyle, cellAddresses));
        } else {
            cell.setCellValue("");
        }
    }

    /**
     * 获取单元格样式
     */
    private CellStyle getCellStyle(Sheet sheetModel, ExtraCellData ecd, Style globalStyle, CellRangeAddress cellAddresses) {
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
        if (ecd.getFillPattern() != FillPatternType.NO_FILL) {
            cellStyle.setFillPattern(ecd.getFillPattern());
            if (ecd.getFillForegroundColor() != ((short) -1)) {
                cellStyle.setFillForegroundColor(ecd.getFillForegroundColor());
            }
        }
        if (ecd.getIsBold()) {
            Font font = this.workbook.createFont();
            font.setBold(true);
            cellStyle.setFont(font);
        }
        //水平位置
        cellStyle.setAlignment(ecd.getHorizontalAlignment());
        //垂直位置
        cellStyle.setVerticalAlignment(ecd.getVerticalAlignment());
        //边框
        if (globalStyle.getContextBorder() != null && globalStyle.getContextBorder() != BorderStyle.NONE) {
            //处理原单元格
            cellStyle.setBorderTop(globalStyle.getContextBorder());
            cellStyle.setBorderBottom(globalStyle.getContextBorder());
            cellStyle.setBorderLeft(globalStyle.getContextBorder());
            cellStyle.setBorderRight(globalStyle.getContextBorder());

            if (globalStyle.getContextBorderColor() != null) {
                cellStyle.setTopBorderColor(globalStyle.getContextBorderColor().getIndex());
                cellStyle.setBottomBorderColor(globalStyle.getContextBorderColor().getIndex());
                cellStyle.setLeftBorderColor(globalStyle.getContextBorderColor().getIndex());
                cellStyle.setRightBorderColor(globalStyle.getContextBorderColor().getIndex());
            }
            //处理合并单元格
            if (cellAddresses != null) {
                RegionUtil.setBorderTop(globalStyle.getContextBorder(), cellAddresses, sheetModel);
                RegionUtil.setBorderBottom(globalStyle.getContextBorder(), cellAddresses, sheetModel);
                RegionUtil.setBorderLeft(globalStyle.getContextBorder(), cellAddresses, sheetModel);
                RegionUtil.setBorderRight(globalStyle.getContextBorder(), cellAddresses, sheetModel);

                if (globalStyle.getContextBorderColor() != null) {
                    RegionUtil.setTopBorderColor(globalStyle.getContextBorderColor().getIndex(), cellAddresses, sheetModel);
                    RegionUtil.setBottomBorderColor(globalStyle.getContextBorderColor().getIndex(), cellAddresses, sheetModel);
                    RegionUtil.setLeftBorderColor(globalStyle.getContextBorderColor().getIndex(), cellAddresses, sheetModel);
                    RegionUtil.setRightBorderColor(globalStyle.getContextBorderColor().getIndex(), cellAddresses, sheetModel);
                }
            }
        } else {
            //处理原单元格
            if (ecd.getBorder() != BorderStyle.NONE) {
                cellStyle.setBorderTop(ecd.getBorder());
                cellStyle.setBorderBottom(ecd.getBorder());
                cellStyle.setBorderLeft(ecd.getBorder());
                cellStyle.setBorderRight(ecd.getBorder());

                cellStyle.setTopBorderColor(ecd.getBorderColor().getIndex());
                cellStyle.setBottomBorderColor(ecd.getBorderColor().getIndex());
                cellStyle.setLeftBorderColor(ecd.getBorderColor().getIndex());
                cellStyle.setRightBorderColor(ecd.getBorderColor().getIndex());
            } else {
                if (ecd.getBorderTop() != BorderStyle.NONE) {
                    cellStyle.setBorderTop(ecd.getBorderTop());
                    cellStyle.setTopBorderColor(ecd.getBorderTopColor().getIndex());
                }
                if (ecd.getBorderBottom() != BorderStyle.NONE) {
                    cellStyle.setBorderBottom(ecd.getBorderBottom());
                    cellStyle.setBottomBorderColor(ecd.getBorderBottomColor().getIndex());
                }
                if (ecd.getBorderLeft() != BorderStyle.NONE) {
                    cellStyle.setBorderLeft(ecd.getBorderLeft());
                    cellStyle.setLeftBorderColor(ecd.getBorderLeftColor().getIndex());
                }
                if (ecd.getBorderRight() != BorderStyle.NONE) {
                    cellStyle.setBorderRight(ecd.getBorderRight());
                    cellStyle.setRightBorderColor(ecd.getBorderRightColor().getIndex());
                }
            }
            //处理合并单元格
            if (cellAddresses != null) {
                if (ecd.getBorder() != BorderStyle.NONE) {
                    RegionUtil.setBorderTop(ecd.getBorder(), cellAddresses, sheetModel);
                    RegionUtil.setBorderBottom(ecd.getBorder(), cellAddresses, sheetModel);
                    RegionUtil.setBorderLeft(ecd.getBorder(), cellAddresses, sheetModel);
                    RegionUtil.setBorderRight(ecd.getBorder(), cellAddresses, sheetModel);

                    RegionUtil.setTopBorderColor(ecd.getBorderColor().getIndex(), cellAddresses, sheetModel);
                    RegionUtil.setBottomBorderColor(ecd.getBorderColor().getIndex(), cellAddresses, sheetModel);
                    RegionUtil.setLeftBorderColor(ecd.getBorderColor().getIndex(), cellAddresses, sheetModel);
                    RegionUtil.setRightBorderColor(ecd.getBorderColor().getIndex(), cellAddresses, sheetModel);
                } else {
                    if (ecd.getBorderTop() != BorderStyle.NONE) {
                        RegionUtil.setBorderTop(ecd.getBorderTop(), cellAddresses, sheetModel);
                        RegionUtil.setTopBorderColor(ecd.getBorderTopColor().getIndex(), cellAddresses, sheetModel);
                    }
                    if (ecd.getBorderBottom() != BorderStyle.NONE) {
                        RegionUtil.setBorderBottom(ecd.getBorderBottom(), cellAddresses, sheetModel);
                        RegionUtil.setBottomBorderColor(ecd.getBorderBottomColor().getIndex(), cellAddresses, sheetModel);
                    }
                    if (ecd.getBorderLeft() != BorderStyle.NONE) {
                        RegionUtil.setBorderLeft(ecd.getBorderLeft(), cellAddresses, sheetModel);
                        RegionUtil.setLeftBorderColor(ecd.getBorderLeftColor().getIndex(), cellAddresses, sheetModel);
                    }
                    if (ecd.getBorderRight() != BorderStyle.NONE) {
                        RegionUtil.setBorderRight(ecd.getBorderRight(), cellAddresses, sheetModel);
                        RegionUtil.setRightBorderColor(ecd.getBorderRightColor().getIndex(), cellAddresses, sheetModel);
                    }
                }
            }
        }

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
            Map<String, TotalRowIndex> totalRowIndexMap,
            Field field,
            ExcelField excelField
    ) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //当前数据行
        int current = i - initRow - extrai;
        //当前的字段值
        String cellValue = ExcelDisposeUtil.correspondingValue(
                field, sheetSet.getSheetData().get(current), sheetSet.getIsGetMethodFieldValue(),
                excelField.dateType(),
                excelField.decimalAfterDigit());

        //获取转换值集中对应值
        cellValue = ExcelDisposeUtil.getValueLimit(sheetSet, cellValue, excelField);

        //跨行处理
        if (excelField.rowspan()) {
            //上一数据行
            int previous = current - 1;
            //下一数据行
            int next = current + 1;
            //是否是最后一行
            boolean isLast = (i == sheetSet.getSheetData().size() + initRow + extrai - 1);
            //获取参考字段
            Field referenceField = null;
            //获取字段注解
            ExcelField referenceExcelField = null;
            if (excelField.rowspanAlignOrder() > 0) {
                //参考数据行
                int reference = excelField.rowspanAlignOrder() - 1;
                referenceField = sheetSet.getSheetCache().useField.get(reference);
                referenceExcelField = referenceField.getAnnotation(ExcelField.class);
            }
            //参考列值
            String rowspanAlignCellValue = cellValue;
            if (referenceField != null && referenceExcelField != null) {
                rowspanAlignCellValue = ExcelDisposeUtil.correspondingValue(
                        referenceField, sheetSet.getSheetData().get(current), sheetSet.getIsGetMethodFieldValue(),
                        referenceExcelField.dateType(),
                        referenceExcelField.decimalAfterDigit());
            }
            //对比列下一行值
            String cv = null;

            if (next < sheetSet.getSheetData().size()) {
                if (excelField.rowspanAlignOrder() == 0) {
                    cv = ExcelDisposeUtil.correspondingValue(
                            field, sheetSet.getSheetData().get(next), sheetSet.getIsGetMethodFieldValue(),
                            excelField.dateType(),
                            excelField.decimalAfterDigit());
                } else if (referenceField != null && referenceExcelField != null) {
                    cv = ExcelDisposeUtil.correspondingValue(
                            referenceField, sheetSet.getSheetData().get(next), sheetSet.getIsGetMethodFieldValue(),
                            referenceExcelField.dateType(),
                            referenceExcelField.decimalAfterDigit());
                }
            } else if (isLast && sheetSet.getSheetData().size() > 1) {
                if (excelField.rowspanAlignOrder() == 0) {
                    cv = ExcelDisposeUtil.correspondingValue(
                            field, sheetSet.getSheetData().get(previous), sheetSet.getIsGetMethodFieldValue(),
                            excelField.dateType(),
                            excelField.decimalAfterDigit());
                } else if (referenceField != null && referenceExcelField != null) {
                    cv = ExcelDisposeUtil.correspondingValue(
                            referenceField, sheetSet.getSheetData().get(previous), sheetSet.getIsGetMethodFieldValue(),
                            referenceExcelField.dateType(),
                            referenceExcelField.decimalAfterDigit());
                }
            }
            //开始计算合并
            TotalRowIndex totalRowIndex = totalRowIndexMap.get(field.getName());
            if (totalRowIndex == null) {
                totalRowIndex = new TotalRowIndex();
                totalRowIndex.field = field;
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
                        totalRowIndex.nextRowspan = false;
                        CellRangeAddress region = new CellRangeAddress(totalRowIndex.rowspanStart, totalRowIndex.rowspanEnd, j, j);
                        totalRowIndex.regionIndex = sheetModel.addMergedRegion(region);
                    }

                    totalRowIndex.rowspanStart = i + 1;
                    totalRowIndex.rowspanEnd = i + 1;
                    totalRowIndex.oldCellValue = rowspanAlignCellValue;
                }

                if (isLast) {
                    if (totalRowIndex.rowspanStart != totalRowIndex.rowspanEnd) {
                        totalRowIndex.nextRowspan = false;
                        CellRangeAddress region = new CellRangeAddress(totalRowIndex.rowspanStart, totalRowIndex.rowspanEnd, j, j);
                        totalRowIndex.regionIndex = sheetModel.addMergedRegion(region);
                    }
                }
            }

            boolean existTotal =
                    sheetSet.getFunction().getSubTotal()
                            .getCalculationFieldNameAndOrder().get(field.getName()) != null
                            || sheetSet.getFunction().getSubTotal().
                            getCalculationFieldNameAndOrder().get(excelField.columnName()) != null
                            || sheetSet.getFunction().getTotal()
                            .getCalculationFieldNameAndOrder().get(field.getName()) != null
                            || sheetSet.getFunction().getTotal().
                            getCalculationFieldNameAndOrder().get(excelField.columnName()) != null
                            || sheetSet.getFunction().getTotalAll()
                            .getCalculationFieldNameAndOrder().get(field.getName()) != null
                            || sheetSet.getFunction().getTotalAll().
                            getCalculationFieldNameAndOrder().get(excelField.columnName()) != null;

            if (totalRowIndex.oldCellValue != null && totalRowIndex.oldCellValue.equals(cv) && existTotal && totalRowIndex.nextRowspan) {
                cellValue = null;
            } else if (
                    (totalRowIndex.oldCellValue != null && !totalRowIndex.oldCellValue.equals(cv) && existTotal && totalRowIndex.nextRowspan)
                            || (totalRowIndex.oldCellValue != null && totalRowIndex.oldCellValue.equals(cv) && existTotal && isLast)
            ) {
                cellValue = null;
            } else if (totalRowIndex.oldCellValue != null && totalRowIndex.oldCellValue.equals(cv) && existTotal) {
                totalRowIndex.nextRowspan = true;
            }
            totalRowIndexMap.put(field.getName(), totalRowIndex);
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
            //当前数据行
            int current = i - initRow - extrai;
            //下一数据行
            int next = current + 1;
            //判断下一行不是数据最后一行
            if ((next) < sheetSet.getSheetData().size()) {
                Object oldcv = referenceField.get(sheetSet.getSheetData().get(current));
                Object cv = referenceField.get(sheetSet.getSheetData().get(next));
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
                        if (calculation.getSpanFieldNames() != null && !calculation.getSpanFieldNames().isEmpty()) {
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
                    if (calculation.getSpanFieldNames() != null && !calculation.getSpanFieldNames().isEmpty()) {
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
            int size = sheetSet.getSheetCache().useField.size();
            for (int j = 0; j < size; j++) {
                //创建列
                Cell cell;
                //是否偏移
                boolean isDeviation = false;
                //获取当前字段
                Field field = sheetSet.getSheetCache().useField.get(j);
                //获取字段注解
                ExcelField excelField = field.getAnnotation(ExcelField.class);

                Integer nameValue = calculation.getCalculationFieldNameAndOrder().get(field.getName());
                Integer annotationValue = calculation.getCalculationFieldNameAndOrder().get(excelField.columnName());
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

                //需要计算的列
                if (totalColumnIndexs.contains(j + 1)) {
                    //长度转成ABC列
                    String colString = CellReference.convertNumToColString(j);

                    StringBuilder sb = new StringBuilder();
                    int rs = rowspanStart;
                    for (Integer occupy : occupyRows) {
                        if ((rs + 1) <= occupy && occupy <= (rowspanEnd + 1)) {
                            while (occupyRows.contains(rs) && rs < rowspanEnd) {
                                rs = rs + 1;
                            }
                            if (!occupyRows.contains(rs)) {
                                sb.append(colString).append(rs + 1).append(":").append(colString).append(occupy).append(",");
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
                        sumString = "SUM(" + colString + (rowspanStart + 1) + ":" + colString + (rowspanEnd + 1) + ")";
                    }
                    cell.setCellFormula(sumString);
                }

                if (isDeviation) {
                    if (!calculation.getCalculationFieldNameAndOrder().containsValue(j + 1)) {
                        //创建列
                        cell = nextRow.createCell(j);
                    }
                }

                //加入当前行额外的数据
                Function.Builder.RowExtraData rowExtraData = calculation.getRowExtraData().get(j + 1);
                if (rowExtraData != null) {
                    if (rowExtraData.getIsFormula()) {
                        String cfv = rowExtraData.getValue();
                        while (cfv.contains("[") && cfv.contains("]") && cfv.contains("this.")) {
                            String cf = cfv.substring(cfv.indexOf("[") + 1, cfv.indexOf("]"));
                            int cellNumber = Integer.parseInt(cf.replaceAll("this.", "")) - 1;
                            //长度转成ABC列
                            String colString = CellReference.convertNumToColString(cellNumber);
                            cfv = cfv.replaceAll("\\[" + cf + "]", colString + (nextRow.getRowNum() + 1));
                        }
                        cell.setCellFormula(cfv);
                    } else {
                        cell.setCellValue(rowExtraData.getValue());
                    }
                }

                //设置样式
                if (sheetSet.getStyle().getContextBorder() != null && sheetSet.getStyle().getContextBorder() != BorderStyle.NONE) {
                    calculation.setBorder(sheetSet.getStyle().getContextBorder());
                }
                if (sheetSet.getStyle().getContextBorderColor() != null) {
                    calculation.setBorderColor(sheetSet.getStyle().getContextBorderColor());
                }
                cell.setCellStyle(calculation.getStyle());
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
                //当前数据行
                int current = i - initRow - extrai;
                //下一数据行
                int next = current + 1;
                //判断下一行不是数据最后一行
                if ((next) < sheetSet.getSheetData().size()) {
                    Object oldcv = field.get(sheetSet.getSheetData().get(current));
                    Object cv = field.get(sheetSet.getSheetData().get(next));
                    if (oldcv != null && !oldcv.equals(cv)) {
                        totalRowIndexMap.get(field.getName()).rowspanEnd = totalRowIndexMap.get(field.getName()).rowspanEnd + 1;
                    }
                } else {
                    totalRowIndexMap.get(field.getName()).rowspanEnd = totalRowIndexMap.get(field.getName()).rowspanEnd + 1;
                    List<CellRangeAddress> cellRangeAddressList = sheetModel.getMergedRegions();
                    CellRangeAddress cellAddresses = sheetModel.getMergedRegion(totalRowIndexMap.get(field.getName()).regionIndex);
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
    private int getInitRow(Sheet sheetModel, List<ExtraRowData> extraRowData, int initRow, Style globalStyle) {
        ExtraRowData erd = null;
        for (ExtraRowData e : extraRowData) {
            if (e != null && e.getRowNumber() != null && e.getRowNumber() == initRow && e.getIsNewRow()) {
                erd = e;
                break;
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
                CellRangeAddress cellAddresses = null;
                if (ecd.getColspan() != null && ecd.getColspan() > 1) {
                    cellAddresses = new CellRangeAddress(initRow, initRow, ecd.getCellNumber(), ecd.getCellNumber() + (ecd.getColspan() - 1));
                    sheetModel.addMergedRegion(cellAddresses);
                }
                this.setCellValue(sheetModel, cell, ecd, globalStyle, cellAddresses);
            }
            initRow = initRow + 1;
            return this.getInitRow(sheetModel, extraRowData, initRow, globalStyle);
        }
        return initRow;
    }

    /**
     * 赋值额外数据
     */
    private void setExtraData(Sheet sheetModel, ExtraRowData erd, Style globalStyle) {
        Row row = sheetModel.getRow(erd.getRowNumber());
        if (row == null) {
            row = sheetModel.createRow(erd.getRowNumber());
        }
        for (ExtraCellData ecd : erd.getExtraCellData()) {
            Cell cell = row.getCell(ecd.getCellNumber());
            if (cell == null) {
                cell = row.createCell(ecd.getCellNumber());
            }
            CellRangeAddress cellAddresses = null;
            if (ecd.getColspan() != null && ecd.getColspan() > 1) {
                cellAddresses = new CellRangeAddress(erd.getRowNumber(), erd.getRowNumber(), ecd.getCellNumber(), ecd.getCellNumber() + (ecd.getColspan() - 1));
                sheetModel.addMergedRegion(cellAddresses);
            }
            this.setCellValue(sheetModel, cell, ecd, globalStyle, cellAddresses);
        }
    }
}
