package com.vas.engine.xml.model;


import java.util.ArrayList;
import java.util.List;

public class LoosCondition {

    private String desc;
    private String userNotification;

    List<String> functionList = new ArrayList<String>();


    public LoosCondition() {
    }

    public LoosCondition(String desc, String userNotification) {
        this.desc = desc;
        this.userNotification = userNotification;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<String> functionList) {
        this.functionList = functionList;
    }

    public String getUserNotification() {
        return userNotification;
    }

    public void setUserNotification(String userNotification) {
        this.userNotification = userNotification;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" desc:").append(desc).append(" userNotification:").append(userNotification);

        for (String nextFunction : functionList) {
            sb.append("\n function:" + nextFunction);
        }
        return sb.toString();

    }
}