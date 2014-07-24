package com.vas.entity;

import org.apache.log4j.Logger;

public class SystemGameEventDesc extends GameEventDescription {

    private static Logger logger = Logger.getLogger(SystemGameEventDesc.class);

    int occurrencePercent;
    int maxOccurrence = 1;

    public int getOccurrencePercent() {
        return occurrencePercent;
    }

    public void setOccurrencePercent(int occurrencePercent) {
        this.occurrencePercent = occurrencePercent;
    }

    public int getMaxOccurrence() {
        return maxOccurrence;
    }

    public void setMaxOccurrence(int maxOccurrence) {
        this.maxOccurrence = maxOccurrence;
    }

    public String getEventInfoForPlayer() {
        return getDetailDesc();
    }
}
