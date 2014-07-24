package com.vas.engine.entity;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/16/13 1:58 AM
 */
public class IncomingMessage {
    private static Logger logger = Logger.getLogger(IncomingMessage.class);

    String sourceAddr;
    String destAddr;

    String messageContent;
    private List<String> msgItems = new ArrayList<String>();


    public IncomingMessage(String sourceAddr, String destAddr, String messageContent) {
        this.sourceAddr = sourceAddr;
        this.destAddr = destAddr;
        this.messageContent = messageContent;
    }

    public String getSourceAddr() {
        return sourceAddr;
    }

    public void setSourceAddr(String sourceAddr) {
        this.sourceAddr = sourceAddr;
    }

    public String getDestAddr() {
        return destAddr;
    }

    public void setDestAddr(String destAddr) {
        this.destAddr = destAddr;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public List<String> getMsgItems() {
        return msgItems;
    }

    public void setMsgItems(List<String> msgItems) {
        this.msgItems = msgItems;
    }
}
