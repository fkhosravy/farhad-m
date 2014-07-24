package com.vas.server.sender;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/16/13 1:17 AM
 */
public interface MessageSender {

    void sendMessage(String receiver, String message,String serviceID);

    void sendMessage(String receiver, String message, String serviceID,int price);

}
