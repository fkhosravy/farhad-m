package com.vas.engine.xml.model;


import org.apache.log4j.Logger;

public class GameParameter implements Cloneable {

    private static Logger logger = Logger.getLogger(GameParameter.class);

    private String code;
    private String name;
    private String desc;
    private String detailDesc;
    //private long value = 0;
    private long value = 0;

    private boolean systemParameter = false;

//    private String paramName;
//    private String paramCode;

    public GameParameter() {
    }

    public GameParameter(String code, String name, String desc, long initialValue) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.value = initialValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? code : code.toLowerCase().trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? name : name.toLowerCase().trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public boolean isSystemParameter() {
        return systemParameter;
    }

    public void setSystemParameter(boolean systemParameter) {
        this.systemParameter = systemParameter;
    }

    public GameParameter clone() {

        try {
            return (GameParameter) super.clone();
        } catch (CloneNotSupportedException e) {
            logger.error("err msg : " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String toString() {
        String result = "";
        result += " paramName:" + name;
        result += " ,paramCode:" + code;
        result += " ,value    : " + value;

        return result;
    }

    public static void main(String[] args) {
        GameParameter g1 = new GameParameter();
        g1.setDesc("100");

        GameParameter g2 = g1.clone();
        System.out.println("g1.getDetailDesc() = " + g1.getDesc());
        System.out.println("g2.getDetailDesc() = " + g2.getDesc());
        System.out.println("g1 = " + g1.hashCode());
        System.out.println("g2 = " + g2.hashCode());
        g2.setDesc("200");
        System.out.println("g1.getDetailDesc() = " + g1.getDesc());
        System.out.println("g2.getDetailDesc() = " + g2.getDesc());

        String[] split = "a*b".split("\\*");
        logger.info("split = " + split.length);
    }
}
