package com.vas.entity;

import org.apache.log4j.Logger;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/18/13 1:19 AM
 */


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
