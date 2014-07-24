package com.vas.server.service.util;

import com.vas.game.model.Game;
import org.apache.log4j.Logger;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 2/27/13 1:34 AM
 */
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
