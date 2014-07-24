package com.vas.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.ConfigurationException;

public final class ConfigLoader {
    private static final Logger logger = Logger.getLogger(ConfigLoader.class);

//    private static final String BUNDLE_NAME = "application-config";
//    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
    private static Properties propFile;

    private static HashMap<String, String> propMap = new HashMap<String, String>();

    public ConfigLoader() {
    }

    private static void loadConfigFile() {
        propFile = new Properties();
        try {
//            resourceBundle = ResourceBundle.getBundle("application-config", new Locale("fa"));
            propFile.load(ConfigLoader.class.getClassLoader().getResourceAsStream("application-config.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error(e.getCause());
            logger.error(e.getStackTrace());
        }
    }

    public static void loadConfig() {
        if (propFile == null)
            loadConfigFile();
        for (String propStr : propFile.stringPropertyNames()) {
            propMap.put(propStr, propFile.getProperty(propStr));
        }
    }

    public static String getValue(String key) {
        try {
            if (propMap.get(key) != null)
                return new String(propMap.get(key).getBytes("ISO-8859-1"), "UTF-8");
            else
                return null;
        }
        catch (UnsupportedEncodingException e)
        {
            logger.error(e.getMessage());
            return propMap.get(key);
        }
    }

    public static HashMap<String, String> getPropMap() {
        return propMap;
    }

    public static void setProperties(HashMap<String, String> propValues) {

        try {
            // writing to file
            PropertiesConfiguration config = new PropertiesConfiguration("application-config.properties");
            String basePath = config.getBasePath();
            System.out.println("basePath = " + basePath);
            for (String key : propValues.keySet()) {
                config.setProperty(key, propValues.get(key));
            }
            config.save();

            // reloading properties again
            loadConfigFile();

        } catch (ConfigurationException e) {
            logger.error(e.getMessage());
            logger.error(e.getCause());
            logger.error(e.getStackTrace());
        }
    }

    /**
     * <p>
     * Resolve a message by a key and argument replacements.
     * </p>
     *
     * @param key       the message to look up
     * @param arguments optional message arguments
     * @return the resolved message
     * @see java.text.MessageFormat#format(String, Object...)
     */
//    public static String get(final String key, final Object... arguments) {
//        String value = resourceBundle.getString(key);
//        if (value == null)
//            return value;
//        try {
//            value = new String(value.getBytes("ISO-8859-1"), "UTF-8"); //$NON-NLS-1$ //$NON-NLS-2$
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            if (logger.isEnabledFor(Level.WARN))
//                logger.warn("Encoding error for " + value, e); //$NON-NLS-1$
//        }
//        return arguments == null || arguments.length == 0 ? value : MessageFormat.format(value, arguments);
//    }

}
