package excel.operation;

import dispose.DateDispose;
import dispose.TextDispose;
import enumerate.CommonlyUsedType;
import excel.annotation.ExcelField;
import excel.exception.ExcelOperateException;
import excel.operation.cache.ReferenceFieldCache;
import excel.operation.cache.TotalRowIndex;
import excel.operation.set.*;
import excel.util.ExcelDisposeConstant;
import excel.util.ExcelDisposeUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Excel文件导出
 *
 * @author fyy
 */
public class ExcelExport {
    /**
     * 工作簿对象
     */
    private final Workbook workbook;

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
    public Workbook setExcel(
            List<SheetSet> sheetSets
    ) {
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
                        if (sheetSet.getExtraRowData() != null && !sheetSet.getExtraRowData().isEmpty()) {
                            sheetSet.getSheetCache().sheetModelCache.initRow = this.getInitRow(
                                    sheetModel,
                                    sheetSet
                            );
                        }
                        //初始化样式
                        if (sheetSet.getStyle().getContextBorder() != null && sheetSet.getStyle().getContextBorder() != BorderStyle.NONE) {
                            sheetSet.getStyle().setTitleBorder(sheetSet.getStyle().getContextBorder());
                        }
                        if (sheetSet.getStyle().getContextBorderColor() != null) {
                            sheetSet.getStyle().setTitleBorderColor(sheetSet.getStyle().getContextBorderColor());
                        }

                        //设置标题
                        this.setTitle(sheetModel, sheetSet);

                        //循环对象（值）
                        this.setContent(sheetModel, sheetSet);

                        //全部总计
                        this.setTotalCalculation(sheetModel, sheetSet);
                    }

                    //额外数据
                    this.setExtraRowData(sheetModel, sheetSet);
                }
            }
        } catch (Exception e) {
            throw new ExcelOperateException("诊断：Excel导出失败！", e);
        }
        return this.workbook;
    }

    /**
     * 额外数据新增开始行数
     */
    private int getInitRow(
            Sheet sheetModel,
            SheetSet sheetSet
    ) {
        ExtraRowData erd = null;
        for (ExtraRowData e : sheetSet.getExtraRowData()) {
            if (e != null && e.getRowNumber() != null && e.getRowNumber().equals(sheetSet.getSheetCache().sheetModelCache.initRow) && e.getIsNewRow()) {
                erd = e;
                break;
            }
        }
        if (erd != null) {
            Row row = sheetModel.getRow(sheetSet.getSheetCache().sheetModelCache.initRow);
            if (row == null) {
                row = sheetModel.createRow(sheetSet.getSheetCache().sheetModelCache.initRow);
            }
            for (ExtraCellData ecd : erd.getExtraCellData()) {
                Cell cell = row.getCell(ecd.getCellNumber());
                if (cell == null) {
                    cell = row.createCell(ecd.getCellNumber());
                }
                CellRangeAddress cellAddresses = null;
                if (ecd.getColspan() != null && ecd.getColspan() > 1) {
                    cellAddresses = new CellRangeAddress(sheetSet.getSheetCache().sheetModelCache.initRow, sheetSet.getSheetCache().sheetModelCache.initRow, ecd.getCellNumber(), ecd.getCellNumber() + (ecd.getColspan() - 1));
                    sheetModel.addMergedRegion(cellAddresses);
                }
                this.setCellValue(sheetModel, cell, ecd, sheetSet.getStyle(), cellAddresses);
            }
            sheetSet.getSheetCache().sheetModelCache.initRow = sheetSet.getSheetCache().sheetModelCache.initRow + 1;
            return this.getInitRow(sheetModel, sheetSet);
        }
        return sheetSet.getSheetCache().sheetModelCache.initRow;
    }

    /**
     * 设置标题
     */
    private void setTitle(
            Sheet sheetModel,
            SheetSet sheetSet
    ) {
        //设置当前页签的第一行
        Row row = sheetModel.createRow(sheetSet.getSheetCache().sheetModelCache.initRow);
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
    }

    /**
     * 设置内容
     */
    private void setContent(
            Sheet sheetModel,
            SheetSet sheetSet
    ) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        int rowSize = sheetSet.getSheetData().size();
        for (sheetSet.getSheetCache().sheetModelCache.i = sheetSet.getSheetCache().sheetModelCache.initRow;
             sheetSet.getSheetCache().sheetModelCache.i < (rowSize + sheetSet.getSheetCache().sheetModelCache.initRow + sheetSet.getSheetCache().sheetModelCache.extraRow);
             sheetSet.getSheetCache().sheetModelCache.i++) {
            //创建行    （标题的下一行）
            Row nextRow = sheetModel.createRow(sheetSet.getSheetCache().sheetModelCache.i + 1);

            if (sheetSet.getExtraRowData() != null && !sheetSet.getExtraRowData().isEmpty()) {
                for (ExtraRowData erd : sheetSet.getExtraRowData()) {
                    if (erd != null) {
                        if (!erd.getIsMaxRowNumber() && erd.getRowNumber() == nextRow.getRowNum()) {
                            //抛错处理
                            throw new ExcelOperateException("诊断：请勿在数据行中添加额外数据！错误行数：" + (erd.getRowNumber() + 1) + " 行", new RuntimeException());
                        }
                    }
                }
            }

            //处理列
            int rowCellSize = sheetSet.getSheetCache().useField.size();
            for (sheetSet.getSheetCache().sheetModelCache.j = 0; sheetSet.getSheetCache().sheetModelCache.j < rowCellSize; sheetSet.getSheetCache().sheetModelCache.j++) {
                //获取字段
                Field field = sheetSet.getSheetCache().useField.get(sheetSet.getSheetCache().sheetModelCache.j);
                //获取字段注解
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                //创建列
                Cell cell = nextRow.createCell(sheetSet.getSheetCache().sheetModelCache.j);
                //获取值和计算夸列
                String cellValue = this.cellValueRowSpan(
                        sheetModel,
                        sheetSet,
                        field,
                        excelField
                );
                //判断是否需要赋值
                boolean isSetValue = true;
                if (!sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.isEmpty()) {
                    TotalRowIndex totalRowIndex = sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName());
                    if (totalRowIndex != null) {
                        if (totalRowIndex.rowspanStart != (sheetSet.getSheetCache().sheetModelCache.i + 1) && totalRowIndex.rowspanEnd != 0 && totalRowIndex.rowspanStart < (sheetSet.getSheetCache().sheetModelCache.i + 1) && (sheetSet.getSheetCache().sheetModelCache.i + 1) <= totalRowIndex.rowspanEnd) {
                            isSetValue = false;
                        }
                    }
                }
                if (isSetValue) {
                    //赋值
                    this.setCellValue(field, excelField, cell, cellValue);
                }
                //设置单元格格式
                cell.setCellStyle(sheetSet.getSheetCache().cellStyleMap.get(field.getName()));
            }

            //计算功能处理
            this.setRowCalculation(sheetModel, sheetSet);

        }
    }

    /**
     * 执行行计算
     */
    private void setRowCalculation(
            Sheet sheetModel,
            SheetSet sheetSet
    ) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        //小计
        if (sheetSet.getSheetCache().subTotalReferenceField != null && sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(sheetSet.getSheetCache().subTotalReferenceField.getName()) != null) {
            if (this.calculation(
                    sheetModel,
                    sheetSet,
                    sheetSet.getFunction().getSubTotal(),
                    sheetSet.getSheetCache().subTotalReferenceField,
                    sheetSet.getSheetCache().subTotalSpanField,
                    sheetSet.getSheetCache().subTotalColumnIndex
            )) {
                //校准变更的数据
                sheetSet.getSheetCache().sheetModelCache.extraRow = sheetSet.getSheetCache().sheetModelCache.extraRow + 1;
                sheetSet.getSheetCache().sheetModelCache.i = sheetSet.getSheetCache().sheetModelCache.i + 1;
            }
        }

        //总计
        if (sheetSet.getSheetCache().totalReferenceField != null && sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(sheetSet.getSheetCache().totalReferenceField.getName()) != null) {
            if (this.calculation(
                    sheetModel,
                    sheetSet,
                    sheetSet.getFunction().getTotal(),
                    sheetSet.getSheetCache().totalReferenceField,
                    sheetSet.getSheetCache().totalSpanField,
                    sheetSet.getSheetCache().totalColumnIndex
            )) {
                //校准变更的数据
                sheetSet.getSheetCache().sheetModelCache.extraRow = sheetSet.getSheetCache().sheetModelCache.extraRow + 1;
                sheetSet.getSheetCache().sheetModelCache.i = sheetSet.getSheetCache().sheetModelCache.i + 1;
            }
        }
    }

    /**
     * 执行计算
     */
    private void setTotalCalculation(
            Sheet sheetModel,
            SheetSet sheetSet
    ) {
        if (
                sheetSet.getFunction().getTotalAll().getCalculationFieldNameAndOrder() != null &&
                        sheetSet.getFunction().getTotalAll().getCalculationFieldNameAndOrder().size() > 0
        ) {
            sheetSet.getSheetCache().sheetModelCache.occupyRows.add(sheetSet.getSheetData().size() + sheetSet.getSheetCache().sheetModelCache.initRow + sheetSet.getSheetCache().sheetModelCache.extraRow + 1);
            this.calculationDispose(
                    sheetSet.getSheetData().size() + sheetSet.getSheetCache().sheetModelCache.initRow + sheetSet.getSheetCache().sheetModelCache.extraRow - 1,
                    sheetSet.getSheetCache().sheetModelCache.initRow + 1,
                    sheetModel.getLastRowNum(),
                    sheetModel,
                    sheetSet,
                    sheetSet.getFunction().getTotalAll(),
                    sheetSet.getSheetCache().totalAllColumnIndex
            );
        }
    }

    /**
     * 设置额外数据行
     */
    private void setExtraRowData(
            Sheet sheetModel,
            SheetSet sheetSet
    ) {
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

    /**
     * 赋值额外数据
     */
    private void setExtraData(
            Sheet sheetModel,
            ExtraRowData erd,
            Style globalStyle
    ) {
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

    /**
     * 赋值
     */
    private void setCellValue(
            Field field,
            ExcelField excelField,
            Cell cell,
            String cellValue
    ) {
        //结果转换
        if (cellValue != null) {
            if (TextDispose.isNumber(cellValue)) {
                //结果转换
                if (field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_double.getValue())) {
                    cell.setCellValue(Double.parseDouble(cellValue));
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Integer.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_int.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_BigInteger.getValue())) {
                    cell.setCellValue(Integer.parseInt(cellValue));
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_Long.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_long.getValue())) {
                    cell.setCellValue(Long.parseLong(cellValue));
                } else if (field.getType().getName().equals(CommonlyUsedType.Type_BigDecimal.getValue())) {
                    cell.setCellValue(new BigDecimal(cellValue).doubleValue());
                } else {
                    cell.setCellValue(cellValue);
                }
            } else if (field.getType().getName().equals(CommonlyUsedType.Type_Util_Date.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Sql_Date.getValue()) || field.getType().getName().equals(CommonlyUsedType.Type_Timestamp.getValue())) {
                cell.setCellValue(DateDispose.formattingDate(cellValue, excelField.dateType()));
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
    private CellStyle getCellStyle(
            Field field,
            ExcelField excelField,
            Style globalStyle
    ) {
        CellStyle cellStyle = this.workbook.createCellStyle();
        //设置内容格式
        this.setCellContentStyle(field, excelField, cellStyle);
        //设置位置
        this.setCellPositionStyle(excelField, cellStyle);
        //设置边框
        this.setCellBorderStyle(excelField, cellStyle, globalStyle);

        return cellStyle;
    }

    /**
     * getCellStyle设置内容格式
     */
    private void setCellContentStyle(
            Field field,
            ExcelField excelField,
            CellStyle cellStyle
    ) {
        // 是否存在
        boolean exist;

        // 数字
        exist = field.getType().getName().equals(CommonlyUsedType.Type_BigDecimal.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_Double.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_double.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_Long.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_long.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_BigInteger.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_Integer.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_int.getValue());
        if (exist) {
            if (excelField.isMoney()) {
                DataFormat format = this.workbook.createDataFormat();
                StringBuilder moneyFormat = new StringBuilder("#,##0");

                if (excelField.decimalAfterDigit() > 0) {
                    moneyFormat.append(".");
                    int decimalAfterDigit = excelField.decimalAfterDigit();
                    for (int i = 0; i < decimalAfterDigit; i++) {
                        moneyFormat.append("0");
                    }
                }
                cellStyle.setDataFormat(format.getFormat(moneyFormat.toString()));
            } else if (excelField.decimalAfterDigit() > 0) {
                DataFormat format = this.workbook.createDataFormat();
                StringBuilder sb = new StringBuilder("#0.");
                int decimalAfterDigit = excelField.decimalAfterDigit();
                for (int i = 0; i < decimalAfterDigit; i++) {
                    sb.append("0");
                }
                cellStyle.setDataFormat(format.getFormat(sb.toString()));
            }
        }
        // 时间
        exist = field.getType().getName().equals(CommonlyUsedType.Type_Util_Date.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_Sql_Date.getValue())
                || field.getType().getName().equals(CommonlyUsedType.Type_Timestamp.getValue());
        if (exist) {
            DataFormat format = this.workbook.createDataFormat();
            cellStyle.setDataFormat(format.getFormat(excelField.dateType().getValue()));
        }
    }

    /**
     * getCellStyle设置位置
     */
    private void setCellPositionStyle(
            ExcelField excelField,
            CellStyle cellStyle
    ) {
        //水平位置
        cellStyle.setAlignment(excelField.horizontalAlignment());
        //垂直位置
        cellStyle.setVerticalAlignment(excelField.verticalAlignment());
    }

    /**
     * getCellStyle设置边框
     */
    private void setCellBorderStyle(
            ExcelField excelField,
            CellStyle cellStyle,
            Style globalStyle
    ) {
        if (globalStyle.getContextBorder() != null && globalStyle.getContextBorder() != BorderStyle.NONE) {
            setContextBorder(cellStyle, globalStyle);
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
    }

    /**
     * 设置全局边框
     */
    private void setContextBorder(
            CellStyle cellStyle,
            Style globalStyle
    ) {
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
    }

    /**
     * 赋值
     */
    private void setCellValue(
            Sheet sheetModel,
            Cell cell,
            ExtraCellData ecd,
            Style globalStyle,
            CellRangeAddress cellAddresses
    ) {
        //结果转换
        if (ecd != null) {
            if (ecd.getCellValue() != null) {
                //结果转换
                if (ecd.getCellType() == Double.class || ecd.getCellType() == double.class) {
                    cell.setCellValue(Double.parseDouble(ecd.getCellValue().toString()));
                } else if (ecd.getCellType() == Integer.class || ecd.getCellType() == int.class || ecd.getCellType() == BigInteger.class) {
                    cell.setCellValue(Integer.parseInt(ecd.getCellValue().toString()));
                } else if (ecd.getCellType() == Long.class || ecd.getCellType() == long.class) {
                    cell.setCellValue(Long.parseLong(ecd.getCellValue().toString()));
                } else if (ecd.getCellType() == BigDecimal.class) {
                    cell.setCellValue(new BigDecimal(ecd.getCellValue().toString()).doubleValue());
                } else if (ecd.getCellType() == Date.class || ecd.getCellType() == java.sql.Date.class || ecd.getCellType() == Timestamp.class) {
                    cell.setCellValue((Date) ecd.getCellValue());
                } else {
                    cell.setCellValue(ecd.getCellValue().toString());
                }
            }
            cell.setCellStyle(this.getCellStyle(sheetModel, ecd, globalStyle, cellAddresses));
        } else {
            cell.setCellValue("");
        }
    }

    /**
     * 获取单元格样式
     */
    private CellStyle getCellStyle(
            Sheet sheetModel,
            ExtraCellData ecd,
            Style globalStyle,
            CellRangeAddress cellAddresses
    ) {
        CellStyle cellStyle = this.workbook.createCellStyle();

        //设置内容格式
        this.setCellContentStyle(ecd, cellStyle);
        //设置位置
        this.setCellPositionStyle(ecd, cellStyle);
        //设置边框
        this.setCellBorderStyle(sheetModel, ecd, cellStyle, globalStyle, cellAddresses);

        return cellStyle;
    }

    /**
     * getCellStyle设置内容格式
     */
    private void setCellContentStyle(
            ExtraCellData ecd,
            CellStyle cellStyle
    ) {
        // 是否存在
        boolean exist;
        // 数字
        exist = ecd.getCellType() == BigDecimal.class
                || ecd.getCellType() == Double.class
                || ecd.getCellType() == double.class
                || ecd.getCellType() == Long.class
                || ecd.getCellType() == long.class
                || ecd.getCellType() == BigInteger.class
                || ecd.getCellType() == Integer.class
                || ecd.getCellType() == int.class;
        if (exist) {
            if (ecd.getIsMoney()) {
                DataFormat format = this.workbook.createDataFormat();
                StringBuilder moneyFormat = new StringBuilder("#,##0");
                if (ecd.getDecimalAfterDigit() > 0) {
                    moneyFormat.append(".");
                    int decimalAfterDigit = ecd.getDecimalAfterDigit();
                    for (int i = 0; i < decimalAfterDigit; i++) {
                        moneyFormat.append("0");
                    }
                }
                cellStyle.setDataFormat(format.getFormat(moneyFormat.toString()));
            } else if (ecd.getDecimalAfterDigit() > 0) {
                DataFormat format = this.workbook.createDataFormat();
                StringBuilder sb = new StringBuilder("#0.");
                int decimalAfterDigit = ecd.getDecimalAfterDigit();
                for (int i = 0; i < decimalAfterDigit; i++) {
                    sb.append("0");
                }
                cellStyle.setDataFormat(format.getFormat(sb.toString()));
            }
        }

        // 时间
        exist = ecd.getCellType() == Date.class
                || ecd.getCellType() == java.sql.Date.class
                || ecd.getCellType() == Timestamp.class;
        if (exist) {
            DataFormat format = this.workbook.createDataFormat();
            cellStyle.setDataFormat(format.getFormat(ecd.getDateType().getValue()));
        }

        // 其他
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
    }

    /**
     * getCellStyle设置位置
     */
    private void setCellPositionStyle(
            ExtraCellData ecd,
            CellStyle cellStyle
    ) {
        //水平位置
        cellStyle.setAlignment(ecd.getHorizontalAlignment());
        //垂直位置
        cellStyle.setVerticalAlignment(ecd.getVerticalAlignment());
    }

    /**
     * getCellStyle设置边框
     */
    private void setCellBorderStyle(
            Sheet sheetModel,
            ExtraCellData ecd,
            CellStyle cellStyle,
            Style globalStyle,
            CellRangeAddress cellAddresses
    ) {
        if (globalStyle.getContextBorder() != null && globalStyle.getContextBorder() != BorderStyle.NONE) {
            //全局
            this.setCellAllBorderStyle(sheetModel, cellStyle, globalStyle, cellAddresses);
        } else {
            //局部
            this.setCellLocalBorderStyle(sheetModel, ecd, cellStyle, cellAddresses);
        }
    }

    /**
     * 全局样式
     */
    private void setCellAllBorderStyle(
            Sheet sheetModel,
            CellStyle cellStyle,
            Style globalStyle,
            CellRangeAddress cellAddresses
    ) {
        //处理原单元格
        setContextBorder(cellStyle, globalStyle);
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
    }

    /**
     * 局部样式
     */
    private void setCellLocalBorderStyle(
            Sheet sheetModel,
            ExtraCellData ecd,
            CellStyle cellStyle,
            CellRangeAddress cellAddresses
    ) {
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

    /**
     * 获取值和计算夸列
     */
    private String cellValueRowSpan(
            Sheet sheetModel,
            SheetSet sheetSet,
            Field field,
            ExcelField excelField
    ) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //当前数据行
        int current = sheetSet.getSheetCache().sheetModelCache.i - sheetSet.getSheetCache().sheetModelCache.initRow - sheetSet.getSheetCache().sheetModelCache.extraRow;
        //当前的字段值
        String cellValue = ExcelDisposeUtil.correspondingValue(field, sheetSet.getSheetData().get(current), sheetSet.getIsGetMethodFieldValue(), excelField.dateType(), excelField.decimalAfterDigit(), excelField.roundingMode());
        //获取转换值集中对应值
        cellValue = ExcelDisposeUtil.getValueLimit(sheetSet, cellValue, excelField);
        //跨行处理
        if (excelField.rowspan()) {
            //上一数据行
            int previous = current - 1;
            //下一数据行
            int next = current + 1;
            //是否是最后一行
            boolean isLast = (sheetSet.getSheetCache().sheetModelCache.i == sheetSet.getSheetData().size() + sheetSet.getSheetCache().sheetModelCache.initRow + sheetSet.getSheetCache().sheetModelCache.extraRow - 1);
            //获取参考字段
            ReferenceFieldCache referenceFieldCache = new ReferenceFieldCache();
            //参考列值
            referenceFieldCache.rowspanAlignCellValue = cellValue;
            //计算参考字段
            this.getReferenceField(current, sheetSet, excelField, referenceFieldCache);
            //对比列下一行值
            this.getNextCellValue(previous, next, isLast, sheetSet, field, excelField, referenceFieldCache);
            //开始计算合并
            TotalRowIndex totalRowIndex = sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName());
            if (totalRowIndex == null) {
                totalRowIndex = new TotalRowIndex();
                totalRowIndex.field = field;
                totalRowIndex.columnNo = sheetSet.getSheetCache().sheetModelCache.j;
            }
            this.addMergedRegion(isLast, sheetModel, sheetSet, totalRowIndex, referenceFieldCache);
            //小计、总计、全部总计
            boolean existTotal = sheetSet.getFunction().getSubTotal().getCalculationFieldNameAndOrder().get(field.getName()) != null
                    || sheetSet.getFunction().getSubTotal().getCalculationFieldNameAndOrder().get(excelField.columnName()) != null
                    || sheetSet.getFunction().getTotal().getCalculationFieldNameAndOrder().get(field.getName()) != null
                    || sheetSet.getFunction().getTotal().getCalculationFieldNameAndOrder().get(excelField.columnName()) != null
                    || sheetSet.getFunction().getTotalAll().getCalculationFieldNameAndOrder().get(field.getName()) != null
                    || sheetSet.getFunction().getTotalAll().getCalculationFieldNameAndOrder().get(excelField.columnName()) != null;
            //判断当前的内容和下一行内容不一致 并且是最后一行
            boolean exist = (totalRowIndex.oldCellValue != null
                    && !totalRowIndex.oldCellValue.equals(referenceFieldCache.cellValue)
                    && existTotal && totalRowIndex.nextRowspan)
                    || (totalRowIndex.oldCellValue != null
                    && totalRowIndex.oldCellValue.equals(referenceFieldCache.cellValue)
                    && existTotal && isLast);
            //判断当前的内容和下一行内容是一致 接下来是否会合并
            if (totalRowIndex.oldCellValue != null && totalRowIndex.oldCellValue.equals(referenceFieldCache.cellValue) && existTotal && totalRowIndex.nextRowspan) {
                cellValue = null;
            } else if (exist) {
                cellValue = null;
            } else if (totalRowIndex.oldCellValue != null && totalRowIndex.oldCellValue.equals(referenceFieldCache.cellValue) && existTotal) {
                totalRowIndex.nextRowspan = true;
            }
            sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.put(field.getName(), totalRowIndex);
        }
        return cellValue;
    }

    /**
     * 获取参考列
     */
    private void getReferenceField(
            int current,
            SheetSet sheetSet,
            ExcelField excelField,
            ReferenceFieldCache referenceFieldCache
    ) {
        if (excelField.rowspanAlignOrder() > 0) {
            //参考数据行
            int reference = excelField.rowspanAlignOrder() - 1;
            referenceFieldCache.referenceField = sheetSet.getSheetCache().useField.get(reference);
            referenceFieldCache.referenceExcelField = referenceFieldCache.referenceField.getAnnotation(ExcelField.class);
        }

        if (referenceFieldCache.referenceField != null && referenceFieldCache.referenceExcelField != null) {
            referenceFieldCache.rowspanAlignCellValue = ExcelDisposeUtil.correspondingValue(
                    referenceFieldCache.referenceField, sheetSet.getSheetData().get(current), sheetSet.getIsGetMethodFieldValue(),
                    referenceFieldCache.referenceExcelField.dateType(),
                    referenceFieldCache.referenceExcelField.decimalAfterDigit(),
                    referenceFieldCache.referenceExcelField.roundingMode()
            );
        }
    }

    /**
     * 获取对比值
     */
    private void getNextCellValue(
            int previous,
            int next,
            boolean isLast,
            SheetSet sheetSet,
            Field field,
            ExcelField excelField,
            ReferenceFieldCache referenceFieldCache
    ) {
        if (next < sheetSet.getSheetData().size()) {
            getReferenceFieldCellValue(next, sheetSet, field, excelField, referenceFieldCache);
        } else if (isLast && sheetSet.getSheetData().size() > 1) {
            getReferenceFieldCellValue(previous, sheetSet, field, excelField, referenceFieldCache);
        }
    }

    /**
     * 获取参考列的值
     */
    private void getReferenceFieldCellValue(int current, SheetSet sheetSet, Field field, ExcelField excelField, ReferenceFieldCache referenceFieldCache) {
        if (excelField.rowspanAlignOrder() == 0) {
            referenceFieldCache.cellValue = ExcelDisposeUtil.correspondingValue(
                    field,
                    sheetSet.getSheetData().get(current),
                    sheetSet.getIsGetMethodFieldValue(),
                    excelField.dateType(),
                    excelField.decimalAfterDigit(),
                    excelField.roundingMode()
            );
        } else if (referenceFieldCache.referenceField != null && referenceFieldCache.referenceExcelField != null) {
            referenceFieldCache.cellValue = ExcelDisposeUtil.correspondingValue(
                    referenceFieldCache.referenceField,
                    sheetSet.getSheetData().get(current),
                    sheetSet.getIsGetMethodFieldValue(),
                    referenceFieldCache.referenceExcelField.dateType(),
                    referenceFieldCache.referenceExcelField.decimalAfterDigit(),
                    referenceFieldCache.referenceExcelField.roundingMode()
            );
        }
    }

    /**
     * 开始计算合并
     */
    private void addMergedRegion(
            boolean isLast,
            Sheet sheetModel,
            SheetSet sheetSet,
            TotalRowIndex totalRowIndex,
            ReferenceFieldCache referenceFieldCache
    ) {
        if (totalRowIndex.rowspanStart == 0) {
            totalRowIndex.rowspanStart = sheetSet.getSheetCache().sheetModelCache.i + 1;
            totalRowIndex.oldCellValue = referenceFieldCache.rowspanAlignCellValue;
        } else {
            if (totalRowIndex.oldCellValue != null && totalRowIndex.oldCellValue.equals(referenceFieldCache.rowspanAlignCellValue)) {
                totalRowIndex.rowspanEnd = sheetSet.getSheetCache().sheetModelCache.i + 1;
            } else {
                if (totalRowIndex.rowspanEnd > 0 && !totalRowIndex.rowspanStart.equals(totalRowIndex.rowspanEnd)) {
                    totalRowIndex.nextRowspan = false;
                    CellRangeAddress region = new CellRangeAddress(totalRowIndex.rowspanStart, totalRowIndex.rowspanEnd, sheetSet.getSheetCache().sheetModelCache.j, sheetSet.getSheetCache().sheetModelCache.j);
                    totalRowIndex.regionIndex = sheetModel.addMergedRegion(region);
                }
                totalRowIndex.rowspanStart = sheetSet.getSheetCache().sheetModelCache.i + 1;
                totalRowIndex.rowspanEnd = sheetSet.getSheetCache().sheetModelCache.i + 1;
                totalRowIndex.oldCellValue = referenceFieldCache.rowspanAlignCellValue;
            }
            if (isLast) {
                if (!totalRowIndex.rowspanStart.equals(totalRowIndex.rowspanEnd)) {
                    totalRowIndex.nextRowspan = false;
                    CellRangeAddress region = new CellRangeAddress(totalRowIndex.rowspanStart, totalRowIndex.rowspanEnd, sheetSet.getSheetCache().sheetModelCache.j, sheetSet.getSheetCache().sheetModelCache.j);
                    totalRowIndex.regionIndex = sheetModel.addMergedRegion(region);
                }
            }
        }
    }

    /**
     * 计算功能
     */
    private boolean calculation(
            Sheet sheetModel,
            SheetSet sheetSet,
            Function.Calculation calculation,
            Field referenceField,
            List<Field> spanFieldNames,
            List<Integer> totalColumnIndexs
    ) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (referenceField != null) {
            TotalRowIndex totalRowIndex = sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(referenceField.getName());
            //当前数据行
            int current = sheetSet.getSheetCache().sheetModelCache.i - sheetSet.getSheetCache().sheetModelCache.initRow - sheetSet.getSheetCache().sheetModelCache.extraRow;
            //下一数据行
            int next = current + 1;
            //判断下一行不是数据最后一行
            if ((next) < sheetSet.getSheetData().size()) {
                Object oldcv;
                Object cv;
                if (sheetSet.getIsGetMethodFieldValue()) {
                    String fieldName = referenceField.getName();
                    String fieldNameFirstUpperCase = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
                    Method currentMethod = sheetSet.getSheetData().get(current).getClass().getMethod("get" + fieldNameFirstUpperCase);
                    Method nextMethod = sheetSet.getSheetData().get(next).getClass().getMethod("get" + fieldNameFirstUpperCase);
                    oldcv = currentMethod.invoke(sheetSet.getSheetData().get(current));
                    cv = nextMethod.invoke(sheetSet.getSheetData().get(next));
                } else {
                    oldcv = referenceField.get(sheetSet.getSheetData().get(current));
                    cv = referenceField.get(sheetSet.getSheetData().get(next));
                }

                if (oldcv != null && !oldcv.equals(cv)) {
                    if (this.calculationDispose(
                            sheetSet.getSheetCache().sheetModelCache.i,
                            totalRowIndex.rowspanStart,
                            totalRowIndex.rowspanEnd,
                            sheetModel,
                            sheetSet,
                            calculation,
                            totalColumnIndexs
                    )) {
                        sheetSet.getSheetCache().sheetModelCache.occupyRows.add(sheetSet.getSheetCache().sheetModelCache.i + 2);
                        //重新合并
                        if (calculation.getSpanFieldNames() != null && !calculation.getSpanFieldNames().isEmpty()) {
                            this.againSpan(
                                    sheetModel,
                                    sheetSet,
                                    spanFieldNames
                            );
                        }
                        return true;
                    }
                }
            } else {
                sheetSet.getSheetCache().sheetModelCache.occupyRows.add(sheetSet.getSheetCache().sheetModelCache.i + 2);
                if (this.calculationDispose(
                        sheetSet.getSheetCache().sheetModelCache.i,
                        totalRowIndex.rowspanStart,
                        totalRowIndex.rowspanEnd,
                        sheetModel,
                        sheetSet,
                        calculation,
                        totalColumnIndexs
                )) {
                    //重新合并
                    if (calculation.getSpanFieldNames() != null && !calculation.getSpanFieldNames().isEmpty()) {
                        this.againSpan(
                                sheetModel,
                                sheetSet,
                                spanFieldNames
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
            List<Integer> totalColumnIndexs
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
                    this.calculationSum(j, rowspanStart, rowspanEnd, sheetSet.getSheetCache().sheetModelCache.occupyRows, cell);
                }
                //设置偏移前样式
                this.setCellStyle(sheetSet, cell, calculation);
                //是否偏移
                if (isDeviation) {
                    if (!calculation.getCalculationFieldNameAndOrder().containsValue(j + 1)) {
                        //创建列
                        cell = nextRow.createCell(j);
                    }
                }
                //加入当前行额外的数据
                this.addRowExtraData(cell, calculation, nextRow, j);
                //设置偏移后样式
                this.setCellStyle(sheetSet, cell, calculation);
            }
            return true;
        }
        return false;
    }

    /**
     * 计算公式
     */
    private void calculationSum(
            int j,
            int rowspanStart,
            int rowspanEnd,
            List<Integer> occupyRows,
            Cell cell
    ) {
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
            sumString = "SUM(" + sb + ")";
        } else {
            sumString = "SUM(" + colString + (rowspanStart + 1) + ":" + colString + (rowspanEnd + 1) + ")";
        }
        cell.setCellFormula(sumString);
    }

    /**
     * 加入当前行额外的数据
     */
    private void addRowExtraData(
            Cell cell,
            Function.Builder calculation,
            Row nextRow,
            int j
    ) {
        Function.Builder.RowExtraData rowExtraData = calculation.getRowExtraData().get(j + 1);
        if (rowExtraData != null) {
            if (rowExtraData.getIsFormula()) {
                String cfv = rowExtraData.getValue();
                while (cfv.contains(ExcelDisposeConstant.LEFT_BRACKET) && cfv.contains(ExcelDisposeConstant.RIGHT_BRACKET) && cfv.contains(ExcelDisposeConstant.THIS_DOT)) {
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
    }

    /**
     * 设置样式
     */
    private void setCellStyle(
            SheetSet sheetSet,
            Cell cell,
            Function.Builder calculation
    ) {
        if (sheetSet.getStyle().getContextBorder() != null && sheetSet.getStyle().getContextBorder() != BorderStyle.NONE) {
            calculation.setBorder(sheetSet.getStyle().getContextBorder());
        }
        if (sheetSet.getStyle().getContextBorderColor() != null) {
            calculation.setBorderColor(sheetSet.getStyle().getContextBorderColor());
        }
        cell.setCellStyle(calculation.getStyle());
    }

    /**
     * 重新计算跨行
     */
    private void againSpan(
            Sheet sheetModel,
            SheetSet sheetSet,
            List<Field> spanFieldNames
    ) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (Field field : spanFieldNames) {
            if (field != null) {
                //当前数据行
                int current = sheetSet.getSheetCache().sheetModelCache.i - sheetSet.getSheetCache().sheetModelCache.initRow - sheetSet.getSheetCache().sheetModelCache.extraRow;
                //下一数据行
                int next = current + 1;
                //判断下一行不是数据最后一行
                if ((next) < sheetSet.getSheetData().size()) {
                    Object oldcv;
                    Object cv;
                    if (sheetSet.getIsGetMethodFieldValue()) {
                        String fieldName = field.getName();
                        String fieldNameFirstUpperCase = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
                        Method currentMethod = sheetSet.getSheetData().get(current).getClass().getMethod("get" + fieldNameFirstUpperCase);
                        Method nextMethod = sheetSet.getSheetData().get(next).getClass().getMethod("get" + fieldNameFirstUpperCase);
                        oldcv = currentMethod.invoke(sheetSet.getSheetData().get(current));
                        cv = nextMethod.invoke(sheetSet.getSheetData().get(next));
                    } else {
                        oldcv = field.get(sheetSet.getSheetData().get(current));
                        cv = field.get(sheetSet.getSheetData().get(next));
                    }
                    if (oldcv != null && !oldcv.equals(cv)) {
                        sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName()).rowspanEnd = sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName()).rowspanEnd + 1;
                    }
                } else {
                    sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName()).rowspanEnd = sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName()).rowspanEnd + 1;
                    List<CellRangeAddress> cellRangeAddressList = sheetModel.getMergedRegions();
                    CellRangeAddress cellAddresses = sheetModel.getMergedRegion(sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName()).regionIndex);
                    if (cellRangeAddressList.contains(cellAddresses)) {
                        sheetModel.removeMergedRegion(cellRangeAddressList.indexOf(cellAddresses));
                        for (Map.Entry<String, TotalRowIndex> totalRowIndexMapEntry : sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.entrySet()) {
                            if (sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName()).regionIndex < totalRowIndexMapEntry.getValue().regionIndex) {
                                totalRowIndexMapEntry.getValue().regionIndex = totalRowIndexMapEntry.getValue().regionIndex - 1;
                            }
                        }
                    }
                    CellRangeAddress region = new CellRangeAddress(sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName()).rowspanStart, sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName()).rowspanEnd, sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName()).columnNo, sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName()).columnNo);
                    sheetSet.getSheetCache().sheetModelCache.totalRowIndexMap.get(field.getName()).regionIndex = sheetModel.addMergedRegion(region);
                }
            }
        }
    }
}
