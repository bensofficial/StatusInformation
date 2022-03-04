package org.benjaminschmitz.statusinformation.senders;

public class SendingException extends RuntimeException {
    public SendingException() {
        super();
    }

    public SendingException(String message) {
        super(message);
    }
}
