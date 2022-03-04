package org.benjaminschmitz.statusinformation.api;

import org.benjaminschmitz.statusinformation.configuration.Configuration;

@FunctionalInterface
public interface APIInterface {
	String get(Configuration configuration);
}
