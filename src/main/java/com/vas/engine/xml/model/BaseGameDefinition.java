package com.vas.engine.xml.model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/26/13 1:46 AM
 */
public class BaseGameDefinition {
    private static Logger logger = Logger.getLogger(BaseGameDefinition.class);

    private String name;
    private String code;
    private String desc;
    private String serviceID;

    private boolean _replaceable;
    private boolean _sendNextQuestionByScore;
    private int _pricePerDay;
    private int _zeroChargePrice;
    private String _series;

    private int startRange;
    private int endRange;

    private List<GameParameter> errorMsg = new ArrayList<GameParameter>();

    private List<GameParameter> parameters = new ArrayList<GameParameter>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getGameCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public List<GameParameter> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(List<GameParameter> errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<GameParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<GameParameter> parameters) {
        this.parameters = parameters;
    }

    public boolean isReplaceable() {
        return _replaceable;
    }

    public void setReplaceable(boolean replaceable) {
        _replaceable = replaceable;
    }

    public int getPricePerDay() {
        return _pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        _pricePerDay = pricePerDay;
    }

    public boolean getSendNextQuestionByScore()
    {
        return _sendNextQuestionByScore;
    }

    public void setSendNextQuestionByScore(boolean sendNextQuestionByScore)
    {
        _sendNextQuestionByScore = sendNextQuestionByScore;
    }

    public int getZeroChargePrice()
    {
        return _zeroChargePrice;
    }
    public void setZeroChargePrice(int zeroChargePrice)
    {
        this._zeroChargePrice = zeroChargePrice;
    }
    public String getSeries() {
        return _series;
    }

    public void setSeries(String series) {
        _series = series;
    }

    public int getStartRange() {
        return startRange;
    }

    public void setStartRange(int startRange) {
        this.startRange = startRange;
    }

    public int getEndRange() {
        return endRange;
    }

    public void setEndRange(int endRange) {
        this.endRange = endRange;
    }
}
