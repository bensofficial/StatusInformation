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
import org.json.JSONObject;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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
    static boolean isHoliday(Date day) {
        String URL = "https://ferien-api.de/api/v1/holidays/"; // + ConfigurationUtil.LOCATION;
        String JSON;
        try {
            JSON = API.get(URL);
        } catch (IOException e) {
            logger.error("An error occurred while getting holidays. Assuming that today is no holiday.");
            return false;
        }

        // Remove [ and ] at the first and last position
        JSON = JSON.replace("[", "");
        JSON = JSON.replace("]", "");

        JSON = JSON.replace("},", "};");

        long size = Arrays.stream(JSON.split(";")).map((t) -> {
            JSONObject obj = new JSONObject(t);
            String start = obj.getString("start");
            String end = obj.getString("end");
            return new Holidays(start, end);
        }).filter((t) -> t.getStart().before(day)).filter((t) -> t.getEnd().after(day)).count();

        return size >= 1;
    }

    @Override
    public String get(Configuration configuration) {
        logger.debug("Loading good morning message.");
        if (isHoliday(new Date())) {
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

class Holidays {
    private static final Logger logger = LogManager.getLogger();

    private final Date start;
    private final Date end;

    public Holidays(Date start, Date end) {
        if (end.before(start)) {
            logger.error("Holidays end before they start.");
            throw new IllegalArgumentException();
        }

        this.start = start;
        this.end = end;
    }

    public Holidays(String start, String end) {
        this(parseString(start), parseString(end));
    }

    public static Date parseString(String date) {
        // Adding the seconds
        return Date.from(Instant.parse(date.replace("Z", ":00Z")));
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Holiday from " + start + " to " + end;
    }
}
