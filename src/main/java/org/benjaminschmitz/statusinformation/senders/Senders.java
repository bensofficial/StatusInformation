package org.benjaminschmitz.statusinformation.senders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.benjaminschmitz.statusinformation.configuration.Configuration;

import java.util.LinkedList;
import java.util.List;

public class Senders {
    private static final Logger logger = LogManager.getLogger();
    private final Configuration configuration;
    private final List<Sender> senders;


    public Senders(Configuration configuration) {
        logger.debug("Initialising");
        this.configuration = configuration;
        senders = new LinkedList<>();

        senders.add(new TelegramSender());
        senders.add(new StdOutSender());
    }

    public void sendToAll(String message) {
        for (Sender sender : senders) {
            logger.debug("Sending to " + sender);
            try {
                sender.send(message, configuration);
            } catch (SendingException e) {
                logger.error("Error while sending to " + sender + ". Error message: " + e);
            }
        }
    }

    public List<Sender> getSenders() {
        return senders;
    }
}
