package com.vas.engine.xml.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDefinition extends BaseGameDefinition {

    private String enDesc;
    private GameStage startStage;

    private WinCondition _winCondition;
    private LoosCondition _loosCondition;

    private List<GameStage> stages = new ArrayList<GameStage>();
    private List<Reminder> reminderList = new ArrayList<Reminder>();
    private Map<String, GameStage> stageListMap = new HashMap<String, GameStage>();

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

    public Map<String, GameStage> getStageListMap()
    {
        return stageListMap;
    }

    public void setStageListMap(Map<String, GameStage> stageListMap)
    {
        this.stageListMap = stageListMap;
    }

    public GameStage getStartStage() {
        if (startStage == null)
        {
            for (GameStage nextStage : stages) {
                if (nextStage.isStartStage())
                {
                    startStage = nextStage;
                    break;
                }
            }
        }

        return startStage;
    }

    public void initStageListMap(List<GameStage> gameStageList)
    {
        for (GameStage stage :  gameStageList)
        {
            stageListMap.put(stage.getId(), stage);
        }
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
