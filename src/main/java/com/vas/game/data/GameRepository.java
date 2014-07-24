package com.vas.game.data;

import com.vas.game.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RooJpaRepository(domainType = Game.class)
public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {

    public List<Game> findByParentPrefix(String parentPrefix);

    public List<Game> findByPrefix(String prefix);

    public List<Game> findByPrefixAndParentPrefix(String prefix, String parentPrefix);
}
