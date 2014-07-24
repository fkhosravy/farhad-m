package com.vas.game.service;

import com.vas.game.model.Player;
import org.springframework.roo.addon.layers.service.RooService;

import java.util.Date;
import java.util.List;

@RooService(domainTypes = {Player.class})
public interface PlayerService {
    public static final int GAME_OFF_STATE = -1;

    public abstract long countAllPlayers();

    public abstract void deletePlayer(Player player);

    public abstract Player findPlayer(Long id);

    public abstract List<Player> findAllPlayers();

    public abstract List<Player> findPlayerEntries(int firstResult, int maxResults);

    public abstract void savePlayer(Player player);

    public abstract Player updatePlayer(Player player);

    public abstract List<Player> findPlayer(String mobile);

    public abstract Player findLastActivePlay(String mobile);

    public abstract List<Player> findByGameIdOrderByScore(Long gameId);

    public abstract Player findLastActivePlayForGameId(String mobile, Long gameId);

    public abstract Player findLastActivePlayByMobileAndGameSeries(String mobile, String gameSeriesCode);

    public abstract List<Player> findPlayerByGameIdAndLastChargeDateLessThanAndGameStateGreaterThan(Long gameId, Date chargeDate, int gameState);

    public abstract List<Player> findActivePlayerByGameId(Long gameId);

    public abstract List<Player> findInActivePlayerByGameId(Long gameId);
}
