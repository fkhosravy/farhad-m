package com.vas.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ResourceBundle;


public final class ConfigLoader {
    private static final String BUNDLE_NAME = "application-config";

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    private static final Logger LOGGER = Logger.getLogger(ConfigLoader.class);

    private ConfigLoader() {
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
    public static String get(final String key, final Object... arguments) {
        String value = resourceBundle.getString(key);
        if (value == null)
            return value;
        try {
            value = new String(value.getBytes("ISO-8859-1"), "UTF-8"); //$NON-NLS-1$ //$NON-NLS-2$
        } catch (UnsupportedEncodingException e) {
            if (LOGGER.isEnabledFor(Level.WARN))
                LOGGER.warn("Encoding error for " + value, e); //$NON-NLS-1$
        }
        return arguments == null || arguments.length == 0 ? value : MessageFormat.format(value, arguments);
    }

}
