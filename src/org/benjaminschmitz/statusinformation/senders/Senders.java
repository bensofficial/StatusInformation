package org.benjaminschmitz.statusinformation.senders;

import java.util.LinkedList;
import java.util.List;

public class Senders {
    private final List<Sender> senders;

    public Senders() {
        senders = new LinkedList<>();
        senders.add(new TelegramSender());
    }

    public void sendToAll(String message) {
        for (Sender sender : senders) {
            sender.send(message);
        }
    }
}
