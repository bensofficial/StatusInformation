package org.benjaminschmitz.statusinformation;

public final class ConfigurationUtil {
	public static String DSB_USERNAME = "";
	public static String DSB_PASSWORD = "";

	public static final String SUBSTITUTIONPLAN_CLASS = "11";

	/**
	 * https://de.wikipedia.org/wiki/ISO_3166-2:DE; https://ferien-api.de
	 */
	public static final String LOCATION = "BY";

	/**
	 * Possible wildcards: [CLASSES] [HOUR] [TEACHER] [SUBJECT] [ROOM] [TEXT]
	 * [TEXT2]
	 * 
	 * Styling: https://core.telegram.org/api/entities
	 */
	public static final String SUBSTITUTIONPLAN_FORMAT = """
			########
			Stunde: `[HOUR]`
			Lehrer: `[TEACHER]`
			Fach: `[SUBJECT]`
			Text: `[TEXT]`
			########
			""";

	/**
	 * Styling: https://core.telegram.org/api/entities
	 */
	public static final String HEADER = """
			*Vertretungsplan*
			""";

	/**
	 * Styling: https://core.telegram.org/api/entities
	 */
	public static final String FOOTER = "";

	public static String TELEGRAM_BOT_TOKEN = "";
	public static String TELEGRAM_CHANNEL_ID = "";

	private ConfigurationUtil() {
	}
}
