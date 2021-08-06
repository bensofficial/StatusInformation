package org.benjaminschmitz.statusinformation.api;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.benjaminschmitz.statusinformation.Configuration;
import org.jsoup.Jsoup;
import de.sematre.dsbmobile.DSBMobile;
import de.sematre.dsbmobile.DSBMobile.TimeTable;

public class SubstitutionPlanApi implements APIInterface {
  private final DSBMobile dsbMobile;

  public SubstitutionPlanApi() {
    dsbMobile = new DSBMobile(Configuration.DSB_USERNAME, Configuration.DSB_PASSWORD);
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
						.filter((t) -> t.getClasses().contains(Configuration.SUBSTITUTIONPLAN_CLASS))
						.collect(Collectors.toList());

				substitutionPlans.add(new SubstitutionPlan(substitutionPlanEntries, getDate(URL)));
			}
		} catch (RuntimeException e) {
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

  public String getDate(String URL) {
    String HTML = getHTML(URL);

    HTML = HTML.substring(HTML.indexOf("<div class=\"mon_title\">"));
    HTML = HTML.substring(HTML.indexOf(">"));
    HTML = HTML.substring(0, HTML.indexOf(" "));

    return HTML;
  }

  /*
   * https://stackoverflow.com/questions/31462/how-to-fetch-html-in-java
   */
  public String getHTML(String URL) {
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

  public List<SubstitutionPlanEntry> parseWebPage(String URL) {
    org.jsoup.nodes.Document doc;
    try {
      doc = Jsoup.connect(URL).get();
    } catch (IOException e) {
      throw new RuntimeException();
    }
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
 * https://stackoverflow.com/questions/59229084/how-to-check-string-date-is-in- today
 */
class SubstitutionPlan {
  private final List<SubstitutionPlanEntry> substitutionPlanEntries;
  private final LocalDate date;

  public SubstitutionPlan(List<SubstitutionPlanEntry> substitutionPlanEntries, LocalDate date) {
    this.substitutionPlanEntries = substitutionPlanEntries;
    this.date = date;
  }

  public SubstitutionPlan(List<SubstitutionPlanEntry> substitutionPlanEntries, String date) {
    this(substitutionPlanEntries,
        LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM-yyyy")).toLocalDate());
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
}


class SubstitutionPlanEntry {
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

  public SubstitutionPlanEntry(String classes, String pupilGroup, String hour, String newTeacher,
      String newSubject, String room, String type, String movedFrom, String oldTeacher,
      String oldSubject, String remark) {
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

  public SubstitutionPlanEntry(org.jsoup.select.Elements columns) {
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
