package com.vas.entity;

import com.vas.engine.xml.model.GameParameter;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/16/13 1:08 AM
 */
public class GameInfo {
    private static Logger logger = Logger.getLogger(GameInfo.class);

    String desc;
    String name;
    String gameCode;
    //added by biabani for send serviceID by game
    String serviceID;

    //Map<Integer, GameParameter> parameterMap;
    List<GameParameter> parameterList;

    List<GameEventDescription> systemEventList;
    List<GameEventDescription> userEventList;

    List<String> winConditionList;
    List<String> lossConditionList;
    private String _faDesc;

    String winConditionDesc;
    String loosConditionDesc;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public void setParameterList(List<GameParameter> parameterList) {
        this.parameterList = parameterList;
    }

    public void setSystemEventList(List<GameEventDescription> systemEventList) {
        this.systemEventList = systemEventList;
    }

    public List<GameEventDescription> getSystemEventList() {
        return systemEventList;
    }

    public void setUserEventList(List<GameEventDescription> userEventList) {
        this.userEventList = userEventList;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public String getGameCode() {
        return gameCode;
    }

    public String getServiceID() {
        return serviceID;
    }

    public List<GameParameter> getParameterList() {
        return parameterList;
    }

    public List<GameEventDescription> getEventList() {
        return systemEventList;
    }

    public List<GameEventDescription> getUserEventList() {
        return userEventList;
    }

    public void setDetailDesc(String faDesc) {
        _faDesc = faDesc;
    }

    public String getDetailDesc() {
        return _faDesc;
    }

    public List<String> getWinConditionList() {
        return winConditionList;
    }

    public void setWinConditionList(List<String> winConditionList) {
        this.winConditionList = winConditionList;
    }

    public List<String> getLossConditionList() {
        return lossConditionList;
    }

    public void setWinConditionDesc(String winConditionDesc) {
        this.winConditionDesc = winConditionDesc;
    }

    public void setLoosConditionDesc(String loosConditionDesc) {
        this.loosConditionDesc = loosConditionDesc;
    }

    public String getWelcomeMessage() {
        StringBuffer sb = new StringBuffer();
        sb.append("به بازی ").append(desc).append(" خوش آمدید.").
                append("\n").append(" ");

        sb.append("پارامترهای بازی عبارتند از").append("\n ");

        for (GameParameter nextGameParameter : parameterList) {

            sb.append(nextGameParameter.getDetailDesc());
            if (!nextGameParameter.isSystemParameter())
                sb.append(" با کد ").append(nextGameParameter.getCode());

            if (!nextGameParameter.isSystemParameter())
                sb.append(" و");
            else
                sb.append(" با");

            sb.append(" مقدار اولیه ").append(nextGameParameter.getValue()).
                    append("\n ");
        }

        sb.append("رخداد های قابل استفاده در بازی عبارتند از:").append("\n ");

        for (GameEventDescription nextGameEventDescription : userEventList) {

            UserGameEventDesc nextUserGameEventDesc = (UserGameEventDesc) nextGameEventDescription;
            sb.
                    append(nextUserGameEventDesc.getDesc()).
                    append(" با کد ").append(nextUserGameEventDesc.getCode()).
                    append(" که در آن ").append(nextUserGameEventDesc.getDetailDesc()).
                    append("\n ");
        }

        sb
                .append(winConditionDesc)
                .append("\n ")
                .append(loosConditionDesc);

        return sb.toString();
    }

    public void setLossConditionList(List<String> lossConditionList) {
        this.lossConditionList = lossConditionList;
    }
}

