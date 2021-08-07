package org.benjaminschmitz.statusinformation.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Test {

	public static void main(String[] args) {
		String str = "01.01.2020";

		System.out.println(LocalDate.parse(str, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
	}

}
