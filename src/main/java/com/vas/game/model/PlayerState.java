package com.vas.game.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@RooJavaBean
@RooToString
@RooJpaEntity
@RooEquals
public class PlayerState {

    @NotNull
    @Size(max = 25)
    private String paramName;

    @NotNull
    private Long paramValue;

    @NotNull
    @Size(max = 25)
    private String paramLabel;

    @Min(0L)
    @Max(99L)
    private int version;

    @Size(max = 25)
    private String reserved1;

    @Size(max = 25)
    private String reserved2;

    @ManyToOne
    private Player playerId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version_")
    private Integer version_;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion_() {
        return this.version_;
    }

    public void setVersion_(Integer version) {
        this.version_ = version;
    }

    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Long getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(Long paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamLabel() {
        return this.paramLabel;
    }

    public void setParamLabel(String paramLabel) {
        this.paramLabel = paramLabel;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public Player getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(Player playerId) {
        this.playerId = playerId;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PlayerState)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        PlayerState rhs = (PlayerState) obj;
        return new EqualsBuilder().append(id, rhs.id).append(paramLabel, rhs.paramLabel).append(paramName, rhs.paramName).append(paramValue, rhs.paramValue).append(playerId, rhs.playerId).append(reserved1, rhs.reserved1).append(reserved2, rhs.reserved2).append(version, rhs.version).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(id).append(paramLabel).append(paramName).append(paramValue).append(playerId).append(reserved1).append(reserved2).append(version).toHashCode();
    }
}
