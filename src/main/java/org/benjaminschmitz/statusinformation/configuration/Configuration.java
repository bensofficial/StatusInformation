/*
 * Copyright (c) 2022 Benjamin Schmitz <dev@benjamin-schmitz.org>.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package org.benjaminschmitz.statusinformation.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

public final class Configuration {
    private static final Logger logger = LogManager.getLogger();
    public static final String PROPERTIES_FILE = "StatusInformation.properties";
    private final PropertyReader propertyReader;

    public Configuration() {
        this(PROPERTIES_FILE);
    }

    public Configuration(String propertiesFile) {
        logger.debug("Initialising");
        try {
            propertyReader = new PropertyReader(propertiesFile);
        } catch (IOException e) {
            logger.fatal("Error loading " + propertiesFile);
            throw new ConfigurationException("Error loading " + propertiesFile);
        }
    }

    public Configuration(PropertyReader propertyReader) {
        logger.debug("Initialising");
        this.propertyReader = propertyReader;
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
