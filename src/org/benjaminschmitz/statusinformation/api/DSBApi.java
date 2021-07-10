package org.benjaminschmitz.statusinformation.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.benjaminschmitz.statusinformation.Configuration;
import org.jsoup.Jsoup;

import de.sematre.dsbmobile.DSBMobile;
import de.sematre.dsbmobile.DSBMobile.TimeTable;

public class DSBApi implements APIInterface {
	private final DSBMobile dsbMobile;

	public DSBApi() {
		dsbMobile = new DSBMobile(Configuration.DSB_USERNAME, Configuration.DSB_PASSWORD);
	}

	public String get() {
		StringBuilder result = new StringBuilder();

		String URL;
		try {
			URL = getCurrentURL();
		} catch (IndexOutOfBoundsException e) {
			return """
					DSBApi: Unexpected format of news feed of DSBMobile.
					""";
		}

		List<DSBSubstitutionPlanEntry> substitutionPlanEntries;

		try {
			substitutionPlanEntries = parseWebPage(URL);
		} catch (RuntimeException e) {
			return """
					DSBApi: Unable to parse substitution plan web page.
					""";
		}

		substitutionPlanEntries = substitutionPlanEntries.stream()
				.filter((t) -> t.getClasses().contains(Configuration.SUBSTITUTIONPLAN_CLASS))
				.collect(Collectors.toList());

		for (DSBSubstitutionPlanEntry dsbSubstitutionPlanEntry : substitutionPlanEntries) {
			result.append(generateMessage(dsbSubstitutionPlanEntry));
		}

		return result.toString();
	}

	String generateMessage(DSBSubstitutionPlanEntry dsbSubstitutionPlanEntry) {
		String result = Configuration.SUBSTITUTIONPLAN_FORMAT;

		result = result.replace("[CLASSES]", dsbSubstitutionPlanEntry.getClasses());
		result = result.replace("[PUPILGROUP]", dsbSubstitutionPlanEntry.getPupilGroup());
		result = result.replace("[HOUR]", dsbSubstitutionPlanEntry.getHour());
		result = result.replace("[NEWTEACHER]", dsbSubstitutionPlanEntry.getNewTeacher());
		result = result.replace("[NEWSUBJECT]", dsbSubstitutionPlanEntry.getNewSubject());
		result = result.replace("[ROOM]", dsbSubstitutionPlanEntry.getRoom());
		result = result.replace("[TYPE]", dsbSubstitutionPlanEntry.getType());
		result = result.replace("[MOVEDFROM]", dsbSubstitutionPlanEntry.getMovedFrom());
		result = result.replace("[OLDTEACHER]", dsbSubstitutionPlanEntry.getNewTeacher());
		result = result.replace("[OLDSUBJECT]", dsbSubstitutionPlanEntry.getNewSubject());
		result = result.replace("[REMARK]", dsbSubstitutionPlanEntry.getRemark());

		return result;
	}

	List<DSBSubstitutionPlanEntry> parseWebPage(String URL) {
		org.jsoup.nodes.Document doc;
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			throw new RuntimeException();
		}
		org.jsoup.select.Elements rows = doc.select("tr");

		List<DSBSubstitutionPlanEntry> result = new LinkedList<>();

		for (org.jsoup.nodes.Element row : rows) {
			org.jsoup.select.Elements columns = row.select("td");

			if (!columns.isEmpty() && !columns.get(0).text().isEmpty()
					&& Character.isDigit(columns.get(0).text().charAt(0))) {
				result.add(new DSBSubstitutionPlanEntry(columns));
			}
		}

		return result;

	}

	String getCurrentURL() {
		ArrayList<TimeTable> timeTables = dsbMobile.getTimeTables();

		// Latest timetable (from today) is the second element
		TimeTable timeTable = timeTables.get(1);

		return timeTable.getDetail();
	}
}

class DSBSubstitutionPlanEntry {
	private final String classes;
	private final String pupilGroup;
	private final String hour;
	private final String newTeacher;
	private final String newSubject;
	private final String room;
	private final String type;
	private final String movedFrom;
	private final String oldTeacher;
	private final String oldSubject;
	private final String remark;

	public DSBSubstitutionPlanEntry(String classes, String pupilGroup, String hour, String newTeacher,
			String newSubject, String room, String type, String movedFrom, String oldTeacher, String oldSubject,
			String remark) {
		this.classes = classes;
		this.pupilGroup = pupilGroup;
		this.hour = hour;
		this.newTeacher = newTeacher;
		this.newSubject = newSubject;
		this.room = room;
		this.type = type;
		this.movedFrom = movedFrom;
		this.oldTeacher = oldTeacher;
		this.oldSubject = oldSubject;
		this.remark = remark;
	}

	public DSBSubstitutionPlanEntry(org.jsoup.select.Elements columns) {
		this(columns.get(0).text(), columns.get(1).text(), columns.get(2).text(), columns.get(3).text(),
				columns.get(4).text(), columns.get(5).text(), columns.get(6).text(), columns.get(7).text(),
				columns.get(8).text(), columns.get(9).text(), columns.get(10).text());
	}

	public String getClasses() {
		return classes;
	}

	public String getPupilGroup() {
		return pupilGroup;
	}

	public String getHour() {
		return hour;
	}

	public String getNewTeacher() {
		return newTeacher;
	}

	public String getNewSubject() {
		return newSubject;
	}

	public String getRoom() {
		return room;
	}

	public String getType() {
		return type;
	}

	public String getMovedFrom() {
		return movedFrom;
	}

	public String getOldTeacher() {
		return oldTeacher;
	}

	public String getOldSubject() {
		return oldSubject;
	}

	public String getRemark() {
		return remark;
	}

	public String toString() {
		return getClasses() + "   " + getRemark();
	}

}
