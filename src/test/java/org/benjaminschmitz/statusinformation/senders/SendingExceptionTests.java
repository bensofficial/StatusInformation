package org.benjaminschmitz.statusinformation.senders;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SendingExceptionTests {

	private static final String MESSAGE = "An error occurred.";

	@Test
	void testWithMessage() {
		SendingException sendingException = new SendingException(MESSAGE);
		assertEquals(MESSAGE, sendingException.getMessage());
	}

	@Test
	void testWithoutMessage() {
		SendingException sendingException = new SendingException();
		assertEquals(null, sendingException.getMessage());
	}
}
