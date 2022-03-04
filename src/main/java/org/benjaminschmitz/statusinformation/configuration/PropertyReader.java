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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static final Logger logger = LogManager.getLogger();
    private final Properties properties;

    /**
     * Making the Properties-Object
     *
     * @param file name of the config file
     */
    public PropertyReader(String file) throws IOException {
        InputStream stream;
        stream = new FileInputStream(file);
        properties = new Properties();
        properties.load(stream);
        stream.close();
    }

    public PropertyReader(InputStream stream) throws IOException {
        properties = new Properties();
        properties.load(stream);
        stream.close();
    }

    /**
     * Returning the String of the property
     *
     * @param property key of the property
     * @return String value of the property
     */
    public String getProperty(String property) {
        String result = properties.getProperty(property);
        if (result == null || result.isBlank()) {
            logger.error("Missing configuration for " + property);
        }
        return properties.getProperty(property);
    }
}