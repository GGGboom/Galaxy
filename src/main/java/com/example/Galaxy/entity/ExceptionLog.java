package com.example.Galaxy.entity;

import java.util.Date;

public class ExceptionLog {
    private Long logId;

    private Long userId;

    private String userName;

    private String ip;

    private Date createTime;

    private String url;

    private String urlArgs;

    private String method;

    private String httpMethod;

    private String excMsg;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getUrlArgs() {
        return urlArgs;
    }

    public void setUrlArgs(String urlArgs) {
        this.urlArgs = urlArgs == null ? null : urlArgs.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod == null ? null : httpMethod.trim();
    }

    public String getExcMsg() {
        return excMsg;
    }

    public void setExcMsg(String excMsg) {
        this.excMsg = excMsg == null ? null : excMsg.trim();
    }
}