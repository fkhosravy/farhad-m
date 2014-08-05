package com.vas.engine.xml.model;

import com.vas.util.GameUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/16/13 1:17 AM
 */
public class PlayerState implements Cloneable {

    private static Logger logger = Logger.getLogger(PlayerState.class);

    private String playerId;

    private String currentStateId;

    private List<GameParameter> playerParameters = new ArrayList<GameParameter>();

    private String playerMobile;

    public PlayerState(String playerMobile, String gameId, String currentStateId) {
        this(GameUtil.getPlayerId(playerMobile, gameId), currentStateId);
        this.playerMobile = playerMobile;
    }

    public PlayerState(String playerId, String currentStateId) {
        this.playerId = playerId;
        this.currentStateId = currentStateId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getCurrentStateId() {
        return currentStateId;
    }

    public void setCurrentStateId(String currentStateId) {
        this.currentStateId = currentStateId;
    }

    public List<GameParameter> getPlayerParameters() {
        return playerParameters;
    }

    public GameParameter findPlayerParameter(String parameterCode) {
        GameParameter foundGameParameter = null;

        if (playerParameters != null) {
            for (GameParameter nextPlayerParameter : playerParameters) {
                if (nextPlayerParameter.getCode().equals(parameterCode)) {
                    foundGameParameter = nextPlayerParameter;
                    break;
                }
            }
            return foundGameParameter;

        } else {
            return foundGameParameter;
        }
    }

    public void setPlayerParameters(List<GameParameter> playerParameters) {
        this.playerParameters = playerParameters;
    }

    public String getPlayerMobile() {
        return playerMobile;
    }

    public String getNotificationString() {
        StringBuilder sb = new StringBuilder();
        //sb.append("وضعیت مقادیر آیتم های بازی  ");
        sb.append("[ ");
        for (GameParameter nextPlayerParameter : playerParameters) {
            //sb.append(nextPlayerParameter.getCode().toUpperCase()).append(":").append(nextPlayerParameter.getValue()).append(" ");
            sb.append(nextPlayerParameter.getName().toUpperCase()).append(":").append(nextPlayerParameter.getValue()).append(" ");
        }
        sb.append("]");

        return sb.toString();
    }

    public String toString() {
        String result = "";
        result += " currentStateId: " + currentStateId;
        //result += " paramCode: " + paramCode;

        return result;
    }

}
