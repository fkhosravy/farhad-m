package com.vas.util;

import org.apache.log4j.Logger;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/26/13 1:44 AM
 */
public class GameUtil {
    private static Logger logger = Logger.getLogger(GameUtil.class);

    public static final String MAP_ID_SEPARATOR = "-*-";

    public static String getPlayerId(String mobile, String gameId) {
        return (mobile + MAP_ID_SEPARATOR + gameId).toLowerCase();
    }

}
