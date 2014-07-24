package com.vas.game.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@RooJavaBean
@RooToString
@RooJpaEntity
@RooEquals
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;

    @NotNull
    @Size(max = 12)
    private String mobile;

    @NotNull
    private Long gameId;

    private Integer gameState;

    @NotNull
    @Size(max = 8)
    private String lastStageId;

    @NotNull
    private Integer score;
    @NotNull
    private Integer questionNo;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastRequestDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastChargeDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date registerDate;

    @Size(max = 25)
    private String reserved1;

    @Size(max = 25)
    private String reserved2;

    @Size(max = 25)
    private String reserved3;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "playerId")
    private Set<PlayerState> PlayerStates = new HashSet<PlayerState>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameId", insertable = false, updatable = false)
    protected Game game;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(Integer questionNo) {
        this.questionNo = questionNo;
    }

    public void incQuestionNo()
    {
        this.questionNo++;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void addScore(Integer score) {
        this.score += score;
    }

    public Game getGame() {
        return game;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getGameId() {
        return this.gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Integer getGameState() {
        return this.gameState;
    }

    public void setGameState(Integer gameState) {
        this.gameState = gameState;
    }

    public String getLastStageId() {
        return this.lastStageId;
    }

    public void setLastStageId(String lastStageId) {
        this.lastStageId = lastStageId;
    }

    public Date getLastRequestDate() {
        return this.lastRequestDate;
    }

    public void setLastRequestDate(Date lastRequestDate) {
        this.lastRequestDate = lastRequestDate;
    }

    public Date getRegisterDate() {
        return this.registerDate;
    }

    public Date getLastChargeDate() {
        return lastChargeDate;
    }

    public void setLastChargeDate(Date lastChargeDate) {
        this.lastChargeDate = lastChargeDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getReserved1() {
        return this.reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getReserved3() {
        return this.reserved3;
    }

    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }

    public Set<PlayerState> getPlayerStates() {
        return this.PlayerStates;
    }

    public void setPlayerStates(Set<PlayerState> PlayerStates) {
        this.PlayerStates = PlayerStates;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Player rhs = (Player) obj;
        return new EqualsBuilder().append(gameId, rhs.gameId).append(score, rhs.score).append(questionNo, rhs.questionNo).
                append(gameState, rhs.gameState).append(id, rhs.id).append(lastRequestDate, rhs.lastRequestDate).
                append(lastChargeDate, rhs.lastChargeDate).append(lastStageId, rhs.lastStageId).append(mobile, rhs.mobile).
                append(registerDate, rhs.registerDate).append(reserved1, rhs.reserved1).append(reserved2, rhs.reserved2).
                append(reserved3, rhs.reserved3).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(gameId).append(score).append(gameState).append(id).append(lastRequestDate).
                append(lastChargeDate).append(lastStageId).append(mobile).append(registerDate).append(reserved1).
                append(reserved2).append(reserved3).toHashCode();
    }

}
