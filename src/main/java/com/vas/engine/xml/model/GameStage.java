package com.vas.engine.xml.model;


import java.util.ArrayList;
import java.util.List;

public class GameStage {

    private String id;
    private String name;
    private String desc;
    private String header;
    private String footer;
    private boolean startStage = false;

    private String welcomeMessage;
    private boolean finalStage = false;

    private String goodByMessage = "";
    private int price = 0;

    private int score = 0;
    private boolean question = false;

    private String nextStageCode;

    List<StageCondition> conditionList = new ArrayList<StageCondition>();

    public GameStage() {
    }

    public GameStage(String id, String name, String desc, String header, String footer, int score, boolean question, String nextStageCode) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.header = header;
        this.footer = footer;
        this.score = score;
        this.question = question;
        this.nextStageCode = nextStageCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFinalStage() {
        return finalStage;
    }

    public void setFinalStage(boolean finalStage) {
        this.finalStage = finalStage;
    }

    public boolean getFinalStage() {
        return finalStage;
    }

    public List<StageCondition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<StageCondition> conditionList) {
        this.conditionList = conditionList;
    }

    public String getGoodByMessage() {
        return goodByMessage;
    }

    public void setGoodByMessage(String goodByMessage) {
        this.goodByMessage = goodByMessage;
    }

    public boolean isStartStage() {
        return startStage;
    }

    public void setStartStage(boolean startStage) {
        this.startStage = startStage;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean getQuestion() {
        return question;
    }

    public void setQuestion(boolean question) {
        this.question = question;
    }

    public boolean isQuestion() {
        return question;
    }

    public String getNextStageCode() {
        return nextStageCode;
    }

    public void setNextStageCode(String nextStageCode) {
        this.nextStageCode = nextStageCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append(" id: ").append(id)
                .append(" name: ").append(name)
                .append(" price: ").append(price)
                .append(" score: ").append(score)
                .append(" header: ").append(header)
                .append(" footer: ").append(footer)
                .append(" startStage: ").append(startStage)
                .append(" welcomeMessage: ").append(welcomeMessage)
                .append(" finalStage: ").append(finalStage)
                .append(" goodByMessage: ").append(goodByMessage)
                .append(" question: ").append(question)
                .append(" nextStageCode: ").append(nextStageCode);
        for (StageCondition nextStageCondition : conditionList) {
            sb.append("\n").append(nextStageCondition);
        }

        return sb.toString();
    }
}