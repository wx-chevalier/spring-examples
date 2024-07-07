package com.taotao.cloud.realtime.behavior.analysis.loginfail_detect;


public class LoginFailWarning {
    private Long userId;
    private Long firstFailTime;
    private Long lastFailTime;
    private String warningMsg;

    public LoginFailWarning() {
    }

    public LoginFailWarning(Long userId, Long firstFailTime, Long lastFailTime, String warningMsg) {
        this.userId = userId;
        this.firstFailTime = firstFailTime;
        this.lastFailTime = lastFailTime;
        this.warningMsg = warningMsg;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFirstFailTime() {
        return firstFailTime;
    }

    public void setFirstFailTime(Long firstFailTime) {
        this.firstFailTime = firstFailTime;
    }

    public Long getLastFailTime() {
        return lastFailTime;
    }

    public void setLastFailTime(Long lastFailTime) {
        this.lastFailTime = lastFailTime;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

    @Override
    public String toString() {
        return "LoginFailWarning{" +
                "userId=" + userId +
                ", firstFailTime=" + firstFailTime +
                ", lastFailTime=" + lastFailTime +
                ", warningMsg='" + warningMsg + '\'' +
                '}';
    }
}
