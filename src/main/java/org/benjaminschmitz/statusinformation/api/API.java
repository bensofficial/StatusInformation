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
}
