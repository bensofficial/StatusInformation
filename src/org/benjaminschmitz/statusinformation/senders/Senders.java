package org.benjaminschmitz.statusinformation.senders;

import java.util.LinkedList;
import java.util.List;

public class Senders {
    <<<<<<<HEAD
    private final List<Sender> senders;=======
    private final List<Sender> senders;>>>>>>>branch'main'
    of https:// github.com/bensofficial/StatusInformation.git

    <<<<<<<HEAD

    public Senders() {
        senders = new LinkedList<>();
        senders.add(new TelegramSender());
    }=======

    public Senders() {
        senders = new LinkedList<>();
        senders.add(new TelegramSender());
    }>>>>>>>branch'main'

    of https:// github.com/bensofficial/StatusInformation.git

    <<<<<<<HEAD

    public void sendToAll(String message) {
        for (Sender sender : senders) {
            sender.send(message);
        }
    }=======

    public void sendToAll(String message) {
        for (Sender sender : senders) {
            sender.send(message);
        }
    }>>>>>>>branch'main'

    of https:// github.com/bensofficial/StatusInformation.git
}
