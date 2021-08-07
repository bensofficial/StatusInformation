package org.benjaminschmitz.statusinformation;

public final class ConfigurationUtil {
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
			Gruppe: `[PUPILGROUP]`
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
