package com.vas.game.service;

import com.vas.game.model.PlayerStateLog;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = {PlayerStateLog.class})
public interface PlayerStateLogService {

    public abstract PlayerStateLog findPlayerStateLog(Long id);

//    public abstract List<PlayerStateLog> findAllPlayerStateLog();

    public abstract void savePlayerStateLog(PlayerStateLog playerStateLog);

//    public abstract Player updatePlayerStateLogService(PlayerStateLogService playerStateLogService);

//    public abstract List<PlayerStateLogService> findPlayerStateLogService(String mobile);
}
