package com.vas.engine.xml.model;


import java.util.ArrayList;
import java.util.List;

public class StageCondition {

    private String inputCode;
    private String targetStageId;
    private String desc;

    List<String> functionList = new ArrayList<String>();


    public StageCondition() {
    }

    public StageCondition(String inputCode, String targetStageId) {
        this.inputCode = inputCode;
        this.targetStageId = targetStageId;
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        if (inputCode != null)
            this.inputCode = inputCode.toLowerCase();
        else
            this.inputCode = inputCode;
    }

    public String getTargetStageId() {
        return targetStageId;
    }

    public void setTargetStageId(String targetStageId) {
        this.targetStageId = targetStageId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<String> functionList) {
        this.functionList = functionList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" inputCode:").append(inputCode).append(" targetStage:").append(targetStageId);

        for (String nextFunction : functionList) {
            sb.append("\n function:" + nextFunction);
        }
        return sb.toString();

    }
}