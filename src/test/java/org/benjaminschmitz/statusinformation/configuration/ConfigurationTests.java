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

import org.benjaminschmitz.statusinformation.api.API;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationTests {
    private static final String TEST_CONFIG = """
            location=Test0
            
            good-morning-message-normal-day=Test1
            good-morning-message-weekend=Test2
            good-morning-message-holiday=Test3
                        
            telegram-bot-token=Test4
            telegram-channel-id=Test5
            """;

    @Test
    void testGetter() throws IOException {

        InputStream stream = new ByteArrayInputStream(TEST_CONFIG.getBytes());

        Configuration configuration = new Configuration(new PropertyReader(stream));

        assertEquals("Test0", configuration.getLocation());

        assertEquals("Test1", configuration.getGoodMorningMessageNormalDay());
        assertEquals("Test2", configuration.getGoodMorningMessageWeekend());
        assertEquals("Test3", configuration.getGoodMorningMessageHoliday());

        assertEquals("Test4", configuration.getTelegramBotToken());
        assertEquals("Test5", configuration.getTelegramChannelID());
    }

    @Test
    void testConstructorWrongFile() {
        assertThrows(ConfigurationException.class, () -> new Configuration("THIS IS NO FILE"));
    }

    @Test
    void testConstructor() {
        Configuration configuration = new Configuration();
    }
}
