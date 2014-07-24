package com.vas.server.service;

import com.vas.engine.entity.IncomingMessage;

import java.util.concurrent.BlockingQueue;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/26/13 1:17 AM
 */
public interface GameEngineIF {

    BlockingQueue<IncomingMessage> getIncomingMessageQueue();
}
