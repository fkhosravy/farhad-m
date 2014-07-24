package com.vas.engine.xml.model;


import java.util.ArrayList;
import java.util.List;

public class GameDefinition extends BaseGameDefinition {

    private String enDesc;

    private WinCondition _winCondition;
    private LoosCondition _loosCondition;

    private List<GameStage> stages = new ArrayList<GameStage>();
    private List<Reminder> reminderList = new ArrayList<Reminder>();

    // -- used by the data-binding framework
    public GameDefinition() {
    }

    public String getEnDesc() {
        return enDesc;
    }

    public void setEnDesc(String enDesc) {
        this.enDesc = enDesc;
    }

    public List<GameStage> getStages() {
        return stages;
    }

    public void setStages(List<GameStage> stages) {
        this.stages = stages;
    }

    public List<Reminder> getReminderList() {
        return reminderList;
    }

    public void setReminderList(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    public WinCondition getWinCondition() {
        return _winCondition;
    }

    public void setWinCondition(WinCondition winCondition) {
        _winCondition = winCondition;
    }

    public LoosCondition getLoosCondition() {
        return _loosCondition;
    }

    public void setLoosCondition(LoosCondition loosCondition) {
        _loosCondition = loosCondition;
    }

    public GameStage getStartStage() {
        for (GameStage nextStage : stages) {
            if (nextStage.isStartStage())
                return nextStage;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" name:").append(getName())
                .append(" code:").append(getGameCode())
                .append(" desc:").append(getDesc());

        return sb.toString();
    }
}
