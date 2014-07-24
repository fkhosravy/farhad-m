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
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    private String parentPrefix;

    @NotNull
    @Size(max = 20)
    private String prefix;

    private Boolean replacable;
//    private Boolean replaceable;

    @Min(0L)
    private Integer chargePerDay;

    @Min(0L)
    @Max(99L)
    private int version;

    @NotNull
    @Size(max = 100)
    private String fileName;

    @NotNull
    @Size(max = 100)
    private String serviceID;

    @Size(max = 25)
    private String reserved1;

    @Size(max = 25)
    private String reserved2;

    public String getParentPrefix() {
        return this.parentPrefix;
    }

    public void setParentPrefix(String parentPrefix) {
        this.parentPrefix = parentPrefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Boolean getReplacable() {
        return this.replacable;
    }

    public void setReplacable(Boolean replacable) {
        this.replacable = replacable;
    }

    public Integer getChargePerDay() {
        return this.chargePerDay;
    }

    public void setChargePerDay(Integer chargePerDay) {
        this.chargePerDay = chargePerDay;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
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

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Game)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Game rhs = (Game) obj;
        return new EqualsBuilder().append(chargePerDay, rhs.chargePerDay).append(fileName, rhs.fileName).append(id, rhs.id).append(parentPrefix, rhs.parentPrefix).append(prefix, rhs.prefix).append(replacable, rhs.replacable).append(reserved1, rhs.reserved1).append(reserved2, rhs.reserved2).append(version, rhs.version).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(chargePerDay).append(fileName).append(id).append(parentPrefix).append(prefix).append(replacable).append(reserved1).append(reserved2).append(version).toHashCode();
    }
}
