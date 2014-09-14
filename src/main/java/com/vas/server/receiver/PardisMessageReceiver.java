package com.vas.server.receiver;

import com.vas.engine.entity.IncomingMessage;
import com.vas.server.service.GameEngineIF;
import org.apache.log4j.Logger;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/16/13 12:01 AM
 */
public class PardisMessageReceiver implements MessageReceiver {

    private static Logger logger = Logger.getLogger(PardisMessageReceiver.class);
    private GameEngineIF _gameEngineIF;

    @Override
    public void processMessage(String sender, String receiver, String message) {

        logger.info("sender = " + sender);
        logger.info("receiver = " + receiver);
        logger.info("message = " + message);

        try {
            _gameEngineIF.getIncomingMessageQueue().put(new IncomingMessage(sender, receiver, message));
        } catch (InterruptedException e) {
            logger.error("err msg : " + e.getMessage(), e);
        }
    }

    public void setGameEngine(GameEngineIF gameEngineIF) {
        _gameEngineIF = gameEngineIF;
    }

}
