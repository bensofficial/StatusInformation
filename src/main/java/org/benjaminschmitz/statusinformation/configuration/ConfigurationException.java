package org.benjaminschmitz.statusinformation.configuration;

public class ConfigurationException extends RuntimeException {
    public ConfigurationException() {
        super();
    }

    public ConfigurationException(String message) {
        super(message);
    }
}
