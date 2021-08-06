package org.benjaminschmitz.statusinformation;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.json.*;
import org.benjaminschmitz.statusinformation.api.API;
import org.benjaminschmitz.statusinformation.senders.Senders;

public class StatusInformation {

	public static void main(String[] args) {
		if (args.length == 4) {
			Configuration.DSB_USERNAME = args[0];
			Configuration.DSB_PASSWORD = args[1];
			Configuration.TELEGRAM_CHANNEL_ID = args[2];
			Configuration.TELEGRAM_BOT_TOKEN = args[3];
		}

		if (!isWeekend(Calendar.getInstance()) && !isHoliday(new Date())) {
			API api = new API();
			Senders senders = new Senders();

			String message = api.getSubstitutionPlan();

			if (message.isEmpty()) {
				message = """
						No substitutions for today.
						""";
			}

			message = Configuration.HEADER + message + Configuration.FOOTER;

			System.out.println(message);
			senders.sendToAll(message);
		} else {
			System.out.println("Weekend or Holiday");
		}
	}

	static boolean isWeekend(Calendar today) {
		return today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

	/**
	 * https://stackoverflow.com/questions/31993153/java-split-string-on-comma-except-when-between-parenthesis
	 * 
	 * https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java
	 */
	static boolean isHoliday(Date day) {
		String URL = "https://ferien-api.de/api/v1/holidays/" + Configuration.LOCATION;
		String JSON;
		try {
			JSON = get(URL);
		} catch (IOException e) {
			return false;
		}

		// Remove [ and ] at the first and last position
		JSON = JSON.replace("[", "");
		JSON = JSON.replace("]", "");

		JSON = JSON.replace("},", "};");

		long size = Arrays.stream(JSON.split(";")).map((t) -> {
			JSONObject obj = new JSONObject(t);
			String start = obj.getString("start");
			String end = obj.getString("end");
			return new Holidays(start, end);
		}).filter((t) -> {
			return t.getStart().before(day);
		}).filter((t) -> {
			return t.getEnd().after(day);
		}).count();

		return size >= 1;
	}

	/**
	 * https://stackoverflow.com/questions/31462/how-to-fetch-html-in-java
	 * 
	 * @throws IOException
	 */
	public static String get(String URL) throws IOException {
		String content = null;
		URLConnection connection = null;
		try {
			connection = new URL(URL).openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\Z");
			content = scanner.next();
			scanner.close();
		} catch (IOException | NoSuchElementException | IllegalStateException e) {
			throw new IOException();
		}

		return content;
	}
}

class Holidays {
	private final Date start;
	private final Date end;

	public Holidays(Date start, Date end) {
		if (end.before(start)) {
			throw new IllegalArgumentException();
		}

		this.start = start;
		this.end = end;
	}

	public Holidays(String start, String end) {
		this(parseString(start), parseString(end));
	}

	public static Date parseString(String date) {
		// Ading the seconds
		return Date.from(Instant.parse(date.replace("Z", ":00Z")));
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	@Override
	public String toString() {
		return "Holiday from " + start + " to " + end;
	}
}
