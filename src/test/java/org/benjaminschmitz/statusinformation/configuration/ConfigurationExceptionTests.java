/*
 * Copyright (c) 2022 Benjamin Schmitz <dev@benjamin-schmitz.org>.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package org.benjaminschmitz.statusinformation.configuration;

import org.benjaminschmitz.statusinformation.senders.SendingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigurationExceptionTests {

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
