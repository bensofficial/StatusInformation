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

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyReaderTests {
    private static final String TEST_CONFIG = """
            key=value
            """;

    @Test
    void testGetProperty() throws IOException {
        InputStream stream = new ByteArrayInputStream(TEST_CONFIG.getBytes());
        PropertyReader propertyReader = new PropertyReader(stream);
        assertEquals("value", propertyReader.getProperty("key"));
    }

    @Test
    void testGetPropertyMissingProperty() throws IOException {
        InputStream stream = new ByteArrayInputStream(TEST_CONFIG.getBytes());
        PropertyReader propertyReader = new PropertyReader(stream);
        assertNull(propertyReader.getProperty("not-a-key"));
    }

    @Test
    void testConstructor() throws IOException {
        PropertyReader propertyReader = new PropertyReader(Configuration.PROPERTIES_FILE);
    }

    @Test
    void testConstructorWithIllegalFile() throws IOException {
        assertThrows(IOException.class, () -> new PropertyReader("THIS IS NO FILE"));
    }
}
