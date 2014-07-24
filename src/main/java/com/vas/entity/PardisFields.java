package com.vas.entity;

import org.apache.log4j.Logger;

public class PardisFields {
    private static Logger logger = Logger.getLogger(PardisFields.class);

    String usernameField;
    String passwordField;
    private String contentField;
    private String serviceField;
    private String receiverField;
    private String senderField;
    private String priceField;


    public String getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(String usernameField) {
        this.usernameField = usernameField;
    }

    public String getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(String passwordField) {
        this.passwordField = passwordField;
    }

    public String getContentField() {
        return contentField;
    }

    public void setContentField(String contentField) {
        this.contentField = contentField;
    }

    public String getServiceField() {
        return serviceField;
    }

    public void setServiceField(String serviceField) {
        this.serviceField = serviceField;
    }

    public String getReceiverField() {
        return receiverField;
    }

    public void setReceiverField(String receiverField) {
        this.receiverField = receiverField;
    }

    public String getSenderField() {
        return senderField;
    }

    public void setSenderField(String senderField) {
        this.senderField = senderField;
    }

    public void setPriceField(String priceField) {
        this.priceField = priceField;
    }

    public String getPriceField() {
        return priceField;
    }
}
