package com.vas.game.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: farhad
 * Date: 4/8/14
 * Time: 10:21 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@RooJavaBean
@RooToString
@RooJpaEntity
@RooEquals
public class PlayerBlackList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private Long gameId;

    @NotNull
    @Size(max = 12)
    private String mobile = "";

    @Size(max = 20)
    private String gamePrefix;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date setTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGamePrefix() {
        return gamePrefix;
    }

    public void setGamePrefix(String gamePrefix) {
        this.gamePrefix = gamePrefix;
    }

    public Date getSetTime() {
        return setTime;
    }

    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PlayerBlackList)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        PlayerBlackList p = (PlayerBlackList) obj;
        return new EqualsBuilder().append(id, p.id).append(gameId, p.gameId).
                append(mobile, p.mobile).append(gamePrefix, p.gamePrefix).append(setTime, p.setTime).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(id).append(gameId).append(mobile).append(gamePrefix).append(setTime).toHashCode();
    }
}
