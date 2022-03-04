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

package org.benjaminschmitz.statusinformation;

public class TestException extends RuntimeException {
	private static final long serialVersionUID = -5612482870443024603L;

	public TestException() {
		super();
	}

	public TestException(String message) {
		super(message);
	}
}
