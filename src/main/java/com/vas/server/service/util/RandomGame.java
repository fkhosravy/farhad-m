package com.vas.server.service.util;

import com.vas.game.model.Game;
import org.apache.log4j.Logger;

public class RandomGame {
    private static Logger logger = Logger.getLogger(RandomGame.class);

    Game game;
    int newId;

    public RandomGame(Game game, int newId) {
        this.game = game;
        this.newId = newId;
    }

    public Game getGame() {
        return game;
    }

    public int getNewId() {
        return newId;
    }
}
