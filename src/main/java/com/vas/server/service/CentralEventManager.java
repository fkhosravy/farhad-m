package com.vas.server.service;

import com.vas.entity.GameEvent;
import com.vas.entity.GameEventDescription;
import com.vas.entity.GameInfo;
import com.vas.entity.SystemGameEventDesc;
import com.vas.engine.entity.IncomingMessage;
import com.vas.server.sender.MessageSender;
import com.vas.util.RandomGenerator;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/18/13 1:20 AM
 */
public class CentralEventManager implements Runnable {
    private static Logger logger = Logger.getLogger(CentralEventManager.class);

    MessageSender _messageSender;
    BlockingQueue<IncomingMessage> _incomingMessageQueue = new ArrayBlockingQueue<IncomingMessage>(100000);
//    TreeSet

    PriorityQueue<GameEvent> queue;
    Map<String, PriorityQueue<GameEvent>> eventQueueMap = new HashMap<String, PriorityQueue<GameEvent>>();

    public void setMessageSender(MessageSender messageSender) {
        _messageSender = messageSender;
    }

    public CentralEventManager() {
        new PriorityQueue<GameEvent>(5, new GameEventComparator());
    }

    public void fireNewEventFor(IncomingMessage incomingMessage, GameInfo relatedGame) {

        logger.info("go to create first event for " + incomingMessage.getSourceAddr());

        String gameId = incomingMessage.getMsgItems().get(0);
        String sourceAddr = incomingMessage.getSourceAddr();
        String playerGameId = sourceAddr + "-*-" + gameId;
        eventQueueMap.put(playerGameId, new PriorityQueue<GameEvent>(5, new GameEventComparator()));

        List<GameEventDescription> systemEventList = relatedGame.getSystemEventList();
        RandomGenerator randomGenerator = new RandomGenerator();
        int randomSystemIndex = randomGenerator.getRandomTO(systemEventList.size());
        SystemGameEventDesc randomSystemEvent = (SystemGameEventDesc) relatedGame.getSystemEventList().get(randomSystemIndex);

        if (randomGenerator.isPercentOccurred(randomSystemEvent.getOccurrencePercent())) {
            randomSystemEvent.getStartDelay();
            int randomOccurrenceMinute = randomGenerator.getRandomTO(randomSystemEvent.getLifeTime());
            int eventStartTime = randomSystemEvent.getStartDelay() + randomOccurrenceMinute;
            int eventLifeTime = randomSystemEvent.getLifeTime() - randomOccurrenceMinute;

            logger.info("randomSystemEvent with Id " + randomSystemEvent.getCode() + " was put in queue for firing...");

            eventQueueMap.get(playerGameId).offer(new GameEvent(eventLifeTime, eventStartTime, true));

        } else {

            logger.info("next system event has not enough percent for "
                    + randomSystemEvent.getCode() + ":" + randomSystemEvent.getOccurrencePercent() + "%");
        }

        _messageSender.sendMessage(incomingMessage.getSourceAddr(), randomSystemEvent.getEventInfoForPlayer(), relatedGame.getServiceID());

    }

    public void addRequestedEvent(IncomingMessage incomingMessage, GameInfo relatedGame) {
        logger.info("go to add event for " + incomingMessage.getSourceAddr());
    }

    @Override
    public void run()
    {
        while (true)
        {
            for (String nextPlayer : eventQueueMap.keySet()) {
                PriorityQueue<GameEvent> playerGameEvents = eventQueueMap.get(nextPlayer);
                //process playerGameEvents
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("err msg : " + e.getMessage(), e);
            }
        }
    }
}

class GameEventComparator implements Comparator<GameEvent> {

    @Override
    public int compare(GameEvent g1, GameEvent g2)
    {
        if (g1.isSystemEvent() != g2.isSystemEvent())
        {
            if (g2.isSystemEvent())
                return -1;
            else
                return 1;
        }
        else
        {
            if (g1.getFireTime() < g2.getFireTime())
                return -1;
            else if (g1.getFireTime() > g2.getFireTime())
                return 1;
            else
                return 0;
        }
    }

//    public static void main(String[] args) {
//
//        PriorityQueue<GameEvent> queue = new PriorityQueue<GameEvent>(5, new GameEventComparator());
//
//        queue.add(new GameEvent(10, 0, true));
//        queue.add(new GameEvent(10, 5, true));
//        queue.add(new GameEvent(10, 1));
//        queue.add(new GameEvent(10, 0));
//
//        while (queue.size() > 0) {
//            GameEvent remove = queue.remove();
//            System.out.println("remove = " + remove);
//        }
//    }
}