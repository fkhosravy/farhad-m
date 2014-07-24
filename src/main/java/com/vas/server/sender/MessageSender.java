package com.vas.server.sender;

public interface MessageSender {

    void sendMessage(String receiver, String message,String serviceID);

    void sendMessage(String receiver, String message, String serviceID,int price);

    void sendProfiler(String serviceID, String phone, String status);
}
