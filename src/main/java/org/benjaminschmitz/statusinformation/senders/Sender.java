package org.benjaminschmitz.statusinformation.senders;

import org.benjaminschmitz.statusinformation.configuration.Configuration;

@FunctionalInterface
public interface Sender {
    void send(String message, Configuration configuration);
}
