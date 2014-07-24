package com.vas.server.service;

import com.vas.entity.GameEventDescription;
import com.vas.entity.GameInfo;
import com.vas.entity.MessageParser;
import com.vas.engine.entity.IncomingMessage;
import com.vas.engine.xml.model.*;
import com.vas.server.sender.MessageSender;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class StrategicalGameEngine implements Runnable, GameEngineIF {
    private static Logger logger = Logger.getLogger(StrategicalGameEngine.class);

    BlockingQueue<IncomingMessage> _incomingMessageQueue = new ArrayBlockingQueue<IncomingMessage>(10000);

    ThreadPoolExecutor threadPool;

    GameStatisticManager _gameStatisticManager;
    CentralEventManager _eventManager;

    MessageParser _messageParser;
    MessageSender _messageSender;

    String gameStatusCode = "103";
    String gameListCode = "110";

    String gameAllCode = "موزیسین بعدی (دریافت سوال بعدی)، موزیسین راهنما (دریافت راهنمایی در مورد بازی جاری مشترک)، موزیسین امتیاز (دریافت اطلاعات امتیازی مشترک)، موزیسین جدول (جدول 3 بازیکن برتر از نظر امتیازی در کتگوری جاری مشترک) و موزیسین انصراف (انصراف از کتگوری موجود و بازگشت به منوی اصلی)";
    String gameStartCode = "روشن";
    String gameEndCode = "خاموش";
    String gameHelpCode = "کمک";
    String gameGuideCode = "راهنما";
    String gameNextCode = "بعدی";
    String gameScoreCode = "امتیاز";
    String gameTableCode = "جدول";
    String gameCancelCode = "انصراف";

    Map<String, GameInfo> gameListMap = new HashMap<String, GameInfo>();

    public void setGameList(List<GameInfo> gameList) {
        for (GameInfo nextGameInfo : gameList) {
            gameListMap.put(nextGameInfo.getGameCode(), nextGameInfo);
        }
        logger.info("gameList.size() = " + gameList.size());
    }

    public void setMessageSender(MessageSender messageSender) {
        _messageSender = messageSender;
    }

    public void setEventManager(CentralEventManager eventManager) {
        _eventManager = eventManager;
    }

    public void setGameStatusCode(String gameStatusCode) {
        this.gameStatusCode = gameStatusCode;
    }

    public void setGameListCode(String gameListCode) {
        this.gameListCode = gameListCode;
    }

    public void setGameStartCode(String gameStartCode) {
        this.gameStartCode = gameStartCode;
    }

    public void setGameEndCode(String gameEndCode) {
        this.gameEndCode = gameEndCode;
    }

    public void setGameStatisticManager(GameStatisticManager gameStatisticManager) {
        _gameStatisticManager = gameStatisticManager;
    }

    public void setMessageParser(MessageParser messageParser) {
        _messageParser = messageParser;
    }

    public String getGameHelpCode() {
        return gameHelpCode;
    }

    public void setGameHelpCode(String gameHelpCode) {
        this.gameHelpCode = gameHelpCode;
    }

    public StrategicalGameEngine() {

//        int threadPoolSize = Integer.parseInt(ConfigLoader.get("com.pardis.incoming_message_thread_pool_size"));
//        int maxThreadPoolSize = Integer.parseInt(ConfigLoader.get("com.pardis.incoming_message_thread_pool_max_siz"));
//
//        threadPoolExecutor = new ThreadPoolExecutor(threadPoolSize, maxThreadPoolSize, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        Thread thread = new Thread(this, "IncomingMessageProcessor thread");
        thread.start();
    }

    @Override
    public BlockingQueue<IncomingMessage> getIncomingMessageQueue() {
        return _incomingMessageQueue;
    }

    public void run()
    {
        try {
            while (true)
            {
                try {
                    IncomingMessage incomingMessage = _incomingMessageQueue.take();
                    logger.info("Toked message from queue >> " + incomingMessage.getMessageContent());
                    incomingMessage.setMsgItems(_messageParser.parse(incomingMessage));

                    String firstToken = incomingMessage.getMsgItems().get(0);
                    logger.info("firstToken = " + firstToken);

                    if (incomingMessage.getMsgItems().size() < 1 || firstToken.equals("") || firstToken.equals(gameHelpCode))
                    {
                        sendHelp(incomingMessage);
                        continue;
                    }
                    else if (firstToken.equals(gameListCode))
                    {
                        sendGameList(incomingMessage);
                        continue;
                    }
                    else
                    {
                        GameInfo foundGame = gameListMap.get(firstToken);
                        if (foundGame == null)
                        {
                            logger.info("foundGame = " + foundGame);
                            sendError(incomingMessage);
                            continue;
                        }
                        else
                        {
                            process(incomingMessage, foundGame);
                        }
                    }

                    logger.info("end process");
//                    logger.debug("Thread pool size:" + threadPoolExecutor.getActiveCount());
//                    threadPoolExecutor.execute(new IncomingMessageTask(incomingMessage, ""));
                } catch (InterruptedException e) {
                    logger.error("err msg : " + e.getMessage(), e);
                }
            }
        } catch (Throwable e) {
            logger.error("err msg : " + e.getMessage(), e);
        }
    }

    /**
     * send game list in engine to player
     */
    private void sendGameList(IncomingMessage incomingMessage) {
        StringBuffer sb = new StringBuffer();
        sb.append("لیست بازی های موجود").append("\n").append(" ");

        for (GameInfo nextGameInfo : gameListMap.values()) {
            sb.append("بازی ").append(nextGameInfo.getDesc())
                    .append(" با کد ").append(nextGameInfo.getGameCode());

            sb.append("\n ");
        }

        sb
            .append("برای شروع بازی ابتدا کد بازی مورد نظر سپس ")
//            .append(_messageParser.getSeparator().replace("\\", ""))
            .append(_messageParser.getSeparatorName())
            .append(" و بعد از آن ").append(gameStartCode)
            .append(" را وارد کنید.");

        _messageSender.sendMessage(incomingMessage.getSourceAddr(), sb.toString(), null);
        logger.info("Send Message " + sb.toString() + " To Reciever " + incomingMessage.getSourceAddr());
    }

    private void process(IncomingMessage incomingMessage, GameInfo relatedGame)
    {
        logger.info("entered into process");

        String firstToken = incomingMessage.getMsgItems().get(0);
        String requestCondition = incomingMessage.getMsgItems().get(1);

        if (requestCondition != null && requestCondition.equals(gameStartCode))
        {
            registerPlayer(incomingMessage, relatedGame);
            _eventManager.fireNewEventFor(incomingMessage, relatedGame);
        }
        else if (requestCondition != null && firstToken.equals(gameEndCode))
        {
            unRegisterPlayer(incomingMessage, relatedGame);
        }
        else if (requestCondition != null && firstToken.equals(gameStatusCode))
        {
            sendCurrentStatus(incomingMessage, relatedGame);
        }
        else
        {
            boolean matched = false;
            GameDefinition game = new GameDefinition();

            //get current state of player
            PlayerState currentPlayerState = _gameStatisticManager.getPlayerState(incomingMessage.getSourceAddr(), firstToken);

            //find related defined stage in system to current state of player
            // and if find process it otherwise send error to player
            for (GameStage nextGameStage : game.getStages()) {
                if (nextGameStage.getId().equals(currentPlayerState.getCurrentStateId())) {
                    matched = true;
                    processStage(nextGameStage, currentPlayerState, requestCondition, incomingMessage);
                    break;
                }
            }

            if (!matched)
                sendError(incomingMessage); //Todo Change better error
        }
    }

    private void processStage(GameStage currentPlayerStage, PlayerState currentPlayerState, String inputValue,
                              IncomingMessage incomingMessage) {
        boolean inputIsValid = false;
        for (StageCondition nextStageCondition : currentPlayerStage.getConditionList())
        {
            if (nextStageCondition.getInputCode().equals(inputValue))
            {
                inputIsValid = true;

                //apply stage condition
                nextStageCondition.getFunctionList();

                //check win and loos condition

                //change state
                currentPlayerState.setCurrentStateId(nextStageCondition.getTargetStageId());

                if (currentPlayerStage.isFinalStage())
                {
                    //end the game with unregistering player and send goodBye
                    _gameStatisticManager.unRegisterUserWithState(currentPlayerState);

                    _messageSender.sendMessage(incomingMessage.getSourceAddr(), currentPlayerStage.getGoodByMessage(), null);
                    logger.info("Send Message " + currentPlayerStage.getGoodByMessage() + " To Reciever " + incomingMessage.getSourceAddr());
                } else {
                    //send stage description
                    _messageSender.sendMessage(incomingMessage.getSourceAddr(), currentPlayerStage.getDesc(), null);
                    logger.info("Send Message " + currentPlayerStage.getDesc() + " To Reciever " + incomingMessage.getSourceAddr());
                }

                break;
            }
        }

        if (!inputIsValid)
            sendError(incomingMessage); //Todo Change better error
    }

    private void strategical_process(IncomingMessage incomingMessage, GameInfo relatedGame) {
        logger.info("entered into process");

        String firstToken = incomingMessage.getMsgItems().get(0);
        String requestedEventCode = incomingMessage.getMsgItems().get(1);

        if (requestedEventCode != null && requestedEventCode.equals(gameStartCode)) {

            registerPlayer(incomingMessage, relatedGame);
            _eventManager.fireNewEventFor(incomingMessage, relatedGame);

        } else if (requestedEventCode != null && firstToken.equals(gameEndCode)) {

            unRegisterPlayer(incomingMessage, relatedGame);

        } else if (requestedEventCode != null && firstToken.equals(gameStartCode)) {

            sendCurrentStatus(incomingMessage, relatedGame);

        } else {

            boolean matched = false;
            //check game user eventCode
            for (GameEventDescription nextGameEventDescription : relatedGame.getUserEventList()) {
                if (nextGameEventDescription.getCode().equals(requestedEventCode)) {

                    matched = true;
                    _eventManager.addRequestedEvent(incomingMessage, relatedGame);
                    break;
                }
            }

            if (!matched)
                sendError(incomingMessage);
        }
    }

    private void sendCurrentStatus(IncomingMessage incomingMessage, GameInfo relatedGame) {
        logger.error("sendCurrentStatus not implemented yet...");
    }

    private void unRegisterPlayer(IncomingMessage incomingMessage, GameInfo relatedGame) {
        logger.error("unRegister not implemented yet...");
    }

    private void registerPlayer(IncomingMessage incomingMessage, GameInfo relatedGame) {
        logger.info("entered into registerPlayer");

        List<GameParameter> parameterList = relatedGame.getParameterList();
        List<GameParameter> playerParameterList = new ArrayList<GameParameter>();

        for (GameParameter nextGameParameter : parameterList) {
            playerParameterList.add(nextGameParameter.clone());
        }

        PlayerState playerState = new PlayerState(incomingMessage.getSourceAddr(), relatedGame.getGameCode(), "init");
        _gameStatisticManager.registerUserWithState(playerState);

        //todo
        String message = relatedGame.getWelcomeMessage() + "\n" + relatedGame.getDesc();
        _messageSender.sendMessage(incomingMessage.getSourceAddr(), message, relatedGame.getServiceID());

        logger.warn("welcome message send to " + incomingMessage.getSourceAddr());
    }

    private void sendHelp(IncomingMessage incomingMessage) {
        StringBuffer sb = new StringBuffer();
        sb.append("کدهای این بازی عبارتند از: شروع بازی").append(" ").append(gameStartCode).append(" ");
        sb.append(gameAllCode);
        _messageSender.sendMessage(incomingMessage.getSourceAddr(), sb.toString(), null);
        logger.info("Send Message " + sb.toString() + " To Reciever " + incomingMessage.getSourceAddr());
    }

    private void sendError(IncomingMessage incomingMessage) {
        StringBuffer sb = new StringBuffer();
        sb.append("کد وارد شده معتبر نمی باشد").append("\n").append(" ");
        sb.append("برای دریافت کد بازی ها ").append(gameListCode).append(" ");
        sb.append(" ،برای دریافت وضعیت جاری کد ").append(gameStatusCode).append(" ");
        sb.append(" و برای دریافت راهنمایی کد ").append(gameHelpCode).append(" ");
        sb.append(" را ارسال کنید.");

        _messageSender.sendMessage(incomingMessage.getSourceAddr(), sb.toString(), null);
        logger.info("Send Message " + sb.toString() + " To Reciever " + incomingMessage.getSourceAddr());
    }


}