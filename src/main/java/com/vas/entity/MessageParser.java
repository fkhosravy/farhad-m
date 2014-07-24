package com.vas.entity;

import com.vas.engine.entity.IncomingMessage;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MessageParser {
    private static Logger logger = Logger.getLogger(MessageParser.class);

    String separator = " ";
    String separatorName = "کاراکتر فاصله";
    String prefix = "";


    public void setSeparator(String separator) {
        logger.error("separator = " + separator);
        if (separator.equals(""))
            this.separator = " ";
        else
            this.separator = separator;
    }

    public String getSeparator() {
        return separator;
    }

    public String getSeparatorName() {
        return separatorName;
    }

    public void setSeparatorName(String separatorName) {
        this.separatorName = separatorName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<String> parse(IncomingMessage message) {

        String[] msgPartItems = message.getMessageContent().split(separator);

        List<String> returnList = new ArrayList<String>(msgPartItems.length);
        for (String nextMsgPartItem : msgPartItems) {
            String msgPartItem = nextMsgPartItem.toLowerCase().trim();
            if (!msgPartItem.isEmpty() && !msgPartItem.equals(prefix.toLowerCase()))
                returnList.add(msgPartItem);
        }

        logger.info("returnList = " + returnList);
        logger.info("returnList = " + returnList.size());

        return returnList;
    }


}
