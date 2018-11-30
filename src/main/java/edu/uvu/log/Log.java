package edu.uvu.log;

public class Log {
    private String ip;
    private String dashOne;
    private String dashTwo;
    private String timestamp;
    private String method;
    private String query;
    private String protocol;
    private String httpStatus;
    private String size;
    private String referralUrl;
    private String userAgent;

    public Log(){}

    public Log(String ip, String dashOne, String dashTwo, String timestamp, String method, String query, String protocol, String httpStatus, String size, String referralUrl, String userAgent) {
        this.ip = ip;
        this.dashOne = dashOne.replace("\"","");
        this.dashTwo = dashTwo.replace("\"","");
        this.timestamp = timestamp.replace("[","").replace("]","");
        this.method = method.replace("\"","");
        this.query = query;
        this.protocol = protocol.replace("\"","");
        this.httpStatus = httpStatus;
        this.size = size;
        this.referralUrl = referralUrl.replace("\"","");
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDashOne() {
        return dashOne;
    }

    public void setDashOne(String dashOne) {
        this.dashOne = dashOne;
    }

    public String getDashTwo() {
        return dashTwo;
    }

    public void setDashTwo(String dashTwo) {
        this.dashTwo = dashTwo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getReferralUrl() {
        return referralUrl;
    }

    public void setReferralUrl(String referralUrl) {
        this.referralUrl = referralUrl;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
