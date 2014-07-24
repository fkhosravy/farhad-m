package com.vas.server;

import com.vas.entity.GameEventDescription;
import com.vas.entity.GameInfo;
import com.vas.engine.xml.XMLConverter;
import com.vas.engine.xml.model.GameDefinition;
import com.vas.engine.xml.model.GameParameter;
import com.vas.engine.xml.model.GameStage;
import com.vas.server.service.GameEngineManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

public class MainStarter {
    private final static long ONCE_PER_DAY = 1000 * 60 * 60 * 24;
    static Log logger = LogFactory.getLog(MainStarter.class);

    public static void main(final String[] args) throws IOException {

        ConfigurableApplicationContext appContext
                = new ClassPathXmlApplicationContext("classpath:appContext-Server.xml",
                "classpath:/META-INF/spring/applicationContext*.xml");
        appContext.registerShutdownHook();

        IoAcceptor ioAcceptor = (IoAcceptor) appContext.getBean("ioAcceptor");
        InetSocketAddress defaultLocalAddress = (InetSocketAddress) ioAcceptor.getDefaultLocalAddress();
        logger.info("===========================\n" +
                "Started and listening on " +
                defaultLocalAddress.getHostName() + ":" + defaultLocalAddress.getPort());

        GameEngineManager gameEngine = (GameEngineManager) appContext.getBean("gameEngineManager");

        // comment before
        List<GameDefinition> gameDefinitionList = parseGameDefecation(appContext, gameEngine.getGameXmlFilePath());

        gameEngine.setGameList(gameDefinitionList);


        GameInfo game1 = (GameInfo) appContext.getBean("game1");
        logger.info("game1.get = " + game1.getParameterList().size());
        logger.info("game1.getGameCode() = " + game1.getGameCode());
        logger.info("game1.getGameCode() = " + game1.getName());

        for (GameParameter next : game1.getParameterList()) {
            logger.info("next = " + next);
        }

        for (GameEventDescription next : game1.getEventList()) {
            logger.info("next = " + next);
        }

//        ScheduleTask task = (ScheduleTask) appContext.getBean("scheduleTask");
//        task.initScheduleTask(gameEngine.getGameListMap(), _gameService.findAllGames());
//        Timer timer = new Timer();
//        Calendar runTime = new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, 12, 30);
//        timer.scheduleAtFixedRate(task, runTime.getTime(), ONCE_PER_DAY);
    }

    private static List<GameDefinition> parseGameDefecation(ApplicationContext appContext,
                                                            String gameXmlFilePath) throws IOException {

        List<GameDefinition> returnList = new ArrayList<GameDefinition>();

        logger.error("gameXmlFilePath = " + gameXmlFilePath);
        logger.error("convert XML back to game definition object...!");

        XMLConverter converter = (XMLConverter) appContext.getBean("XMLConverter");

        File xmlDirPath = new File(gameXmlFilePath);
        if (!xmlDirPath.exists()) {
            logger.warn("=====================================================");
            logger.warn("=====================================================");
            logger.warn("xmlDirPath does not exist in system >> " + xmlDirPath);
            System.exit(1);
        }

        File[] xmlDirFiles = xmlDirPath.listFiles();
        for (File nextXmlDirFile : xmlDirFiles) {

            logger.info("nextXmlDirFile.getName() = " + nextXmlDirFile.getName());
            if (nextXmlDirFile.isDirectory())
                continue;
            if (!nextXmlDirFile.getName().toLowerCase().endsWith(".xml"))
                continue;

            logger.info(">>nextXmlDirFile.getName() = " + nextXmlDirFile.getName());

            GameDefinition game = (GameDefinition) converter.convertFromXMLToObject(nextXmlDirFile.getAbsolutePath());
            returnList.add(game);

            printGameInfo(game);
        }

        logger.info("process xml files done...");
        return returnList;
    }

    private static void printGameInfo(GameDefinition game) {
        logger.info("parameters >>");
        for (GameParameter nextPerson : game.getParameters()) {
            logger.info("  " + nextPerson);
//            logger.info("  >>" + (nextPerson.getCode() == null ? "!!!" : nextPerson.getNewAddress().getPelak()));
//            logger.info("nextPerson.getEmail() = " + nextPerson.getEmail());
        }

        logger.info("stages >>");
        for (GameStage nextGameStage : game.getStages()) {
            logger.info("  nextGameStage = " + nextGameStage);
        }

        logger.info(game);
        logger.info(game.getWinCondition());
    }

}
