package org.benjaminschmitz.statusinformation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.benjaminschmitz.statusinformation.api.API;
import org.benjaminschmitz.statusinformation.configuration.Configuration;
import org.benjaminschmitz.statusinformation.senders.Senders;

import java.util.LinkedList;
import java.util.List;

public class StatusInformation {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        API api = new API(configuration);
        List<String> messages = new LinkedList<>();
        Senders senders = new Senders(configuration);

        messages.add(api.getGoodMorningMessage());

        logger.debug("Building message list to one message string.");
        StringBuilder stringBuilder = new StringBuilder();
        for (String message : messages) {
            stringBuilder.append(message);
            stringBuilder.append("\n");
        }
        String message = stringBuilder.toString();

        logger.debug("Sending message");
        senders.sendToAll(message);
    }
}


