package com.vas.server.service;

import com.vas.engine.xml.model.GameDefinition;
import com.vas.engine.xml.model.Reminder;
import com.vas.game.model.Game;
import com.vas.game.service.GameService;
import com.vas.game.service.PlayerService;
import com.vas.server.scheduler.ScheduleTask;
import com.vas.server.sender.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: farhad
 * Date: 4/18/14
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleTaskManager
{
    MessageSender messageSender;

    PlayerService playerService;

    GameService gameService;

    List<ScheduleTask> scheduleTaskList = new ArrayList<ScheduleTask>();

    List<Game> gameList;

    Map<String, GameDefinition> gameListMap;

    public List<ScheduleTask> getScheduleTaskList() {
        return scheduleTaskList;
    }

    public void setScheduleTaskList(List<ScheduleTask> scheduleTaskList) {
        this.scheduleTaskList = scheduleTaskList;
    }

    public ScheduleTaskManager(List<Game> gameList, Map<String, GameDefinition> gameListMap, PlayerService playerService,
                               MessageSender messageSender, GameService gameService)
    {
        this.gameList = gameList;
        this.gameListMap = gameListMap;
        this.playerService = playerService;
        this.messageSender = messageSender;
        this.gameService = gameService;
    }

    public void initTaskManager()
    {
        int i = 0;

        for (Game game : gameList)
        {
            GameDefinition gameDefinition = gameListMap.get(game.getPrefix());
            for (Reminder reminder : gameDefinition.getReminderList())
            {
                ScheduleTask scheduleTask = new ScheduleTask();
                scheduleTask.initScheduleTask(gameDefinition, game, reminder, playerService, gameService, messageSender);
                scheduleTaskList.add(scheduleTask);
            }
        }
    }
}
