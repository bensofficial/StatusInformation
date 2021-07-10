package org.benjaminschmitz.statusinformation.senders;

@FunctionalInterface
public interface Sender {
	void send(String message);
}
