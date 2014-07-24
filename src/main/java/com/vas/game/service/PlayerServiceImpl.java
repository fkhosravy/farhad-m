package com.vas.game.service;

import com.vas.game.data.PlayerRepository;
import com.vas.game.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    @Qualifier("playerRepository")
    @Autowired
    PlayerRepository playerRepository;

    public long countAllPlayers() {
        return playerRepository.count();
    }

    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }

    public Player findPlayer(Long id) {
        return playerRepository.findOne(id);
    }

    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> findPlayerEntries(int firstResult, int maxResults) {
        return playerRepository.findAll(new PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

    public void savePlayer(Player player) {
        playerRepository.save(player);
    }

    public Player updatePlayer(Player player) {
        return playerRepository.saveAndFlush(player);
    }

    @Override
    public List<Player> findPlayer(String mobile) {
        return playerRepository.findPlayerByMobile(mobile);
    }

    @Override
    public Player findLastActivePlay(String mobile) {
        List<Player> foundPlayerList = playerRepository.findPlayerByMobileAndGameState(mobile, 0);
        if (foundPlayerList.isEmpty())
            return null;
        else
            return foundPlayerList.get(foundPlayerList.size() - 1);
    }

    @Override
    public Player findLastActivePlayForGameId(String mobile, Long gameId) {

        //List<Player> foundPlayerList = playerRepository.findPlayerByMobileAndLastStageIdAndGameState(mobile, gameId, 0);
        List<Player> foundPlayerList = playerRepository.findCustom(mobile, gameId, 0);
        if (foundPlayerList.isEmpty())
            return null;
        else
            return foundPlayerList.get(foundPlayerList.size() - 1);
    }

    @Override
    public Player findLastActivePlayByMobileAndGameSeries(String mobile, String gameSeriesCode) {
        gameSeriesCode = gameSeriesCode.toUpperCase();
        List<Player> foundPlayerList = playerRepository.findByMobileAndGameSeries(mobile, gameSeriesCode);
        if (foundPlayerList.isEmpty())
            return null;
        else
            return foundPlayerList.get(foundPlayerList.size() - 1);
    }

    @Override
    public List<Player> findByGameIdOrderByScore(Long gameId) {
        List<Player> foundPlayerList = playerRepository.findByGameIdOrderByScoreAndLastRequestDate(gameId);
        if (foundPlayerList.isEmpty())
            return null;
        else
            return foundPlayerList;
    }

    @Override
    public List<Player> findPlayerByGameIdAndLastChargeDateLessThanAndGameStateGreaterThan(Long gameId, Date chargeDate, int gameState) {
        List<Player> playerList = playerRepository.findPlayerByGameIdAndLastChargeDateLessThanAndGameStateGreaterThan(gameId, chargeDate, gameState);
        if (playerList.isEmpty())
            return null;
        else
            return playerList;
    }

    @Override
    public List<Player> findActivePlayerByGameId(Long gameId) {
        List<Player> playerList = playerRepository.findActivePlayerByGameId(gameId);
        if (playerList.isEmpty())
            return null;
        else
            return playerList;
    }

    @Override
    public List<Player> findInActivePlayerByGameId(Long gameId) {
        List<Player> playerList = playerRepository.findInActivePlayerByGameId(gameId);
        if (playerList.isEmpty())
            return null;
        else
            return playerList;
    }

}
