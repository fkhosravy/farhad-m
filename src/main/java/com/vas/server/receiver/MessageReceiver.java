package com.vas.server.receiver;

public interface MessageReceiver {

    void processMessage(String sender, String receiver, String message);
}
