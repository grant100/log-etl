package edu.uvu.log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private String ip;
    private String logName;
    private String username;
    private Date timestamp;
    private String method;
    private String query;
    private String protocol;
    private String httpStatus;
    private String size;
    private String referrer;
    private String userAgent;
    private String fullQuery;

    public Log() {
    }

    public Log(String ip, String logName, String username, String timestamp, String method, String query, String protocol, String httpStatus, String size, String referrer, String userAgent, String fullQuery) throws ParseException {
        this.ip = ip;
        this.logName = logName.replace("\"", "");
        this.username = username;
        DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        this.timestamp = df.parse(timestamp.replace("[", "").substring(0, timestamp.indexOf(":") - 1));
        this.method = method.replace("\"", "");
        this.query = query;

        String p = protocol.replace("\"", "");
        if (p.contains("HTTP")) {
            this.protocol = p;
        } else {
            this.protocol = "?";
        }

        try {
            Integer.parseInt(httpStatus);
            this.httpStatus = httpStatus;
        } catch (Exception e) {
            this.httpStatus = "?";
        }

        if (size != null && !size.isEmpty()) {
            try {
                Integer.parseInt(size);
                this.size = size;
            } catch (Exception e) {
                this.size = "?";
            }
        }
        this.referrer = referrer.replace("\"", "");
        this.userAgent = userAgent;
        this.fullQuery = fullQuery;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
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

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getFullQuery() {
        return this.fullQuery;
    }

    public void setFullQuery(String fullQuery) {
        this.fullQuery = fullQuery;
    }
}
