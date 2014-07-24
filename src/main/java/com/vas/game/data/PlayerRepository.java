package com.vas.game.data;

import com.vas.game.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RooJpaRepository(domainType = Player.class)
public interface PlayerRepository extends JpaSpecificationExecutor<Player>, JpaRepository<Player, Long> {

    List<Player> findPlayerByMobile(String mobile);

    List<Player> findPlayerByMobileAndGameState(String mobile, Integer gameState);

    List<Player> findPlayerByMobileAndLastStageIdAndGameState(String mobile, String gameId, Integer gameState);

    /**
     * Finds a Player by using the mobile and gameId search criteria.
     *
     * @return A list of players whose mobile is an match with the given mobile.
     *         If no players is found, this method returns an empty list.
     */
    @Query("SELECT p FROM Player p WHERE p.mobile=:mobile and p.gameId=:gameId and p.gameState=:gameState order by id desc")
    public List<Player> findCustom(@Param("mobile") String mobile,
                                   @Param("gameId") Long gameId,
                                   @Param("gameState") int gameState);

    @Query("SELECT p FROM Player p WHERE p.mobile=:mobile and p.game.parentPrefix=:gameSeriesPrefix and p.gameState=:gameState order by id desc")
    public List<Player> findByMobileAndGameSeriesAndGameState(
            @Param("mobile") String mobile,
            @Param("gameSeriesPrefix") String gameSeriesPrefix,
            @Param("gameState") int gameState);

    @Query("SELECT p FROM Player p WHERE p.mobile=:mobile and p.game.parentPrefix=:gameSeriesPrefix order by id desc")
    public List<Player> findByMobileAndGameSeries(
            @Param("mobile") String mobile,
            @Param("gameSeriesPrefix") String gameSeriesPrefix);

    @Query("SELECT p from Player p where p.gameId=:gameId order by score desc, lastRequestDate desc")
    public List<Player> findByGameIdOrderByScoreAndLastRequestDate(
            @Param("gameId") Long gameId);

    @Query("SELECT p FROM Player p WHERE p.gameId=:gameId and (p.lastChargeDate is null or date(p.lastChargeDate)<date(:lastChargeDate)) and p.gameState>:gameState order by id")
    public List<Player> findPlayerByGameIdAndLastChargeDateLessThanAndGameStateGreaterThan(
            @Param("gameId") Long gameId,
            @Param("lastChargeDate") Date lastChargeDate,
            @Param("gameState") int gameState);

    @Query("SELECT p FROM Player p WHERE p.gameId=:gameId and p.gameState > -1 order by id desc")
    public List<Player> findActivePlayerByGameId(
            @Param("gameId") Long gameId);

    @Query("SELECT p FROM Player p WHERE p.gameId=:gameId and p.gameState = -1 order by id desc")
    public List<Player> findInActivePlayerByGameId(
            @Param("gameId") Long gameId);

}
