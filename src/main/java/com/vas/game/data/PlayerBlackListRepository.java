package com.vas.game.data;

import com.vas.game.model.PlayerBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RooJpaRepository(domainType = PlayerBlackList.class)
public interface PlayerBlackListRepository extends JpaRepository<PlayerBlackList, Long>, JpaSpecificationExecutor<PlayerBlackList> {

    public List<PlayerBlackList> findByMobile(String mobile);

    public List<PlayerBlackList> findByMobileAndGameId(String mobile, Long gameId);

    public List<PlayerBlackList> findByMobileAndGamePrefix(String mobile, String gamePrefix);

}
