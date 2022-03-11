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

package org.benjaminschmitz.statusinformation.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.benjaminschmitz.statusinformation.configuration.Configuration;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Calendar;

public class GoodMorningMessageAPI implements APIInterface {
    private static final Logger logger = LogManager.getLogger();

    static boolean isWeekend(Calendar today) {
        return today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    /**
     * https://stackoverflow.com/questions/31993153/java-split-string-on-comma-except-when-between-parenthesis
     * <p>
     * https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java
     */
    boolean isHoliday(Configuration configuration) {
        String URL = "https://sindheuteferien.celll.net/?api=1";
        String webContent;
        try {
            webContent = API.get(URL);
        } catch (IOException e) {
            logger.error("An error occurred while getting holidays. Assuming that today is no holiday.");
            return false;
        }

        // Removing HTML tags (https://stackoverflow.com/a/3149645)
        webContent = Jsoup.parse(webContent).text();

        return webContent.matches("Ja,[a-zA-Z]+,"+configuration.getLocation());
    }

    @Override
    public String get(Configuration configuration) {
        logger.debug("Loading good morning message.");
        if (isHoliday(configuration)) {
            logger.debug("Taking holiday message.");
            return configuration.getGoodMorningMessageHoliday();
        } else if (isWeekend(Calendar.getInstance())) {
            logger.debug("Taking weekend message.");
            return configuration.getGoodMorningMessageWeekend();
        } else {
            logger.debug("Taking normal day message.");
            return configuration.getGoodMorningMessageNormalDay();
        }
    }
}
