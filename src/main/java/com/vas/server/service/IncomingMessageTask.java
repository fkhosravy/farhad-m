package com.vas.server.service;


import com.vas.engine.entity.IncomingMessage;

public class IncomingMessageTask implements Runnable {
    private IncomingMessage incomingMessage;

    public IncomingMessageTask(IncomingMessage incomingMessage, String mm) {
        this.incomingMessage = incomingMessage;
    }

    public void run() {


    }
}
