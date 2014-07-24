package com.vas.server.service;

import com.vas.entity.MessageParser;
import com.vas.game.model.Game;
import com.vas.game.model.*;
import com.vas.game.model.PlayerBlackList;
import com.vas.game.service.GameService;
import com.vas.game.service.PlayerBlackListService;
import com.vas.game.service.PlayerService;
import com.vas.game.service.PlayerStateLogService;
import com.vas.engine.entity.IncomingMessage;
import com.vas.engine.xml.XMLConverter;
import com.vas.engine.xml.model.*;
import com.vas.engine.xml.model.PlayerState;
import com.vas.server.scheduler.ScheduleTask;
import com.vas.server.sender.MessageSender;
import com.vas.util.ConfigLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/16/13 1:18 AM
 */

public class GameEngineManager implements Runnable, GameEngineIF
{

    private static Logger logger = Logger.getLogger(GameEngineManager.class);

    public static final int GAME_END_STATE = 100;

    public static final int UN_STARTED_GAME_ERROR = 1;

    public static final int UNDEFINED_GAME = 2;

    ConfigLoader configLoader;

    ResourceBundle resourceBundle;

    BlockingQueue<IncomingMessage> _incomingMessageQueue = new ArrayBlockingQueue<IncomingMessage>(10000);

    GameStatisticManager _gameStatisticManager;

    CentralEventManager _eventManager;

    MessageParser _messageParser;

    MessageSender _messageSender;

    ScheduleTaskManager scheduleTaskManager;

    String gameStatusCode = "103";

    String gameListCode = "100";

    String gameStartCode = "روشن";

    String gameEndCode = "خاموش";

    String gameHelpCode = "کمك";

    String gameTableCode = "جدول";

    String gameScoreCode = "امتياز";

    String gameNextCode = "بعدي";

    String gameCancelCode = "انصراف";

    String gameServiceOff = "serviceOff";

    String gameXmlFilePath = "";

    XMLConverter converter;

    // contain game Code ----> Game Definition
    Map<String, GameDefinition> gameListMap = new HashMap<String, GameDefinition>();

    List<String> gameSeriesList = new ArrayList<String>();

    boolean useEngineError = false;

    boolean useEngineHelp = false;

    String engineErrorMessage = "";

    String engineHelpMessage = "";

    @Qualifier("playerServiceImpl")
    @Autowired
    PlayerService _playerService;

    @Autowired
    GameService _gameService;

    @Autowired
    PlayerStateLogService _playerStateLogService;

    @Autowired
    PlayerBlackListService _playerBlackListService;

    public void setGameList(List<GameDefinition> gameList)
    {
        for (GameDefinition nextGameDefinition : gameList)
        {
            gameListMap.put(nextGameDefinition.getGameCode().toLowerCase().trim(), nextGameDefinition);
            gameListMap.put(nextGameDefinition.getGameCode().toUpperCase().trim(), nextGameDefinition);
        }
        logger.info("gameList.size() = " + gameList.size());
    }

    public String getGameXmlFilePath()
    {
        return gameXmlFilePath;
    }

    public void setGameXmlFilePath(String gameXmlFilePath)
    {
        this.gameXmlFilePath = gameXmlFilePath;
    }

    public void setMessageSender(MessageSender messageSender)
    {
        _messageSender = messageSender;
    }

    public void setEventManager(CentralEventManager eventManager)
    {
        _eventManager = eventManager;
    }

    public void setGameStatusCode(String gameStatusCode)
    {
        this.gameStatusCode = gameStatusCode;
    }

    public void setGameListCode(String gameListCode)
    {
        this.gameListCode = gameListCode;
    }

    public void setGameStartCode(String gameStartCode)
    {
        this.gameStartCode = gameStartCode;
    }

    public void setGameEndCode(String gameEndCode)
    {
        this.gameEndCode = gameEndCode;
    }

    public String getGameEndCode()
    {
        return gameEndCode;
    }

    public void setGameStatisticManager(GameStatisticManager gameStatisticManager)
    {
        _gameStatisticManager = gameStatisticManager;
    }

    public void setMessageParser(MessageParser messageParser)
    {
        _messageParser = messageParser;
    }

    public String getGameHelpCode()
    {
        return gameHelpCode;
    }

    public String getGameServiceOff()
    {
        return gameServiceOff;
    }

    public void setGameServiceOff(String gameServiceOff)
    {
        this.gameServiceOff = gameServiceOff;
    }

    public XMLConverter getConverter()
    {
        return converter;
    }

    public void setConverter(XMLConverter converter)
    {
        this.converter = converter;
    }

    public void setGameHelpCode(String gameHelpCode)
    {
        this.gameHelpCode = gameHelpCode;
    }

    public boolean isUseEngineError()
    {
        return useEngineError;
    }

    public void setUseEngineError(boolean useEngineError)
    {
        this.useEngineError = useEngineError;
    }

    public boolean isUseEngineHelp()
    {
        return useEngineHelp;
    }

    public void setUseEngineHelp(boolean useEngineHelp)
    {
        this.useEngineHelp = useEngineHelp;
    }

    public String getEngineErrorMessage()
    {
        return engineErrorMessage;
    }

    public void setEngineErrorMessage(String engineErrorMessage)
    {
        this.engineErrorMessage = engineErrorMessage;
    }

    public String getEngineHelpMessage()
    {
        return engineHelpMessage;
    }

    public void setEngineHelpMessage(String engineHelpMessage)
    {
        this.engineHelpMessage = engineHelpMessage;
    }

    public String getGameTableCode()
    {
        return gameTableCode;
    }

    public void setGameTableCode(String gameTableCode)
    {
        this.gameTableCode = gameTableCode;
    }

    public String getGameScoreCode()
    {
        return gameScoreCode;
    }

    public void setGameScoreCode(String gameScoreCode)
    {
        this.gameScoreCode = gameScoreCode;
    }

    public String getGameNextCode()
    {
        return gameNextCode;
    }

    public void setGameNextCode(String gameNextCode)
    {
        this.gameNextCode = gameNextCode;
    }

    public String getGameCancelCode()
    {
        return gameCancelCode;
    }

    public void setGameCancelCode(String gameCancelCode)
    {
        this.gameCancelCode = gameCancelCode;
    }

    public Map<String, GameDefinition> getGameListMap()
    {
        return gameListMap;
    }

    public GameEngineManager()
    {
//        configLoader = new ConfigLoader();
        ConfigLoader.loadConfig();
//        readConfig(this);
        Thread thread = new Thread(this, "IncomingMessageProcessor thread");
        thread.start();
    }

    @Override
    public BlockingQueue<IncomingMessage> getIncomingMessageQueue()
    {
        return _incomingMessageQueue;
    }

    public static void readConfig(GameEngineManager gameEngineManager)
    {
        gameEngineManager.resourceBundle = ResourceBundle.getBundle("application-config", new Locale("fa"));
        Enumeration<String> keys = gameEngineManager.resourceBundle.getKeys();
        while (keys.hasMoreElements())
        {
            String key = keys.nextElement();
//            if (key.compareTo("cancel.message") == 0)
//                cancelMessage = gameEngineManager.resourceBundle.getString(key);
        }
    }

    public void run()
    {
        while (true)
        {
            try
            {
                IncomingMessage incomingMessage = _incomingMessageQueue.take();
                logger.info("get message from queue: " + incomingMessage.getMessageContent());
                incomingMessage.setMsgItems(_messageParser.parse(incomingMessage));

                if (incomingMessage.getMsgItems().size() < 1)
                {
                    sendHelp(incomingMessage, null);
                    continue;
                }
                else    // player send something
                {
                    String firstToken = (incomingMessage.getMsgItems().size() > 0) ? incomingMessage.getMsgItems().get(0) : "";
                    String secondToken = (incomingMessage.getMsgItems().size() > 1) ? incomingMessage.getMsgItems().get(1) : "";
                    logger.info("firstToken = " + firstToken);

                    String playerMobileNo = incomingMessage.getSourceAddr();

                    if (existInBlackList(playerMobileNo, firstToken))
                        continue;
                    else
                    {
                        try
                        {
                            int code = Integer.parseInt(firstToken);
                            for (GameDefinition gameDefinition : gameListMap.values())
                            {
                                if (code >= gameDefinition.getStartRange() && code <= gameDefinition.getEndRange())
                                {
                                    secondToken = firstToken;
                                    firstToken = gameDefinition.getCode().toLowerCase();

                                    List<String> items = new ArrayList<String>();
                                    items.add(firstToken);
                                    items.add(secondToken);
                                    incomingMessage.getMsgItems().clear();
                                    incomingMessage.setMsgItems(items);
                                    break;
                                }
                            }
                        }
                        catch (NumberFormatException exp)
                        {}

                        //find player in player database
                        Player player = _playerService.findLastActivePlayByMobileAndGameSeries(playerMobileNo, firstToken);

                        if (firstToken.equals(gameHelpCode) || secondToken.equals(gameHelpCode))
                        {
                            sendHelp(incomingMessage, player);
                            continue;
                        }
                        else if (firstToken.equals(gameListCode) || secondToken.equals(gameListCode))
                        {
                            sendHelp(incomingMessage, player);
                            continue;
                        }
                        else if (firstToken.equals(gameServiceOff) || firstToken.equals(gameServiceOff.toLowerCase()))
                        {
                            serviceOffRequest(playerMobileNo);
                            continue;
                        }
                        else if (!gameSeriesList.contains(firstToken))
                        {
                            //if received message at least has one part and first part is invalid code
                            logger.warn("TODO must send error that express first token is invalid...");

                            String correctCode = "";
                            if (!gameListMap.keySet().isEmpty())
                            {
                                correctCode = gameListMap.keySet().iterator().next();
                                if (correctCode != null)
                                    correctCode = correctCode.toUpperCase();
                            }
                            else
                                logger.fatal("Error : Game List Map is Empty...");

                            sendInvalidInput(incomingMessage, correctCode);
                            savePlayerStateLog(incomingMessage, player);
                        }
                        else
                        {
                            //if received message has one part and it is game series code
                            if (secondToken.isEmpty())
                            {
                                List<Game> gameListRelatedToSeries = _gameService.findGameBySeries(firstToken);

                                if (gameListRelatedToSeries.isEmpty())
                                {
                                    //send error
                                    logger.fatal("Does not exist any game for requested series with token " + firstToken + "] !");
                                    sendDoesNotAnyGame(incomingMessage);
                                    savePlayerStateLog(incomingMessage, player);
                                }
                                else
                                {
                                    //normal registration
                                    Game firstGameInSeries = gameListRelatedToSeries.get(0);
                                    GameDefinition gameDefinition = gameListMap.get(firstGameInSeries.getPrefix());
                                    if (gameDefinition != null)
                                    {
                                        if (player == null)
                                        {
                                            sendHelp(incomingMessage, null);
//                                            registerCustomer(incomingMessage, gameDefinition, firstGameInSeries.getId(), true);
                                        }
                                        else
                                        {
                                            sendHelp(incomingMessage, player);
//                                            if (player.getGameState() == _playerService.GAME_OFF_STATE)
//                                                registerCustomer(incomingMessage, gameDefinition, firstGameInSeries.getId(), true);
//                                            else
//                                            {
//                                                // check player already has active play if true send current state for it
//                                                logger.info("Already has registered play so send current state for player");
//                                                Game foundGame = _gameService.findGame(player.getGameId());
//
//                                                if (foundGame != null)
//                                                    sendStartStatus(incomingMessage, player, foundGame);
//                                            }
                                        }
                                    }
                                    else
                                    {
                                        logger.fatal("Does not exist any GameDefinition for requested prefix: " + firstGameInSeries.getPrefix());
                                        sendDoesNotAnyGame(incomingMessage);
                                    }
                                }
                            }
                            else // if secondToken is not empty
                            {
                                // if received message at least has two part, first part is game series and second part is not empty
                                GameDefinition gameDefinition = gameListMap.get(firstToken);
                                if (gameDefinition == null)
                                {
                                    logger.info("found any game for gameId " + firstToken + " is null...");
                                    sendHelp(incomingMessage, player);
                                    continue;
                                }
                                else
                                {
                                    List<Game> gameListRelatedToSeries = _gameService.findGameBySeries(firstToken);

                                    if (gameListRelatedToSeries.isEmpty())
                                    {
                                        //send error
                                        logger.fatal("Does not exist any game for requested series with token " + firstToken + "] !");
                                        //TODO must send error message to express does not exist any game for requested series");
                                        sendDoesNotAnyGame(incomingMessage);
                                        savePlayerStateLog(incomingMessage, null);
                                    }
                                    else
                                        process(incomingMessage, gameDefinition, gameListRelatedToSeries.get(0));
                                }
                            }
                        }
                    }
                }

                logger.info("end process");
            }
            catch (Throwable e)
            {
                logger.error("err msg : " + e.getMessage(), e);
            }
        }
    }

    private void serviceOffRequest(String playerMobileNo)
    {
        Player player = _playerService.findLastActivePlay(playerMobileNo);
        if (player != null)
        {
            logger.info("customer service off requested...");

            player.setGameState(_playerService.GAME_OFF_STATE);
            player.setLastRequestDate(new Date());

            _playerService.updatePlayer(player);
        }
        else
            logger.info("unknown customer service off requested...");
    }

    private void process(IncomingMessage incomingMessage, GameDefinition relatedGame, Game game)
    {
        logger.info("entered into process");

        String firstToken = incomingMessage.getMsgItems().get(0);
        String secondToken = incomingMessage.getMsgItems().get(1);
        String customerMobileNo = incomingMessage.getSourceAddr();

        //if second code is gameStartCode register new customer
        if (secondToken != null && secondToken.equals(gameStartCode))
        {
            registerCustomer(incomingMessage, relatedGame, game.getId(), true);
        }
        //if second code is gameEndCode unregister exist customer
        else if (secondToken != null && secondToken.equals(gameEndCode))
            unRegisterCustomer(incomingMessage, relatedGame, true);
        else
        {
            //get current state of player
            //todo check conflict with _playerService. findLastActivePlay(customerMobileNo); called in main method

            //PlayerState currentPlayerState = _gameStatisticManager.getPlayerState(customerMobileNo, relatedGame.getGameCode());
            Player player = _playerService.findLastActivePlayByMobileAndGameSeries(customerMobileNo, relatedGame.getGameCode());

            //current player not have previous state in the system so send error for customer
            if (player == null)
            {
                logger.error("customer : " + customerMobileNo + " does not have previous state with code " + firstToken);
                sendHelp(incomingMessage, player);
            }
            else if (secondToken != null && secondToken.equals(gameStatusCode))
            {
                logger.info("customer status requested...");
                sendCurrentStatus(incomingMessage, relatedGame, player, null);
            }
            else if (secondToken != null && secondToken.equals(gameTableCode))
            {
                logger.info("customer table requested...");
                sendTable(incomingMessage, player, relatedGame);
                savePlayerStateLog(incomingMessage, player);
            }
            else if (secondToken != null && secondToken.equals(gameScoreCode))
            {
                logger.info("customer score requested...");
                sendScore(incomingMessage, player, relatedGame);
                savePlayerStateLog(incomingMessage, player);
            }
            else if (secondToken != null && secondToken.equals(gameCancelCode))
            {
                savePlayerStateLog(incomingMessage, player);

                GameStage startStage = relatedGame.getStartStage();
                int price = messagePrice(relatedGame, player, startStage);

                String customerMessage = ConfigLoader.getValue("game.message.cancelCode").replace("#", player.getScore().toString());
                customerMessage += "\n";

                player.setLastStageId(startStage.getId());
                player.setLastRequestDate(new Date());
                player = _playerService.updatePlayer(player);

                if (startStage.getHeader() != null)
                    customerMessage = startStage.getHeader() + "\n";

                customerMessage = customerMessage + " " + startStage.getDesc();

                if (startStage.getFooter() != null)
                    customerMessage = customerMessage + "\n" + startStage.getFooter();

                _messageSender.
                        sendMessage(incomingMessage.getSourceAddr(), customerMessage, relatedGame.getServiceID(), price);
                logger.warn("cancel code received, start stage message send to " + incomingMessage.getSourceAddr() +
                        " price: " + price);

                if (price > 0)
                {
                    player.incChargeNo();
                    player.setLastChargeDate(new Date());
                    _playerService.updatePlayer(player);
                }
            }
            else
            {
                //find related defined stage in system to current state of customer
                //and if find, process it otherwise send error to customer
                GameStage relatedGameStageToPlayer = findGameStage(relatedGame, player.getLastStageId());
                if (relatedGameStageToPlayer != null)
                {
                    if (secondToken != null && secondToken.equals(gameNextCode))
                    {
                        GameStage targetGameStage = findGameStage(relatedGame, relatedGameStageToPlayer.getNextStageCode());

                        savePlayerStateLog(incomingMessage, player);

                        if (targetGameStage != null)
                            nextStage(relatedGame, incomingMessage, player, targetGameStage);
                        else
                        {
                            int price = messagePrice(relatedGame, player, targetGameStage);

                            String customerMessage = "";
                            if (relatedGameStageToPlayer.isStartStage())
                                customerMessage += ConfigLoader.getValue("game.message.firstStage");
                            else
                                customerMessage += ConfigLoader.getValue("game.message.lastStage");

                            if (relatedGameStageToPlayer.getDesc() != null)
                                customerMessage = customerMessage + "\n" + relatedGameStageToPlayer.getDesc();

                            if (relatedGameStageToPlayer.getFooter() != null)
                                customerMessage = customerMessage + " " + relatedGameStageToPlayer.getFooter();

                            _messageSender.sendMessage(incomingMessage.getSourceAddr(), customerMessage, relatedGame.getServiceID(), price);
                            logger.info("Send Message " + customerMessage + " To Receiver " + incomingMessage.getSourceAddr() +
                                    " With ServiceId " + relatedGame.getServiceID() + " With Price " + price);

                            if (price > 0)
                            {
                                player.incChargeNo();
                                player.setLastChargeDate(new Date());
                            }

                            player.setLastStageId(relatedGameStageToPlayer.getName());
                            player = _playerService.updatePlayer(player);
                        }
                    }
                    else
                        processInputForStage(relatedGame, relatedGameStageToPlayer, player.getLastStageId(), secondToken, incomingMessage);
                }
                else
                    sendError(incomingMessage); //Todo Change better error
            }
        }
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
        else
            return gameStage.getPrice();
    }

    private void processInputForStage(GameDefinition relatedGame, GameStage currentPlayerStage,
                                      String previousStageId, String inputValue, IncomingMessage incomingMessage)
    {
        logger.warn("********************************");
        logger.info("entered into processInputForStage");
        String customerMobileNo = incomingMessage.getSourceAddr();
        boolean inputIsValid = false;

        List<Player> customerList = _playerService.findPlayer(customerMobileNo);
        Player customer = customerList.get(customerList.size() - 1);

        savePlayerStateLog(incomingMessage, customer);

        //apply all stage condition functions to customer state
        for (StageCondition nextStageCondition : currentPlayerStage.getConditionList())
        {
            if (nextStageCondition.getInputCode().equalsIgnoreCase(inputValue))
            {
                inputIsValid = true;

                //change customer stage
                String targetStageId = nextStageCondition.getTargetStageId();
                GameStage nextGameStage = findGameStage(relatedGame, targetStageId);

                customer.addScore(nextGameStage.getScore());
                if (nextGameStage.getQuestion())
                    customer.incQuestionNo();

                customer.setLastStageId(targetStageId);
                customer.setLastRequestDate(new Date());

                logger.info("CurrentStateId: [" + previousStageId + "] TargetStageId: [" + targetStageId + "]");
                customer = _playerService.updatePlayer(customer);

                GameStage targetGameStage = findGameStage(relatedGame, targetStageId);

                if (targetGameStage == null)
                {
                    //TODO check this condition
                    logger.fatal("targetGameStage with id " + targetStageId + " not found in system .....");
                }
                else
                    nextStage(relatedGame, incomingMessage, customer, targetGameStage);

                break;
            }
        }

        if (!inputIsValid)
            sendError(incomingMessage, currentPlayerStage, incomingMessage.getMsgItems().get(0));
    }

    private void nextStage(GameDefinition relatedGame, IncomingMessage incomingMessage, Player customer, GameStage targetGameStage)
    {
        boolean updateFlag = false;
        //send stage description
        String customerMessage = "";
        if (targetGameStage.getHeader() != null)
            customerMessage = targetGameStage.getHeader() + "\n";

        if (targetGameStage.getDesc() != null)
            customerMessage = customerMessage + " " + targetGameStage.getDesc();

        if (targetGameStage.getFooter() != null)
            customerMessage = customerMessage + " " + targetGameStage.getFooter();

        //if targetGameStage is final stage unRegistering customer and send goodBye message to it
        if (targetGameStage.isFinalStage())
        {
            //_gameStatisticManager.unRegisterUserWithState(currentPlayerState);
            customer.setGameState(GAME_END_STATE);

            customerMessage = customerMessage + "\n " +
                    (targetGameStage.getGoodByMessage() == null ? "" : targetGameStage.getGoodByMessage());

            updateFlag = true;
        }

        int price = messagePrice(relatedGame, customer, targetGameStage);

        _messageSender.sendMessage(incomingMessage.getSourceAddr(), customerMessage, relatedGame.getServiceID(), price);
        logger.info("Send Message " + customerMessage + " To Receiver " + incomingMessage.getSourceAddr() +
                " With ServiceId " + relatedGame.getServiceID() + " With Price " + price);

        if (price > 0)
        {
            customer.incChargeNo();
            customer.setLastChargeDate(new Date());
            updateFlag = true;
        }

//        customer.setLastStageId(targetGameStage.getName());

        if (updateFlag)
            _playerService.updatePlayer(customer);
    }

    private void sendStartStatus(IncomingMessage incomingMessage, Player player, Game game)
    {
        logger.info("Get Stage0");

        GameDefinition gameDefinition = gameListMap.get(game.getPrefix());

        int price = messagePrice(gameDefinition, player, gameDefinition.getStartStage());

        savePlayerStateLog(incomingMessage, player);

        player.setLastStageId(gameDefinition.getStartStage().getId());
        player.setLastRequestDate(new Date());

        player = _playerService.updatePlayer(player);

        //find gameStage
        GameStage relatedStageToPlayer = gameDefinition.getStartStage();

        if (relatedStageToPlayer == null)
            sendError(incomingMessage); //Todo Change better error
        else
        {
            String smsContent = relatedStageToPlayer.getDesc();

            if (relatedStageToPlayer.getHeader() != null)
                smsContent = relatedStageToPlayer.getHeader() + "\n" + smsContent;

            if (relatedStageToPlayer.getFooter() != null)
                smsContent = smsContent + "\n" + relatedStageToPlayer.getFooter();

            _messageSender.sendMessage(incomingMessage.getSourceAddr(), smsContent, gameDefinition.getServiceID(), price);
            logger.info("Send Message " + smsContent + " To Receiver " + incomingMessage.getSourceAddr() +
                    " With ServiceId " + gameDefinition.getServiceID() + " With Price " + price);

            if (price > 0)
            {
                player.incChargeNo();
                player.setLastChargeDate(new Date());
                _playerService.updatePlayer(player);
            }
        }
    }

    private void sendCurrentStatus(IncomingMessage incomingMessage, GameDefinition relatedGame, Player player, String msg)
    {
        logger.info("entered into sendCurrentStatus");

        //find gameStage
        GameStage relatedStageToPlayer = findGameStage(relatedGame, player.getLastStageId());

        if (relatedStageToPlayer == null)
            sendError(incomingMessage); //Todo Change better error
        else
        {
            int price = messagePrice(relatedGame, player, relatedStageToPlayer);

            String smsContent = relatedStageToPlayer.getDesc();

            if (msg != null)
                smsContent = msg + smsContent;
            else if (relatedStageToPlayer.getHeader() != null)
                smsContent = relatedStageToPlayer.getHeader() + "\n" + smsContent;

            if (relatedStageToPlayer.getFooter() != null)
                smsContent = smsContent + "\n" + relatedStageToPlayer.getFooter();

            _messageSender.sendMessage(incomingMessage.getSourceAddr(), smsContent, relatedGame.getServiceID(), price);
            logger.info("Send Message " + smsContent + " To Receiver " + incomingMessage.getSourceAddr() +
                    " With ServiceId " + relatedGame.getServiceID() + " With Price " + price);

            if (price > 0)
            {
                player.incChargeNo();
                player.setLastChargeDate(new Date());
                _playerService.updatePlayer(player);
            }
        }
    }

    private void unRegisterCustomer(IncomingMessage incomingMessage, GameDefinition relatedGame, boolean notifyPlayer)
    {
        logger.info("entered into unRegisterCustomer");
        String message;

        PlayerState currentPlayerState = _gameStatisticManager.getPlayerState(incomingMessage.getSourceAddr(), relatedGame.getGameCode());
        _gameStatisticManager.unRegisterUserWithState(currentPlayerState);

        Player player = _playerService.findLastActivePlayByMobileAndGameSeries(incomingMessage.getSourceAddr(), relatedGame.getGameCode());

        if (player != null)
        {
            savePlayerStateLog(incomingMessage, player);

            if (player.getGameState().intValue() != _playerService.GAME_OFF_STATE)
            {
                message = ConfigLoader.getValue("game.message.goodBye");
                player.setGameState(_playerService.GAME_OFF_STATE);
                player.setLastRequestDate(new Date());
                _playerService.updatePlayer(player);
            }
            else
                message = ConfigLoader.getValue("game.message.unregisterBefore");
        }
        else
            message = ConfigLoader.getValue("game.message.notMember");

        //todo send game good bye...
        if (notifyPlayer)
        {
            _messageSender.sendMessage(incomingMessage.getSourceAddr(), message, relatedGame.getServiceID());
            logger.info("Send Message goodby message To Receiver " + incomingMessage.getSourceAddr() +
                    " With ServiceId " + relatedGame.getServiceID());
        }
    }

    private void registerCustomer(IncomingMessage incomingMessage, GameDefinition gameDefinition, Long gameId, boolean sendMsg)
    {
        logger.info("entered into registerCustomer");

        int price = 0;
        String message = "";

        GameStage startStage = gameDefinition.getStartStage();

        logger.info("game code :" + gameDefinition.getGameCode());
        Player player = _playerService.findLastActivePlayByMobileAndGameSeries(incomingMessage.getSourceAddr(), gameDefinition.getGameCode());

        if (player != null)
        {
            if (player.getGameState().intValue() == _playerService.GAME_OFF_STATE)
            {
                player.setGameState(0);
                player.setRegisterDate(new Date());
                player.setLastRequestDate(new Date());
                player = _playerService.updatePlayer(player);

                message = ConfigLoader.getValue("game.message.registerAgain");
                logger.fatal("Player Register Again " + player.getId());

                price = messagePrice(gameDefinition, player, startStage);
            }
            else
            {
                logger.fatal("Player already registered" + player.getId().longValue());
                message = ConfigLoader.getValue("game.message.alreadyMember");
                sendCurrentStatus(incomingMessage, gameDefinition, player, message);
                return;
            }
        }
        else
        {
            player = new Player();
            player.setGameId(gameId);
            player.setMobile(incomingMessage.getSourceAddr());
            player.setGameState(0);//TODO what is this field
            player.setLastStageId(startStage.getId().toUpperCase());
            player.setLastRequestDate(new Date());
            player.setLastChargeDate(new Date());
            player.setRegisterDate(new Date());
            player.setScore(0);
            player.setVersion(0);
            player.setQuestionNo(0);
            player.setChargeNo(0);

            _playerService.savePlayer(player);

            savePlayerStateLog(incomingMessage, player);

            message = startStage.getWelcomeMessage() != null ? startStage.getWelcomeMessage() + "\n" : "";

            price = messagePrice(gameDefinition, player, startStage);
        }

        if (sendMsg)
        {
            if (startStage.getHeader() != null)
                message = message + " " + startStage.getHeader() + "\n";

            message = message + " " + startStage.getDesc();

            if (startStage.getFooter() != null)
                message = message + "\n" + startStage.getFooter();

            _messageSender.sendMessage(incomingMessage.getSourceAddr(), message, gameDefinition.getServiceID(), price);
            logger.warn("welcome message send to " + incomingMessage.getSourceAddr());
        }

        if (player != null && price > 0)
        {
            player.incChargeNo();
            player.setLastChargeDate(new Date());
            _playerService.updatePlayer(player);
        }
    }

    private void sendInvalidInput(IncomingMessage incomingMessage, String mustBe)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(ConfigLoader.getValue("game.message.invalidInput1").replaceAll("#", mustBe));
        sb.append("\n").append(ConfigLoader.getValue("game.message.invalidInput2"));

        _messageSender.sendMessage(incomingMessage.getSourceAddr(), sb.toString(), null, 0);
        logger.info("Send Message " + sb.toString() + " To Receiver " + incomingMessage.getSourceAddr());
    }

    private void sendDoesNotAnyGame(IncomingMessage incomingMessage)
    {
        logger.info("entered into sendDoesNotAnyGame");
        String msg = ConfigLoader.getValue("game.message.doesNotExistAnyGame");

        _messageSender.sendMessage(incomingMessage.getSourceAddr(), msg, null, 0);
        logger.info("Send Message " + msg + " To Receiver " + incomingMessage.getSourceAddr());
    }

    private void sendHelp(IncomingMessage incomingMessage, Player player)
    {
        logger.info("entered into sendHelp");

        if (player != null)
            savePlayerStateLog(incomingMessage, player);

        String msg = "";
        StringBuilder sb = new StringBuilder();

        if (useEngineHelp)
            sb.append(getEngineHelpMessage());
        else
        {
            if (gameSeriesList.size() > 1)
            {
                for (String nextSeries : gameSeriesList)
                    msg += nextSeries.toUpperCase() + ", ";

                msg = ConfigLoader.getValue("game.message.helpAll").replace("#", msg);
            }
            else
                msg = ConfigLoader.getValue("game.message.help").replace("#", gameSeriesList.get(0).toUpperCase());

            sb.append(msg).append("\n");
            sb.append(ConfigLoader.getValue("game.message.invalidInput2")).append("\n");
        }

        _messageSender.sendMessage(incomingMessage.getSourceAddr(), sb.toString(), null);
        logger.info("Send Message " + sb.toString() + " To Receiver " + incomingMessage.getSourceAddr());
    }

    private void savePlayerStateLog(IncomingMessage incomingMessage, Player player)
    {
        PlayerStateLog playerStateLog = new PlayerStateLog();
        if (player != null)
        {
            if (player.getGameId() != null)
                playerStateLog.setGameId(player.getGameId());

            if (player.getId() != null)
                playerStateLog.setPlayerId(player.getId());

            if (player.getLastStageId() != null)
                playerStateLog.setCurrentStage(player.getLastStageId());
        }

        playerStateLog.setMobile(incomingMessage.getSourceAddr());

        if (incomingMessage.getMsgItems().size() > 0)
            playerStateLog.setFirstToken(incomingMessage.getMsgItems().get(0));

        if (incomingMessage.getMsgItems().size() > 1)
            playerStateLog.setSecondToken(incomingMessage.getMsgItems().get(1));

        playerStateLog.setSetTime(new Date());
        _playerStateLogService.savePlayerStateLog(playerStateLog);
    }

    private void sendTable(IncomingMessage incomingMessage, Player player, GameDefinition game)
    {
        logger.info("entered into sendTable");
        StringBuilder sb = new StringBuilder();

        List<Player> playerList = _playerService.findByGameIdOrderByScore(player.getGameId());
        int max = (playerList.size() < 4) ? playerList.size() : 3;

        boolean foundThisPlayer = false;
        for (int i = 0; i < max; i++)
        {
            Player tmpPlayer = playerList.get(i);

            String msg = "";

            if (i == 0)
                msg = ConfigLoader.getValue("game.message.firstOne");
            else if (i == 1)
                msg = ConfigLoader.getValue("game.message.secondOne");
            else
                msg = ConfigLoader.getValue("game.message.thirdOne");

            if (incomingMessage.getSourceAddr().compareTo(tmpPlayer.getMobile()) == 0)
            {
                foundThisPlayer = true;
                msg = msg.replaceFirst("#", ConfigLoader.getValue("game.message.yourAre"));
            }
            else
                msg = msg.replaceFirst("#", tmpPlayer.getMobile().substring(9) + "**" + tmpPlayer.getMobile().substring(0, 7));

            msg = msg.replace("#", tmpPlayer.getScore().toString());
            sb.append(msg).append("\n");
        }

        if (!foundThisPlayer)
            sb.append(ConfigLoader.getValue("game.message.yourScore").replace("#", player.getScore().toString()));

        _messageSender.sendMessage(incomingMessage.getSourceAddr(), sb.toString(), game.getServiceID(), 0);
        logger.info("Send Message " + sb.toString() + " To Receiver " + incomingMessage.getSourceAddr() +
                " With ServiceId " + game.getServiceID());
    }

    private void sendScore(IncomingMessage incomingMessage, Player player, GameDefinition game)
    {
        logger.info("entered into sendScore");

        String msg = ConfigLoader.getValue("game.message.playerScore").replaceFirst("#", player.getQuestionNo().toString());
        msg = msg.replace("#", player.getScore().toString());

        _messageSender.sendMessage(incomingMessage.getSourceAddr(), msg, game.getServiceID(), 0);
        logger.info("Send Message " + msg + " To Receiver " + incomingMessage.getSourceAddr() +
                " With ServiceId " + game.getServiceID());
    }

    private void sendError(IncomingMessage incomingMessage)
    {
        logger.info("entered into sendError");
        StringBuffer sb = new StringBuffer();

        if (useEngineError)
            sb.append(getEngineErrorMessage());
        else
            sb.append(ConfigLoader.getValue("game.message.error"));

        _messageSender.sendMessage(incomingMessage.getSourceAddr(), sb.toString(), null, 0);
        logger.info("Send Message " + sb.toString() + " To Receiver " + incomingMessage.getSourceAddr());
    }

    private void sendError(IncomingMessage incomingMessage, GameStage gameStage, String gameId)
    {
        logger.info("entered into sendError");
        StringBuffer sb = new StringBuffer();

        if (useEngineError)
            sb.append(getEngineErrorMessage());
        else
        {
            sb.append(ConfigLoader.getValue("game.message.invalidCode"));

            if (!gameStage.getConditionList().isEmpty())
            {
                String msg = ConfigLoader.getValue("game.message.correctCode");
                msg = msg.replaceFirst("#", gameId.toUpperCase());

                String tmp = "";
                for (int i = 0; i < gameStage.getConditionList().size(); i++)
                {
                    StageCondition nextStageCondition = gameStage.getConditionList().get(i);
                    tmp += nextStageCondition.getInputCode().toUpperCase();

                    if (i < (gameStage.getConditionList().size() - 1))
                        tmp += " يا ";
                }

                sb.append(msg.replace("#", tmp));
            }
        }

        _messageSender.sendMessage(incomingMessage.getSourceAddr(), sb.toString(), null, 0);
        logger.info("Send Message " + sb.toString() + " To Receiver " + incomingMessage.getSourceAddr());
    }

    private GameStage findGameStage(GameDefinition game, String stageId)
    {
        for (GameStage nextGameStage : game.getStages())
        {
            if (nextGameStage.getId().equalsIgnoreCase(stageId))
                return nextGameStage;
        }

        return null;
    }

    private boolean existInBlackList(String playerMobileNo, String firstToken)
    {
        List<PlayerBlackList> playerBlackLists;
        if (firstToken != null && !firstToken.isEmpty())
            playerBlackLists = _playerBlackListService.findByMobileAndGamePrefix(playerMobileNo, firstToken);
        else
            playerBlackLists = _playerBlackListService.findByMobile(playerMobileNo);

        if (playerBlackLists != null && playerBlackLists.size() > 0)
        {
            logger.warn("Receive Message " + firstToken + " From Blocked Player " + playerMobileNo);
            return true;
        }
        else
            return false;
    }

    public void initGamesFromXmlDir() throws IOException
    {
        List<GameDefinition> returnList = new ArrayList<GameDefinition>();

        logger.error("gameXmlFilePath = " + gameXmlFilePath);
        logger.error("convert XML back to game definition object...!");

        File xmlDirPath = new File(gameXmlFilePath);
        if (!xmlDirPath.exists())
        {
            logger.error("=====================================================");
            logger.error("=====================================================");
            logger.error("xmlDirPath does not exist in system >> " + xmlDirPath);
            System.exit(1);
        }

        File[] xmlDirFiles = xmlDirPath.listFiles();
        for (File nextXmlDirFile : xmlDirFiles)
        {

            logger.info("nextXmlDirFile.getName() = " + nextXmlDirFile.getName());
            if (nextXmlDirFile.isDirectory())
                continue;
            if (!nextXmlDirFile.getName().toLowerCase().endsWith(".xml"))
                continue;

            logger.info(">>nextXmlDirFile.getName() = " + nextXmlDirFile.getName());

            GameDefinition game = (GameDefinition) converter.convertFromXMLToObject(nextXmlDirFile.getAbsolutePath());

            List<Game> foundGame = _gameService.findGame(game.getGameCode(), game.getSeries());

            Game nextGame;
            if (foundGame.size() > 0)
                nextGame = foundGame.get(0);
            else
                nextGame = new Game();

            nextGame.setPrefix(game.getGameCode());
            nextGame.setParentPrefix(game.getSeries());
            nextGame.setReplacable(false);
            nextGame.setServiceID(game.getServiceID());
            nextGame.setFileName(nextXmlDirFile.getName());
            nextGame.setReplacable(game.isReplaceable());
            nextGame.setChargePerDay(game.getPricePerDay());

            _gameService.saveGame(nextGame);

            if (!gameSeriesList.contains(nextGame.getParentPrefix().toLowerCase()))
                gameSeriesList.add(nextGame.getParentPrefix().toLowerCase());

            returnList.add(game);

            printGameInfo(game);
        }

        logger.info("process xml files done...");

        for (String nextGameSeries : gameSeriesList)
            logger.info("nextGameSeriesCode = " + nextGameSeries);

        setGameList(returnList);

        loadPreviousPlayState();

        scheduleTaskManager = new ScheduleTaskManager(_gameService.findAllGames(), gameListMap, _playerService, _messageSender, _gameService);
        scheduleTaskManager.initTaskManager();
    }

    private void loadPreviousPlayState()
    {
        List<Player> playList = _playerService.findAllPlayers();

        for (Player nextPlay : playList)
        {
            if (nextPlay.getGameState() == 100)
                continue;

            Game foundGame = _gameService.findGame(nextPlay.getGameId());
            if (foundGame == null)
            {
                logger.error("err msg game with id " + nextPlay.getGameId() + " does not in system...");
                continue;
            }

            PlayerState oldPlayState = new PlayerState(nextPlay.getMobile(), foundGame.getPrefix(), nextPlay.getLastStageId());

            for (com.vas.game.model.PlayerState nextPlayerState : nextPlay.getPlayerStates())
            {
                GameParameter nextGameParameter = new GameParameter(nextPlayerState.getParamName(),
                        nextPlayerState.getParamLabel(), "", nextPlayerState.getParamValue());
                oldPlayState.getPlayerParameters().add(nextGameParameter);
            }
            _gameStatisticManager.registerUserWithState(oldPlayState);
            logger.info("next play for [" + nextPlay.getMobile() + "] with state [" + nextPlay.getLastStageId() + "] loaded...");
        }
    }

    private static void printGameInfo(GameDefinition game)
    {
        logger.error("=====================================================1");
        logger.info("parameters >>");
        for (GameParameter nextPerson : game.getParameters())
        {
            logger.info("  " + nextPerson);
//            logger.info("  >>" + (nextPerson.getCode() == null ? "!!!" : nextPerson.getNewAddress().getPelak()));
//            logger.info("nextPerson.getEmail() = " + nextPerson.getEmail());
        }

        logger.info("stages >>");
        for (GameStage nextGameStage : game.getStages())
            logger.info("  nextGameStage = " + nextGameStage);

        logger.info(game);
        logger.info(game.getWinCondition());
    }
}