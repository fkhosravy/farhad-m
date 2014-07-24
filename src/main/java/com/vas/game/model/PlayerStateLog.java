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
public class PlayerStateLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private Long gameId;
    private Long playerId;

    @Size(max = 8)
    private String currentStage;

    @NotNull
    @Size(max = 12)
    private String mobile = "";

    @Size(max = 15)
    private String firstToken;

    @Size(max = 15)
    private String secondToken;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date setTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstToken() {
        return firstToken;
    }

    public void setFirstToken(String firstToken) {
        this.firstToken = firstToken;
    }

    public String getSecondToken() {
        return secondToken;
    }

    public void setSecondToken(String secondToken) {
        this.secondToken = secondToken;
    }

    public Date getSetTime() {
        return setTime;
    }

    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PlayerStateLog)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        PlayerStateLog p = (PlayerStateLog) obj;
        return new EqualsBuilder().append(id, p.id).append(playerId, p.playerId).append(gameId, p.gameId).
                append(mobile, p.mobile).append(currentStage, p.currentStage).append(firstToken, p.firstToken).
                append(secondToken, p.secondToken).append(setTime, p.setTime).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(id).append(playerId).append(gameId).append(mobile).append(currentStage).
                append(firstToken).append(secondToken).append(setTime).toHashCode();
    }
}
