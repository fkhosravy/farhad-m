package com.vas.entity;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/16/13 1:23 AM
 */
public class GameEventDescription {

    private static Logger logger = Logger.getLogger(GameEventDescription.class);

    String desc;

    String name;
    String eventCode;

//    Date startDate;
//    Date endDate;

    //    String function;
    List<String> functionList;

    private String _faDesc;
    private int _startDelay = 0;
    private int _lifeTime = 1;


    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String eventCode) {
        this.eventCode = eventCode;
    }


    @Override
    public String toString() {
        String result = "";
        result += " name: " + name;
        result += " code: " + eventCode;
//        result += " percent: " + occurrencePercent;
//        result += " startDate: " + startDate;
//        result += " endDate: " + endDate;
//        result += " function: " + function;

        return result;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return eventCode;
    }

    public void setDetailDesc(String faDesc) {
        _faDesc = faDesc;
    }

    public String getDetailDesc() {
        return _faDesc;
    }

    public List<String> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<String> functionList) {
        this.functionList = functionList;
    }

    public int getStartDelay() {
        return _startDelay;
    }

    public void setStartDelay(int startDelay) {
        _startDelay = startDelay;
    }

    public int getLifeTime() {
        return _lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        _lifeTime = lifeTime;
    }

}
