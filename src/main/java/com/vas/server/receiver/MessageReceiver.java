package com.vas.server.receiver;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/16/13 1:17 AM
 */
public interface MessageReceiver {

    void processMessage(String sender, String receiver, String message);
}
