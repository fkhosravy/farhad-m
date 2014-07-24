package com.vas.server.servlet;

import com.vas.game.model.Player;
import com.vas.game.service.PlayerService;
import com.vas.server.receiver.MessageReceiver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

@Component("receiverServlet")
public class ReceiverServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(ReceiverServlet.class);

    public static final String FROM_FIELD = "phone";
    public static final String MESSAGE_FIELD = "message";

    @Autowired
    private MessageReceiver messageProcessor;

    @Qualifier("playerServiceImpl")
    @Autowired
    private PlayerService _playerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
//        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

//        messageProcessor = (MessageReceiver) context.getBean("pardisMsgReceiver");
//        _playerService = (PlayerService) context.getBean("PlayerService");

        logger.info("messageProcessor = " + messageProcessor);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doPost called.... ");
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        logger.info("doGet called.... ");
        String fromAddress = request.getParameter(FROM_FIELD);
        logger.info("fromAddress = " + fromAddress);

        if (fromAddress == null || fromAddress.isEmpty())
            logger.warn("The Requested URL has not 'phone' field : " + request.getRequestURL()+"?"+request.getQueryString());
        else
        {
            if (request.getRequestURL().toString().endsWith("UserState"))
            {
                List<Player> playerList = _playerService.findPlayer(fromAddress);

                if (playerList != null && !playerList.isEmpty())
                {
                    String state = (playerList.get(0).getGameState().longValue() != _playerService.GAME_OFF_STATE) ? "OK " : "NO ";
                    state += (playerList.get(0).getRegisterDate() != null) ? playerList.get(0).getRegisterDate().getTime() : "empty";
                    state += " ";

                    if (playerList.get(0).getLastRequestDate() != null && playerList.get(0).getGameState().longValue() == _playerService.GAME_OFF_STATE)
                        state += playerList.get(0).getLastRequestDate().getTime();
                    else
                        state += "empty";

                    response.getOutputStream().print(state);
                }
                else
                    response.getOutputStream().print("NO empty empty");
            }
            else
            {
                String msgBody = request.getParameter(MESSAGE_FIELD);
                logger.info("msgBody = " + msgBody);

                if (msgBody == null || msgBody.isEmpty())
                    logger.warn("The Requested URL has not 'message' field : " + request.getRequestURL()+"?"+request.getQueryString());
                else
                {
                    String message = URLDecoder.decode(msgBody, "UTF-8");
                    logger.info("decoded message = " + message);
                    logger.fatal("Received Message:" + message + " From Phone:" + fromAddress);

                    messageProcessor.processMessage(fromAddress, "", message);
                }
            }
        }
    }
}
