package com.vas.game.service;

import com.vas.game.data.PlayerBlackListRepository;
import com.vas.game.model.PlayerBlackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: farhad
 * Date: 4/10/14
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */


@Service
@Transactional
public class PlayerBlackListImpl implements PlayerBlackListService {
    @Qualifier("playerBlackListRepository")
    @Autowired
    PlayerBlackListRepository playerBlackListRepository;

    @Override
    public List<PlayerBlackList> findByMobile(String mobile) {
        return playerBlackListRepository.findByMobile(mobile);
    }

    @Override
    public List<PlayerBlackList> findByMobileAndGameId(String mobile, Long gameId) {
        return playerBlackListRepository.findByMobileAndGameId(mobile, gameId);
    }

    @Override
    public List<PlayerBlackList> findByMobileAndGamePrefix(String mobile, String gamePrefix) {
        return playerBlackListRepository.findByMobileAndGamePrefix(mobile, gamePrefix);
    }
}
