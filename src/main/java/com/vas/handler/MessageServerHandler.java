package com.vas.handler;

import com.vas.engine.entity.IncomingMessage;
import com.vas.server.receiver.MessageReceiver;
import com.vas.server.sender.MessageSender;
import com.vas.server.service.GameEngineIF;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessageServerHandler extends IoHandlerAdapter implements MessageReceiver, MessageSender {

    private static Logger logger = Logger.getLogger(MessageServerHandler.class);

    private GameEngineIF _gameEngineIF;

    Map<String, IoSession> _sessionMap = new HashMap<String, IoSession>();

    public void setGameEngine(GameEngineIF gameEngineIF) {
        _gameEngineIF = gameEngineIF;
    }

    @Override
    public void messageSent(final IoSession session, final Object message) throws Exception {
        //session.close();
    }

    @Override
    public void sessionOpened(final IoSession session) throws Exception {
        sendMessage(session, "Welcome to game engine 1.0.0" + "\n You connected @ " + new Date());
        //sendMessage(session, "you connected @ " + new Date());  \
        System.out.println(session + " was opened...");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        String playerNumber = (String) session.getAttribute("playerNumber");
        if (playerNumber != null)
            _sessionMap.remove(playerNumber);

        System.out.println(session + " was closed...");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        String userId = getUserId(session);

        String receivedSMS = message.toString();
        logger.info("receivedSMS = " + receivedSMS);

        if (receivedSMS.equals("qq")) {
            logger.info("good by...");
            session.write("good by..." + "\n");
            session.close(true);
        }

        if (receivedSMS.toLowerCase().startsWith("start")) {
            String[] partItems = receivedSMS.split(" ");
            String senderMobileNo = partItems[1];
            session.setAttribute("playerNumber", senderMobileNo);
            _sessionMap.put(senderMobileNo, session);

//            String messageWithoutAddress = "";
//            for (int i = 1; i < partItems.length; i++) {
//                messageWithoutAddress = messageWithoutAddress + partItems[i] + " ";
//            }
//            processMessage(senderMobileNo, "engine_???", messageWithoutAddress);
            return;
        }

        String playerNumber = (String) session.getAttribute("playerNumber");
        processMessage(playerNumber, "engine_???", message.toString());

//        if (receivedSMS.toLowerCase().startsWith("start")) {
//            if (userId != null) {
//                sendMessage(session, "invalid command");
//                return;
//            }
//
//            String[] startItem = receivedSMS.split(" ");
//            if (startItem.length < 3)
//                printHelp(session);
//            else {
//                registerUserWithState(startItem);
//            }
//
//        } else if (userId == null) {
//            printHelp(session);
//            return;
//        } else
//            session.write(new Date() + "\n");
    }

    private void registerUser(String[] startItem) {
        for (String nextS : startItem) {
            System.out.println(nextS);
        }
    }

    private String getUserId(IoSession session) {
        String userId = (String) session.getAttribute("userId");
        return userId;
    }

    private void sendMessage(IoSession session, String message) {
        session.write(message);
    }

    private void printHelp(IoSession session) {
        StringBuilder sb = new StringBuilder();
        sb.append("Please use following format for start game...").append("\n");
        sb.append("start gameId mobileNo").append("\n");
        sb.append("for example:").append("\n");
        sb.append("start 120 09124048587").append("\n");
        session.write(sb.toString());
    }

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

    @Override
    public void sendMessage(String receiver, String message,String serviceID) {
        sendMessage(receiver, message,null, 0);
    }

    @Override
    public void sendMessage(String receiver, String message,String serviceID, int price) {
        IoSession ioSession = _sessionMap.get(receiver);
        ioSession.write(message);
        logger.info("send message is => " + message + " with price [" + price + "]");
    }
}