package com.vas.server.service;

import com.vas.engine.xml.model.PlayerState;
import com.vas.util.GameUtil;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameStatisticManager {

    private static Logger logger = Logger.getLogger(GameStatisticManager.class);


    private GameStatisticManager() {
    }

    Map<String, PlayerState> playerStateMap = new ConcurrentHashMap<String, PlayerState>();


    public Map<String, PlayerState> getPlayerStateMap() {
        return playerStateMap;
    }

    public void registerUserWithState(PlayerState playerState) {
        playerStateMap.put(playerState.getPlayerId(), playerState);
        logger.info("player with id = " + playerState.getPlayerId() + " is registered...");
    }

    public void unRegisterUserWithState(PlayerState playerState) {

        if (playerState != null) {

            playerStateMap.remove(playerState.getPlayerId());
            logger.info("player with id = " + playerState.getPlayerId() + " unRegistered...");

        } else {
            logger.error("error: requested playerState for unRegister is null..!!!");
        }
    }

    public PlayerState getPlayerState(String mobile, String gameId) {
        return playerStateMap.get(GameUtil.getPlayerId(mobile, gameId));
    }

    public boolean isUserRegistered(String mobile, String gameId) {
        return playerStateMap.get(GameUtil.getPlayerId(mobile, gameId)) != null;
    }
}