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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class API {
    private static final Logger logger = LogManager.getLogger();
    private final Configuration configuration;
    private final GoodMorningMessageAPI goodMorningMessageAPI;

    public API(Configuration configuration) {
        logger.debug("Initialising");
        this.configuration = configuration;
        goodMorningMessageAPI = new GoodMorningMessageAPI();
    }

    /**
     * https://stackoverflow.com/questions/31462/how-to-fetch-html-in-java
     */
    static String get(String URL) throws IOException {
        String content;
        URLConnection connection;
        try {
            connection = new URL(URL).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        } catch (IOException | NoSuchElementException | IllegalStateException e) {
            logger.error("Could not get content of " + URL);
            throw new IOException();
        }

        return content;
    }

    public String getGoodMorningMessage() {
        return goodMorningMessageAPI.get(configuration);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public GoodMorningMessageAPI getGoodMorningMessageAPI() {
        return goodMorningMessageAPI;
    }


}
