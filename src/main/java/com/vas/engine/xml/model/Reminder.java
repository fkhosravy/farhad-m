package com.vas.engine.xml.model;

/**
 * Created with IntelliJ IDEA.
 * User: farhad
 * Date: 4/18/14
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Reminder {
    public int hour;
    public String message;
    public String period;
    public String action;
    public String header;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getMessage() {
        if (message == null)
            return "";
        else
            return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getHeader() {
        if (header == null)
            return "";
        else
            return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Reminder(int hour, String message, String period, String action, String header) {
        this.hour = hour;
        this.message = message;
        this.period = period;
        this.action = action;
        this.header = header;
    }

    public Reminder() {
    }
}
