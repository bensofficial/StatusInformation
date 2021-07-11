package org.benjaminschmitz.statusinformation;

public final class Configuration {

	public static String DSB_USERNAME = "";
	public static String DSB_PASSWORD = "";

	public static final String SUBSTITUTIONPLAN_CLASS = "10";

	/**
	 * https://de.wikipedia.org/wiki/ISO_3166-2:DE; https://ferien-api.de
	 */
	public static final String LOCATION = "BY";

	/**
	 * Possible wildcards: [CLASSES] [PUPILGROUP] [HOUR] [NEWTEACHER] [NEWSUBJECT]
	 * [ROOM] [TYPE] [MOVEDFROM] [OLDTEACHER] [OLDSUBJECT] [REMARK]
	 * 
	 * Styling: https://core.telegram.org/api/entities
	 */
	public static final String SUBSTITUTIONPLAN_FORMAT = """
			########
			Klasse: `[CLASSES]`
			Typ: `[TYPE]`
			Stunde: `[HOUR]`
			Neuer Lehrer: `[NEWTEACHER]`
			Neues Fach: `[NEWSUBJECT]`
			Bemerkung: `[REMARK]`
			########
			""";

	/**
	 * Styling: https://core.telegram.org/api/entities
	 */
	public static final String HEADER = """
			*News*
			""";

	/**
	 * Styling: https://core.telegram.org/api/entities
	 */
	public static final String FOOTER = """
			Powered by [StatusInformation](https://github.com/bensofficial/StatusInformation) v1.0.0
			""";

	public static String TELEGRAM_BOT_TOKEN = "";
	public static String TELEGRAM_CHANNEL_ID = "";

	private Configuration() {
	}
}
