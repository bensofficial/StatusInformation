package org.benjaminschmitz.statusinformation.senders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.benjaminschmitz.statusinformation.configuration.Configuration;

public class StdOutSender implements Sender {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void send(String message, Configuration configuration) {
        System.out.print(message);
    }
}
