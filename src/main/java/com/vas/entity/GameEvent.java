package com.vas.entity;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/16/13 1:23 AM
 */
public class GameEvent {

    public static final int MILLISEC_IN_MINUTES = 60 * 1000;
    private static Logger logger = Logger.getLogger(GameEvent.class);

    String desc;

    String name;
    String eventCode;

    List<String> functionList;

    private String _faDesc;
    private int _startDelay = 0;
    private int _lifeTime = 1;

    long creationTime = System.currentTimeMillis();
    long fireTime = System.currentTimeMillis();
    long endTim = 0;
    int firedCount = 0;

    private boolean systemEvent = false;


    public GameEvent(int lifeTime, int startDelay, boolean systemEvent) {
        this(lifeTime, startDelay);
        this.systemEvent = systemEvent;
    }

    public GameEvent(int lifeTime, int startDelay) {
        this(lifeTime);
        _startDelay = startDelay;
    }

    public GameEvent(int lifeTime) {
        _lifeTime = lifeTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }


    public long getFireTime() {
        return creationTime + (_startDelay) * MILLISEC_IN_MINUTES;
    }

//    public void setFireTime(long fireTime) {
//        this.fireTime = fireTime;
//    }

    public long getEndTim() {
        return creationTime + (_startDelay) * MILLISEC_IN_MINUTES + (_lifeTime) * MILLISEC_IN_MINUTES;
    }

    public void setEndTim(long endTim) {
        this.endTim = endTim;
    }

    public int getFiredCount() {
        return firedCount;
    }

    public void setFiredCount(int firedCount) {
        this.firedCount = firedCount;
    }

    public List<String> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<String> functionList) {
        this.functionList = functionList;
    }

    public String getFaDesc() {
        return _faDesc;
    }

    public void setFaDesc(String faDesc) {
        _faDesc = faDesc;
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

    public boolean isSystemEvent() {
        return systemEvent;
    }

    public void setSystemEvent(boolean systemEvent) {
        this.systemEvent = systemEvent;
    }

    @Override
    public String toString() {
        String result = "";
//        result += " name: " + name;
//        result += " code: " + eventCode;
        result += " startDelay: " + getStartDelay();
        result += " lifeTime: " + getLifeTime();
        result += " startTime: " + getFireTime();
        result += " System: " + isSystemEvent();
//        result += " startDate: " + startDate;
//        result += " endDate: " + endDate;
//        result += " function: " + function;

        return result;
    }


}