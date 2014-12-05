package com.vas.server.scheduler;

import com.vas.engine.xml.model.Reminder;
import com.vas.game.model.Game;
import com.vas.game.model.Player;
import com.vas.game.service.GameService;
import com.vas.game.service.PlayerService;
import com.vas.engine.xml.model.GameDefinition;
import com.vas.engine.xml.model.GameStage;
import com.vas.server.sender.MessageSender;
import com.vas.server.service.GameEngineManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

//public class ScheduleTask extends TimerTask {
public class ScheduleTask implements Runnable {
    private static Logger logger = Logger.getLogger(ScheduleTask.class);

    private final static String ACTION_REMINDER = "reminder";
    private final static String ACTION_DEACTIVATION = "deactivation";
    private final static String ACTION_INVITE = "invite";
    private final static String ACTION_NEXT_STAGE = "nextstage";
    private final static long ONCE_PER_DAY = 60 * 60 * 24;
    private final static long ONCE_PER_WEEK = 60 * 60 * 24 * 7;
    private final static long ONCE_PER_2WEEK = 60 * 60 * 24 * 14;
    private final static long ONCE_PER_MONTH = 60 * 60 * 24 * 30;
    private final static int REMINDER_NO = 4;

    int price;
//    Timer timer;
    long period;
    long delay;

    Game game;
    GameDefinition gameDefinition;
    private Reminder reminder;

    MessageSender _messageSender;

    PlayerService _playerService;

    GameService _gameService;

    public MessageSender getMessageSender() {
        return _messageSender;
    }

    public void setMessageSender(MessageSender messageSender) {
        this._messageSender = messageSender;
    }

    public PlayerService get_playerService() {
        return _playerService;
    }

    public void set_playerService(PlayerService _playerService) {
        this._playerService = _playerService;
    }

    public GameService get_gameService() {
        return _gameService;
    }

    public void set_gameService(GameService _gameService) {
        this._gameService = _gameService;
    }

    public MessageSender get_messageSender() {
        return _messageSender;
    }

    public void set_messageSender(MessageSender _messageSender) {
        this._messageSender = _messageSender;
    }

    public long getPeriod()
    {
        return period;
    }

    public void setPeriod(long period)
    {
        this.period = period;
    }

    public long getDelay()
    {
        return delay;
    }

    public void setDelay(long delay)
    {
        this.delay = delay;
    }

    //    public void initScheduleTask(Map<String, GameDefinition> gameListMap, List<Game> gameList)
    public void initScheduleTask(GameDefinition gameDefinition, Game game, Reminder reminder, PlayerService playerService, GameService gameService, MessageSender messageSender)
    {
        this.game = game;
        this.gameDefinition = gameDefinition;
        this.reminder = new Reminder(reminder.hour, reminder.message, reminder.period, reminder.action, reminder.header, reminder.price);
        this._playerService = playerService;
        this._gameService = gameService;
        this._messageSender = messageSender;

        if (reminder.getPeriod().compareToIgnoreCase("ONCE_PER_DAY") == 0)
            this.period = ONCE_PER_DAY;
        else if (reminder.getPeriod().compareToIgnoreCase("ONCE_PER_WEEK") == 0)
            this.period = ONCE_PER_WEEK;
        else if (reminder.getPeriod().compareToIgnoreCase("ONCE_PER_2WEEK") == 0)
            this.period = ONCE_PER_2WEEK;
        else if (reminder.getPeriod().compareToIgnoreCase("ONCE_PER_MONTH") == 0)
            this.period = ONCE_PER_MONTH;

        Calendar runTime = Calendar.getInstance();

        runTime.set(Calendar.HOUR_OF_DAY, reminder.getHour());
        runTime.set(Calendar.MINUTE, 0);
        runTime.set(Calendar.SECOND, 0);

        Date d1 = new Date();
        Date d2 = runTime.getTime();
        long diff = d2.getTime() - d1.getTime();

        if (diff > 0)
            this.delay = diff / 1000;
        else if (diff > -3600000)
            this.delay = 30;
        else
        {
            runTime.set(Calendar.HOUR_OF_DAY, 23);
            runTime.set(Calendar.MINUTE, 59);
            runTime.set(Calendar.SECOND, 59);

            d2 = runTime.getTime();
            diff = (d2.getTime() - d1.getTime()) / 1000;
            this.delay = diff + (reminder.getHour() * 60 * 60);
        }
//        if (reminder.getPeriod().compareToIgnoreCase("ONCE_PER_DAY") == 0)
//            timer.scheduleAtFixedRate(this, runTime.getTime(), ONCE_PER_DAY);
//        else if (reminder.getPeriod().compareToIgnoreCase("ONCE_PER_WEEK") == 0)
//            timer.scheduleAtFixedRate(this, runTime.getTime(), ONCE_PER_WEEK);
//        else if (reminder.getPeriod().compareToIgnoreCase("ONCE_PER_2WEEK") == 0)
//            timer.scheduleAtFixedRate(this, runTime.getTime(), ONCE_PER_2WEEK);
//        else if (reminder.getPeriod().compareToIgnoreCase("ONCE_PER_MONTH") == 0)
//            timer.scheduleAtFixedRate(this, runTime.getTime(), ONCE_PER_MONTH);
    }

    @Override
    public void run()
    {
        if (game != null && reminder != null)
        {
            Calendar chargeDate = Calendar.getInstance();
            chargeDate.set(Calendar.HOUR, 0);
            chargeDate.set(Calendar.MINUTE, 0);
            chargeDate.set(Calendar.SECOND, 0);
            chargeDate.set(Calendar.AM_PM, Calendar.AM);

//            long milliseconds = Calendar.getInstance().getTimeInMillis();
//            int hour = (int)TimeUnit.MILLISECONDS.toHours(milliseconds) % 24;

//            if (reminder.getHour() == hour)
//            {
            logger.error("====================== Schedule Task ================== ");
            List<Player> playerList = null;

            if (reminder.getAction().compareToIgnoreCase(ACTION_REMINDER) == 0)
            {
                playerList = _playerService.
                        findPlayerByGameIdAndLastChargeDateLessThanAndGameStateGreaterThan(game.getId(), chargeDate.getTime(), -1);

                if (playerList != null)
                    sendReminder(playerList);
            }
            else if (reminder.getAction().compareToIgnoreCase(ACTION_DEACTIVATION) == 0)
            {
                playerList = _playerService.findActivePlayerByGameId(game.getId());
                if (playerList != null)
                {
                    String message = reminder.getMessage();
                    if (reminder.getHeader() != null && !reminder.getHeader().isEmpty())
                        message = reminder.getHeader() + " " + message;

                    for (Player player : playerList)
                    {
                        _messageSender.sendMessage(player.getMobile(), message, game.getServiceID(), gameDefinition.getZeroChargePrice());
                        logger.info("Send DeActivation Message " + message + " To Receiver " + player.getMobile());
                    }
                }
            }
            else if (reminder.getAction().compareToIgnoreCase(ACTION_INVITE) == 0)
            {
                String message = reminder.getMessage();
                if (reminder.getHeader() != null && !reminder.getHeader().isEmpty())
                    message = reminder.getHeader() + " " + message;

                playerList = _playerService.findInActivePlayerByGameId(game.getId());
                for (Player player : playerList)
                {
                        _messageSender.sendMessage(player.getMobile(), message, game.getServiceID(), gameDefinition.getZeroChargePrice());
                    logger.info("Send Invite Message " + message + " To Receiver " + player.getMobile());
                }
            }
            else if (reminder.getAction().compareToIgnoreCase(ACTION_NEXT_STAGE) == 0)
            {
                playerList = _playerService.
                        findPlayerByGameIdAndLastChargeDateLessThanAndGameStateGreaterThan(game.getId(), chargeDate.getTime(), -1);

                if (playerList != null)
                    sendNextStageReminder(playerList);
            }
        }
    }

    private void sendReminder(List<Player> playerList)
    {
        String reminderMsg = reminder.getMessage();
        boolean reminderHasMsg = true;

        if (reminder.getMessage().isEmpty())
            reminderHasMsg = false;

        for (Player player : playerList)
        {
            String message = "";

            if (!reminderHasMsg)
            {
                if (player.getGameState() == GameEngineManager.GAME_END_STATE)
                {
                    GameStage gameStage = gameDefinition.getStartStage();
                    if (gameStage != null)
                        message = gameStage.getDesc();
                }
                else
                {
                    GameStage gameStage = findGameStage(gameDefinition, player.getLastStageId());

                    if (gameStage != null)
                    {
                        message = gameStage.getDesc();

                        if (reminder.getHeader().isEmpty())
                        {
                            if (gameStage.getHeader() != null)
                                message = gameStage.getHeader() + " " + message;
                        }
                        else
                            message = reminder.getHeader() + " " + message;

                        if (gameStage.getFooter() != null)
                            message = message + " " + gameStage.getFooter();
                    }
                }
            }
            else
            {
                message = reminder.getHeader() + " " + reminderMsg;
            }

            if (!message.isEmpty())
            {
                player.incChargeNo();
                player.setLastChargeDate(new Date());
                _playerService.updatePlayer(player);

                _messageSender.sendMessage(player.getMobile(), message, game.getServiceID(), price);
                logger.info("Send Reminder Message " + message + " To Receiver " + player.getMobile() + " With ServiceId " + game.getServiceID() + " With Price " + price);
            }
        }
    }

    private void sendNextStageReminder(List<Player> playerList)
    {
        String reminderMsg = reminder.getMessage();
        boolean reminderHasMsg = true;

        if (reminder.getMessage().isEmpty())
            reminderHasMsg = false;

        for (Player player : playerList)
        {
            String message = "";

            if (!reminderHasMsg)
            {
                if (player.getGameState() == GameEngineManager.GAME_END_STATE)
                {
                    GameStage gameStage = gameDefinition.getStartStage();
                    if (gameStage != null)
                        message = gameStage.getDesc();
                }
                else
                {
                    player.incReminderNo();
                    GameStage gameStage = findGameStage(gameDefinition, player.getLastStageId());

                    if (player.getReminderNo() >= REMINDER_NO)
                    {
                        player.setLastStageId(gameStage.getNextStageCode());
                        player.setReminderNo(1);

                        gameStage = findGameStage(gameDefinition, gameStage.getNextStageCode());
                    }

                    if (gameStage != null)
                    {
                        message = gameStage.getDesc();

                        if (reminder.getHeader().isEmpty())
                        {
                            if (gameStage.getHeader() != null)
                                message = gameStage.getHeader() + " " + message;
                        }
                        else
                            message = reminder.getHeader() + " " + message;

                        if (gameStage.getFooter() != null)
                            message = message + " " + gameStage.getFooter();
                    }
                }
            }
            else
            {
                message = reminder.getHeader() + " " + reminderMsg;
            }

            if (!message.isEmpty())
            {
                player.incChargeNo();
                player.setLastChargeDate(new Date());
            }

            _playerService.updatePlayer(player);

            if (!message.isEmpty())
            {
                _messageSender.sendMessage(player.getMobile(), message, game.getServiceID(), price);
                logger.info("Send Reminder Message " + message + " To Receiver " + player.getMobile() + " With ServiceId " + game.getServiceID() + " With Price " + price);
            }
        }
    }

    private GameStage findGameStage(GameDefinition gameDefinition, String stageId)
    {
        return gameDefinition.getStageListMap().get(stageId);
//        for (GameStage nextGameStage : gameDefinition.getStages())
//        {
//            if (nextGameStage.getId().equalsIgnoreCase(stageId)) {
//                return nextGameStage;
//            }
//        }
//
//        return null;
    }

    private int messagePrice(GameDefinition relatedGame, Player player, GameStage gameStage)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        if (player.getChargeNo() == 0)
            return relatedGame.getPricePerDay();
        else if (player.getLastChargeDate().before(calendar.getTime()))
            return relatedGame.getPricePerDay();
        else if (gameStage != null)
            return gameStage.getPrice();
        else
            return relatedGame.getZeroChargePrice();
    }
}
