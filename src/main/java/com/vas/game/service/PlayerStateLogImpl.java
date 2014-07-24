package com.vas.game.service;

import com.vas.game.data.PlayerStateLogRepository;
import com.vas.game.model.PlayerStateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: farhad
 * Date: 4/10/14
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */


@Service
@Transactional
public class PlayerStateLogImpl implements PlayerStateLogService {
    @Qualifier("playerStateLogRepository")
    @Autowired
    PlayerStateLogRepository playerStateLogRepository;

    @Override
    public PlayerStateLog findPlayerStateLog(Long id) {
        return playerStateLogRepository.findOne(id);
    }

    @Override
    public void savePlayerStateLog(PlayerStateLog playerStateLog) {
        playerStateLogRepository.save(playerStateLog);
    }
}
