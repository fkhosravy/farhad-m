package com.vas.util;

import org.apache.log4j.Logger;

public class GameUtil {
    private static Logger logger = Logger.getLogger(GameUtil.class);

    public static final String MAP_ID_SEPARATOR = "-*-";

    public static String getPlayerId(String mobile, String gameId) {
        return (mobile + MAP_ID_SEPARATOR + gameId).toLowerCase();
    }

}
