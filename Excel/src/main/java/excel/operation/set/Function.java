package excel.operation.set;

import org.apache.poi.ss.usermodel.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Function {
    /**
     * 小计
     */
    private Function.Calculation subTotal;

    /**
     * 总计
     */
    private Function.Calculation total;

    /**
     * 总计所有
     */
    private Function.Builder totalAll;

    //------------------------------------------------------------------------------------------------------------------构造
    public Function(Workbook workbook) {
        this.subTotal = new Function.Calculation(workbook);
        this.total = new Function.Calculation(workbook);
        this.totalAll = new Function.Builder(workbook);
    }

    //------------------------------------------------------------------------------------------------------------------GetSet

    /**
     * 小计
     */
    public Calculation getSubTotal() {
        return subTotal;
    }

    /**
     * 总计
     */
    public Function.Calculation getTotal() {
        return total;
    }

    /**
     * 总计所有
     */
    public Function.Builder getTotalAll() {
        return totalAll;
    }

    //------------------------------------------------------------------------------------------------------------------内部类

    /**
     * 计算配置构建基础类
     */
    public class Builder {
        /**
         * 需要计算字段名,排序
         */
        private Map<String, Integer> calculationFieldNameAndOrder;

        /**
         * 说明标注
         */
        private String explain;

        /**
         * 说明标注离结果位置
         */
        private int explainOrder;

        /**
         * 样式
         */
        private CellStyle style;

        //---------------------------------------------------------------------------------------------内部参数
        /**
         * 字体
         */
        private Font font;

        /**
         * 格式
         */
        private DataFormat format;

        //---------------------------------------------------------------------------------------------构造
        //构造
        private Builder(Workbook workbook) {
            this.style = workbook.createCellStyle();
            this.font = workbook.createFont();
            this.format = workbook.createDataFormat();
            this.calculationFieldNameAndOrder = new HashMap<>();
        }

        //--------------------------------------------------------------------------------------------GetSet

        /**
         * 计算字段名 - 可以多个
         */
        public Function.Builder setCalculationFieldNames(String... calculationFieldNames) {
            for (String name : calculationFieldNames) {
                Integer i = this.calculationFieldNameAndOrder.get(name);
                if (i == null) {
                    this.calculationFieldNameAndOrder.put(name, 0);
                } else {
                    this.calculationFieldNameAndOrder.put(name, i);
                }
            }
            return this;
        }

        /**
         * 设置计算字段名,位置
         */
        public Function.Builder setCalculationFieldNameAndOrder(String calculationFieldName, int order) {
            this.calculationFieldNameAndOrder.put(calculationFieldName, order);
            return this;
        }

        /**
         * 多条设置计算字段名,位置
         */
        public Function.Builder setCalculationFieldNameAndOrder(Map<String, Integer> calculationFieldNameAndOrder) {
            for (Map.Entry<String, Integer> fieldNameAndOrder : calculationFieldNameAndOrder.entrySet()) {
                this.calculationFieldNameAndOrder.put(fieldNameAndOrder.getKey(), fieldNameAndOrder.getValue());
            }
            return this;
        }

        /**
         * 获取计算字段名,位置
         */
        public Map<String, Integer> getCalculationFieldNameAndOrder() {
            return this.calculationFieldNameAndOrder;
        }

        /**
         * 说明标注
         */
        public String getExplain() {
            return explain;
        }

        /**
         * 说明标注
         */
        public Function.Builder setExplain(String explain) {
            this.explain = explain;
            return this;
        }

        /**
         * 说明标注离结果位置
         */
        public int getExplainOrder() {
            return explainOrder;
        }

        /**
         * 说明标注离结果位置
         */
        public Function.Builder setExplainOrder(int explainOrder) {
            this.explainOrder = explainOrder;
            return this;
        }

        /**
         * 说明标注
         */
        public Function.Builder setExplainAndOrder(String explain, int explainOrder) {
            this.explain = explain;
            this.explainOrder = explainOrder;
            return this;
        }

        /**
         * 样式
         */
        public CellStyle getStyle() {
            return style;
        }

        /**
         * 样式
         */
        public Function.Builder setStyle(CellStyle style) {
            this.style = style;
            return this;
        }


        /**
         * 标题颜色
         */
        public Function.Builder setStyleColor(short styleColor) {
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(styleColor);
            return this;
        }

        /**
         * 标题颜色
         */
        public Function.Builder setStyleColor(IndexedColors indexedColors) {
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(indexedColors.getIndex());
            return this;
        }

        /**
         * 标题是否加粗
         */
        public Function.Builder setStyleBold(boolean bold) {
            font.setBold(true);
            style.setFont(font);
            return this;
        }

        /**
         * 水平位置设置
         */
        public Function.Builder setHorizontalAlignment(HorizontalAlignment alignment) {
            style.setAlignment(alignment);
            return this;
        }

        /**
         * 垂直位置设置
         */
        public Function.Builder setVerticalAlignment(VerticalAlignment alignment) {
            style.setVerticalAlignment(alignment);
            return this;
        }

        /**
         * 内容位置设置
         */
        public Function.Builder setAlignment(HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
            style.setAlignment(horizontalAlignment);
            style.setVerticalAlignment(verticalAlignment);
            return this;
        }

        /**
         * 数字货币格式
         *
         * @param decimalAfterDigit 货币格式后保留位数
         */
        public Function.Builder setStyleMoneyFormat(int decimalAfterDigit) {
            StringBuilder moneyFormat = new StringBuilder("#,##0");
            if (decimalAfterDigit > 0) {
                moneyFormat.append(".");
                for (int i = 0; i < decimalAfterDigit; i++) {
                    moneyFormat.append("0");
                }
            }
            style.setDataFormat(this.format.getFormat(moneyFormat.toString()));
            return this;
        }

        /**
         * 数字格式
         *
         * @param decimalAfterDigit 数字格式后保留位数
         */
        public Function.Builder setStyleNumberFormat(int decimalAfterDigit) {
            StringBuilder sb = new StringBuilder("#0.");
            if (decimalAfterDigit > 0) {
                for (int i = 0; i < decimalAfterDigit; i++) {
                    sb.append("0");
                }
            } else {
                sb.append("000");
            }
            style.setDataFormat(this.format.getFormat(sb.toString()));
            return this;
        }
    }

    /**
     * 计算配置类
     */
    public class Calculation extends Builder {
        /**
         * 插入参考字段名 - 只能一个
         */
        private String referenceFieldName;

        /**
         * 插入后需要合并的字段
         */
        private List<String> spanFieldNames;


        //构造
        private Calculation(Workbook workbook) {
            super(workbook);
        }


        //创建方法
        public void setCalculationModel(String referenceFieldName, String[] calculationFieldNames, String explain, int explainOrder) {
            this.referenceFieldName = referenceFieldName;
            super.setCalculationFieldNames(calculationFieldNames).setExplainAndOrder(explain, explainOrder);
        }

        public void setCalculationModel(String referenceFieldName, String[] spanFieldNames, String[] calculationFieldNames, String explain, int explainOrder) {
            this.referenceFieldName = referenceFieldName;
            this.spanFieldNames = Arrays.asList(spanFieldNames);
            super.setCalculationFieldNames(calculationFieldNames).setExplainAndOrder(explain, explainOrder);
        }

        public void setCalculationModel(String referenceFieldName, Map<String, Integer> calculationFieldNameAndOrder, String explain, int explainOrder) {
            this.referenceFieldName = referenceFieldName;
            super.setCalculationFieldNameAndOrder(calculationFieldNameAndOrder).setExplainAndOrder(explain, explainOrder);
        }

        public void setCalculationModel(String referenceFieldName, String[] spanFieldNames, Map<String, Integer> calculationFieldNameAndOrder, String explain, int explainOrder) {
            this.referenceFieldName = referenceFieldName;
            this.spanFieldNames = Arrays.asList(spanFieldNames);
            super.setCalculationFieldNameAndOrder(calculationFieldNameAndOrder).setExplainAndOrder(explain, explainOrder);
        }

        //--------------------------------------------------------------------------------------------GetSet

        /**
         * 插入参考字段名 - 只能一个
         */
        public String getReferenceFieldName() {
            return referenceFieldName;
        }

        /**
         * 插入参考字段名 - 只能一个
         */
        public Function.Calculation setReferenceFieldName(String referenceFieldName) {
            this.referenceFieldName = referenceFieldName;
            return this;
        }

        /**
         * 插入后需要合并的字段
         */
        public List<String> getSpanFieldNames() {
            return spanFieldNames;
        }

        /**
         * 插入后需要合并的字段
         */
        public Function.Calculation setSpanFieldNames(String... spanFieldNames) {
            this.spanFieldNames = Arrays.asList(spanFieldNames);
            return this;
        }
    }

}
