package com.vas.game.service;

import com.vas.game.data.GameRepository;
import com.vas.game.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Qualifier("gameRepository")
    @Autowired
    GameRepository gameRepository;

    public long countAllGames() {
        return gameRepository.count();
    }

    public void deleteGame(Game game) {
        gameRepository.delete(game);
    }

    public Game findGame(Long id) {
        return gameRepository.findOne(id);
    }

    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    public List<Game> findGameEntries(int firstResult, int maxResults) {
        return gameRepository.findAll(new PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    public Game updateGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public List<Game> findGame(String prefix) {
        return gameRepository.findByPrefix(prefix);
    }

    @Override
    public List<Game> findGame(String prefix, String parentPrefix) {
        return gameRepository.findByPrefixAndParentPrefix(prefix, parentPrefix);
    }

    @Override
    public List<Game> findGameBySeries(String parentPrefix) {
        List<Game> games = gameRepository.findByParentPrefix(parentPrefix.toUpperCase());
        for (Game game : games) {
            System.out.println("in GameServiceImpl" + new String(game.getFileName()));
        }

        return games;
    }
}
