package org.benjaminschmitz.statusinformation.api;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.benjaminschmitz.statusinformation.ConfigurationUtil;
import org.jsoup.Jsoup;
import de.sematre.dsbmobile.DSBMobile;
import de.sematre.dsbmobile.DSBMobile.TimeTable;

public class SubstitutionPlanApi implements APIInterface {

	private final DSBMobile dsbMobile;

	public SubstitutionPlanApi() {
		dsbMobile = new DSBMobile(ConfigurationUtil.DSB_USERNAME, ConfigurationUtil.DSB_PASSWORD);
	}

	public String get() {
		List<String> URLs;
		try {
			URLs = getURLs();
		} catch (IndexOutOfBoundsException e) {
			return """
					SubstitutionPlanApi: Unexpected format of news feed of DSBMobile.
					""";
		}

		List<SubstitutionPlan> substitutionPlans = new LinkedList<>();

		try {
			for (String URL : URLs) {
				List<SubstitutionPlanEntry> substitutionPlanEntries = parseWebPage(URL);

				substitutionPlanEntries = substitutionPlanEntries.stream()
						.filter((t) -> t.getClasses().contains(ConfigurationUtil.SUBSTITUTIONPLAN_CLASS))
						.collect(Collectors.toList());

				substitutionPlans.add(new SubstitutionPlan(substitutionPlanEntries, getDate(URL)));
			}
		} catch (IOException | IndexOutOfBoundsException e) {
			return """
					SubstitutionPlanApi: Unable to parse substitution plan web page.
					""";
		}

		substitutionPlans = substitutionPlans.stream().filter(t -> t.isToday()).collect(Collectors.toList());

		try {
			StringBuilder result = new StringBuilder();

			List<SubstitutionPlanEntry> substitutionPlanEntries = substitutionPlans.get(0).getSubstitutionPlanEntries();

			for (SubstitutionPlanEntry dsbSubstitutionPlanEntry : substitutionPlanEntries) {
				result.append(generateMessage(dsbSubstitutionPlanEntry));
			}

			return result.toString();
		} catch (IndexOutOfBoundsException e) {
			return """
					SubstitutionPlanApi: No substitution plan for today.
					""";
		}
	}

	public static String getDate(String URL) {
		String HTML = getHTML(URL);

		HTML = HTML.substring(HTML.indexOf("<div class=\"mon_title\">"));
		HTML = HTML.substring(HTML.indexOf(">") + 1);
		HTML = HTML.substring(0, HTML.indexOf(" "));

		// Adding zeros
		HTML = formatDay(HTML);
		HTML = formatMonth(HTML);

		return HTML;
	}

	public static String formatDay(String string) {
		String day = string.substring(0, string.indexOf("."));
		String rest = string.substring(string.indexOf("."));

		if (day.length() == 1) {
			day = "0" + day;
			string = day + rest;
		}

		return string;
	}

	public static String formatMonth(String string) {
		String month = string.substring(string.indexOf(".") + 1);
		month = month.substring(0, month.indexOf("."));

		String newMonth;
		if (month.length() == 1) {
			newMonth = "0" + month;
			string = string.replace("." + month + ".", "." + newMonth + ".");
		}

		return string;
	}

	/*
	 * https://stackoverflow.com/questions/31462/how-to-fetch-html-in-java
	 */
	public static String getHTML(String URL) {
		String content = null;
		URLConnection connection = null;
		try {
			connection = new URL(URL).openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\Z");
			content = scanner.next();
			scanner.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return content;
	}

	public String generateMessage(SubstitutionPlanEntry dsbSubstitutionPlanEntry) {
		String result = ConfigurationUtil.SUBSTITUTIONPLAN_FORMAT;

		result = result.replace("[CLASSES]", dsbSubstitutionPlanEntry.getClasses());
		result = result.replace("[HOUR]", dsbSubstitutionPlanEntry.getHour());
		result = result.replace("[TEACHER]", dsbSubstitutionPlanEntry.getTeacher());
		result = result.replace("[SUBJECT]", dsbSubstitutionPlanEntry.getSubject());
		result = result.replace("[ROOM]", dsbSubstitutionPlanEntry.getRoom());
		result = result.replace("[TEXT]", dsbSubstitutionPlanEntry.getText());
		result = result.replace("[TEXT2]", dsbSubstitutionPlanEntry.getText2());

		return result;
	}

	public List<SubstitutionPlanEntry> parseWebPage(String URL) throws IOException {
		org.jsoup.nodes.Document doc = Jsoup.connect(URL).get();

		org.jsoup.select.Elements rows = doc.select("tr");

		List<SubstitutionPlanEntry> result = new LinkedList<>();

		for (org.jsoup.nodes.Element row : rows) {
			org.jsoup.select.Elements columns = row.select("td");

			if (!columns.isEmpty() && !columns.get(0).text().isEmpty()
					&& Character.isDigit(columns.get(0).text().charAt(0))) {
				result.add(new SubstitutionPlanEntry(columns));
			}
		}

		return result;
	}

	public List<String> getURLs() {
		List<String> result = new LinkedList<>();

		ArrayList<TimeTable> timeTables = dsbMobile.getTimeTables();

		for (TimeTable timeTable : timeTables) {
			result.add(timeTable.getDetail());
		}
		return result;
	}
}

/*
 * https://stackoverflow.com/questions/59229084/how-to-check-string-date-is-in-
 * today
 */
class SubstitutionPlan {

	private final List<SubstitutionPlanEntry> substitutionPlanEntries;
	private final LocalDate date;

	public SubstitutionPlan(List<SubstitutionPlanEntry> substitutionPlanEntries, LocalDate date) {
		this.substitutionPlanEntries = substitutionPlanEntries;
		this.date = date;
	}

	public SubstitutionPlan(List<SubstitutionPlanEntry> substitutionPlanEntries, String date) {
		this(substitutionPlanEntries, LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
	}

	public List<SubstitutionPlanEntry> getSubstitutionPlanEntries() {
		return substitutionPlanEntries;
	}

	public LocalDate getDate() {
		return date;
	}

	public boolean isToday() {
		return date.equals(LocalDate.now());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubstitutionPlan other = (SubstitutionPlan) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (substitutionPlanEntries == null) {
			if (other.substitutionPlanEntries != null)
				return false;
		} else if (!substitutionPlanEntries.equals(other.substitutionPlanEntries))
			return false;
		return true;
	}
}

class SubstitutionPlanEntry {

	private final String classes;
	private final String hour;
	private final String teacher;
	private final String subject;
	private final String room;
	private final String text;
	private final String text2;

	public SubstitutionPlanEntry(org.jsoup.select.Elements columns) {
		this.classes = columns.get(0).text();
		this.hour = columns.get(1).text();
		this.teacher = columns.get(2).text();
		this.subject = columns.get(3).text();
		this.room = columns.get(4).text();
		this.text = columns.get(5).text();
		this.text2 = columns.get(6).text();
	}

	public String getClasses() {
		return classes;
	}

	public String getHour() {
		return hour;
	}

	public String getTeacher() {
		return teacher;
	}

	public String getSubject() {
		return subject;
	}

	public String getRoom() {
		return room;
	}

	public String getText() {
		return text;
	}

	public String getText2() {
		return text2;
	}

	public String toString() {
		return getClasses();
	}
}
