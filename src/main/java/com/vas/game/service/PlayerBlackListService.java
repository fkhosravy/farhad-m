package com.vas.game.service;

import com.vas.game.model.PlayerBlackList;
import org.springframework.roo.addon.layers.service.RooService;

import java.util.List;

@RooService(domainTypes = {PlayerBlackList.class})
public interface PlayerBlackListService {

    public abstract List<PlayerBlackList> findByMobile(String mobile);

    public abstract List<PlayerBlackList> findByMobileAndGameId(String mobile, Long gameId);

    public abstract List<PlayerBlackList> findByMobileAndGamePrefix(String mobile, String gamePrefix);

}
