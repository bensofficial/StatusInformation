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