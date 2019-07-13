package log.service;


import dispose.DateDispose;
import enumerate.DateType;
import excel.ExcelOperate;
import excel.util.ExcelValidationUtil;
import log.config.LogParameters;
import log.model.LogModel;
import log.model.LogSumModel;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日志逻辑类
 */
public class LogService {

    /**
     * 写入日志
     *
     * @param requestName        访问人名称
     * @param requestIP          访问人IP
     * @param interfaceURI       接口
     * @param interfaceDescribe  接口描述
     * @param interfaceCondition 访问所带参数
     * @param interfaceURL       访问全路径
     * @param SQL                执行sql
     */
    public static void writeLog(String requestName, String requestIP, String interfaceURI, String interfaceDescribe, String interfaceCondition, String interfaceURL, String SQL) {
        String logName = DateDispose.Year_Month_Day() + LogParameters.logFileFormat;
        String documentPath = LogParameters.logPath + logName;
        try {
            ExcelValidationUtil.validationDocument(documentPath, LogParameters.sheetName, LogModel.class);
            ExcelOperate eo = new ExcelOperate(documentPath);
            LogModel logModel = new LogModel();
            logModel.requestName = requestName;
            logModel.requestIP = requestIP;
            logModel.requestDate = new Date();
            logModel.interfaceURI = interfaceURI;
            logModel.interfaceDescribe = interfaceDescribe;
            logModel.interfaceCondition = URLDecoder.decode(interfaceCondition, "UTF-8");
            logModel.interfaceURL = URLDecoder.decode(interfaceURL, "UTF-8");
            logModel.SQL = SQL;
            int i = eo.addRow(LogParameters.sheetName, logModel);
            if (i == 0) {
                System.out.println("日志记录失败");
            } else {
                System.out.println("日志已记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 需要统计的日志天集合
     *
     * @param startDate 开始天
     * @param endDate   结束天
     * @return 时间范围
     */
    public static String[] getDayStrings(Date startDate, Date endDate) {
        int x = DateDispose.dateDiff(startDate, endDate, DateType.Day).intValue() + 1;
        String[] days = new String[x];
        for (int i = 0; i < x; i++) {
            days[i] = DateDispose.formatting_Date(startDate, DateType.Year_Month_Day);
            startDate = DateDispose.day_calculate_Date(startDate, 1);
        }
        return days;
    }

    /**
     * 是否存在日志访问总数统计list中
     *
     * @param logSums      日志访问总数统计list
     * @param requestIP    访问IP
     * @param interfaceURI 接口
     * @return 是否在统计中
     */
    private static int isThereLogSumList(List<LogSumModel> logSums, String requestIP, String interfaceURI) {
        for (int i = 0; i < logSums.size(); i++) {
            if (logSums.get(i).getRequestIP().equals(requestIP) && logSums.get(i).getInterfaceURI().equals(interfaceURI)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 分页查询日志文件
     *
     * @param startNumber    开始数
     * @param eachPageNumber 结束数
     * @return 该查询的日志范围
     */
    public static List<String> getLogFileNames(int startNumber, int eachPageNumber) {
        File file = new File(LogParameters.logPath);
        List<String> result = new ArrayList<>();
        if (file.exists()) {
            String[] fileNames = file.list();
            if (fileNames != null && fileNames.length > 0) {
                for (int i = 0; i < fileNames.length - 1; i++) {
                    for (int j = 0; j < fileNames.length - 1 - i; j++) {
                        String sj = fileNames[j].replace(LogParameters.logFileFormat, "");
                        String sj1 = fileNames[j + 1].replace(LogParameters.logFileFormat, "");
                        Date dj = DateDispose.formatting_Date(fileNames[j], DateType.Year_Month_Day);
                        Date dj1 = DateDispose.formatting_Date(fileNames[j + 1], DateType.Year_Month_Day);
                        if (DateDispose.compareDateSize(dj, dj1)) {
                            fileNames[j] = sj1;
                            fileNames[j + 1] = sj;
                        }
                    }
                }

                if (startNumber < fileNames.length) {
                    int max = startNumber + eachPageNumber;
                    if (max > fileNames.length) {
                        max = fileNames.length;
                    }
                    for (int i = startNumber; i < max; i++) {
                        result.add(fileNames[i]);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 查询一共有多少文件
     *
     * @return 返回目录下文档数量
     */
    public static int getLogFileNamesCount() {
        File file = new File(LogParameters.logPath);
        if (file.exists()) {
            return file.list().length;
        } else {
            return 0;
        }
    }
}
