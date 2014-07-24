package com.vas.server.service;

import com.vas.engine.entity.IncomingMessage;

import java.util.concurrent.BlockingQueue;

public interface GameEngineIF {

    BlockingQueue<IncomingMessage> getIncomingMessageQueue();
}
