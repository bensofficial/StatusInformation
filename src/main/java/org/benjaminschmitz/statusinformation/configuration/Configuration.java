package org.benjaminschmitz.statusinformation.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

public final class Configuration {
    private static final Logger logger = LogManager.getLogger();
    private static final String PROPERTIES_FILE = "StatusInformation.properties";
    private final PropertyReader propertyReader;

    public static void main(String[] args) throws ConfigurationException {
        Configuration c = new Configuration();
        System.out.println(c.getLocation());
    }

    public Configuration() {
        logger.debug("Initialising");
        try {
            propertyReader = new PropertyReader(PROPERTIES_FILE);
        } catch (IOException e) {
            logger.fatal("Error loading " + PROPERTIES_FILE);
            throw new ConfigurationException("Error loading " + PROPERTIES_FILE);
        }
    }

    public String getLocation() {
        return propertyReader.getProperty("location");
    }

    public String getGoodMorningMessageNormalDay() {
        return propertyReader.getProperty("good-morning-message-normal-day");
    }

    public String getGoodMorningMessageWeekend() {
        return propertyReader.getProperty("good-morning-message-weekend");
    }

    public String getGoodMorningMessageHoliday() {
        return propertyReader.getProperty("good-morning-message-holiday");
    }

    public String getTelegramBotToken() {
        return propertyReader.getProperty("telegram-bot-token");
    }

    public String getTelegramChannelID() {
        return propertyReader.getProperty("telegram-channel-id");
    }
}
