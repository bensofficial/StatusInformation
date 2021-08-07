package org.benjaminschmitz.statusinformation.senders;

public class SendingException extends RuntimeException {
	private static final long serialVersionUID = -803313340011254269L;

	public SendingException() {
		super();
	}

	public SendingException(String message) {
		super(message);
	}
}
