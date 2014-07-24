package com.vas.game.data;

import com.vas.game.model.PlayerStateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RooJpaRepository(domainType = PlayerStateLog.class)
public interface PlayerStateLogRepository extends JpaRepository<PlayerStateLog, Long>, JpaSpecificationExecutor<PlayerStateLog> {

    public List<PlayerStateLog> findByPlayerId(Long id);

}
