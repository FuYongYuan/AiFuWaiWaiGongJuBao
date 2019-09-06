import enumerate.DateType;
import excel.annotation.ExcelField;

import java.util.Date;

/**
 * 用户
 */

public class UserPO {
    @ExcelField(columnName = "用户名称", order = 1)
    private String account;
    @ExcelField(columnName = "用户密码", order = 2)
    private String password;
    @ExcelField(columnName = "最后登录时间", order = 3, dateType = DateType.Year_Month_Day)
    private Date lastLoginDate;
    @ExcelField(columnName = "最后登录IP", order = 4)
    private String lastLoginIP;
    @ExcelField(columnName = "最后尝试登陆时间", order = 5, dateType = DateType.Year_Month_Day)
    private Date lastAttemptLoginDate;
    @ExcelField(columnName = "尝试错误IP", order = 6)
    private String attemptLoginIP;
    @ExcelField(columnName = "尝试错误次数", order = 7)
    private Integer attemptLoginNumber;
    @ExcelField(columnName = "状态", order = 8)
    private Integer state;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public Date getLastAttemptLoginDate() {
        return lastAttemptLoginDate;
    }

    public void setLastAttemptLoginDate(Date lastAttemptLoginDate) {
        this.lastAttemptLoginDate = lastAttemptLoginDate;
    }

    public String getAttemptLoginIP() {
        return attemptLoginIP;
    }

    public void setAttemptLoginIP(String attemptLoginIP) {
        this.attemptLoginIP = attemptLoginIP;
    }

    public Integer getAttemptLoginNumber() {
        return attemptLoginNumber;
    }

    public void setAttemptLoginNumber(Integer attemptLoginNumber) {
        this.attemptLoginNumber = attemptLoginNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserPO{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", lastLoginDate=" + lastLoginDate +
                ", lastLoginIP='" + lastLoginIP + '\'' +
                ", lastAttemptLoginDate=" + lastAttemptLoginDate +
                ", attemptLoginIP='" + attemptLoginIP + '\'' +
                ", attemptLoginNumber=" + attemptLoginNumber +
                ", state=" + state +
                '}';
    }
}
