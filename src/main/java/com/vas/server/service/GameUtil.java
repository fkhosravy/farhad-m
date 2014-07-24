package com.vas.server.service;

import com.vas.game.model.Game;
import com.vas.game.model.Player;
import com.vas.game.service.PlayerService;
import com.vas.server.service.util.RandomGame;
import com.vas.util.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GameUtil {

    @Autowired
    PlayerService _playerService;

    RandomGame generateNewRandomGame(String playerNumber, List<Game> gameListRelatedToSeries) {

        List<Game> gameList = new ArrayList<Game>();
        gameList.addAll(gameListRelatedToSeries);

        List<Player> alreadyPlayedGame = _playerService.findPlayer(playerNumber);
        for (Player nextPlayer : alreadyPlayedGame) {
            Game foundGame = findGameByIdInList(gameList, nextPlayer.getGameId());
            if (foundGame != null)
                gameList.remove(foundGame);
        }

        if (gameList.size() > 0) {
            RandomGenerator randomGenerator = new RandomGenerator();
            int randomIndex = randomGenerator.getRandomTO(gameList.size());
            Game randomGame = gameList.get(randomIndex - 1);
            int randomGameId = randomGenerator.getRandomTO(999);
            return new RandomGame(randomGame, randomGameId);
        } else
            return null;
    }

    private Game findGameByIdInList(List<Game> gameListRelatedToSeries, Long gameId) {
        for (Game nextGame : gameListRelatedToSeries) {
            if (nextGame.getId().equals(gameId))
                return nextGame;
        }
        return null;
    }
}