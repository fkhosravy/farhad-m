package com.vas.game.service;

import com.vas.game.model.Game;
import org.springframework.roo.addon.layers.service.RooService;

import java.util.List;

@RooService(domainTypes = {Game.class})
public interface GameService {

    public abstract long countAllGames();


    public abstract void deleteGame(Game game);


    public abstract Game findGame(Long id);


    public abstract List<Game> findAllGames();


    public abstract List<Game> findGameEntries(int firstResult, int maxResults);


    public abstract void saveGame(Game game);


    public abstract Game updateGame(Game game);

    public List<Game> findGame(String prefix);

    public List<Game> findGame(String prefix, String parentPrefix);

    public List<Game> findGameBySeries(String parentPrefix);

}
