package org.benjaminschmitz.statusinformation.api;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

class SubstitutionPlanTest {

	@Test
	void testConstructors() {
		String stringOfDate = "01.01.2020";
		LocalDate date = LocalDate.parse(stringOfDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

		SubstitutionPlan s1 = new SubstitutionPlan(null, date);
		SubstitutionPlan s2 = new SubstitutionPlan(null, stringOfDate);

		assertEquals(s1, s2);
	}

	@Test
	void testGetters() {
		LocalDate expectedDate = LocalDate.now().minusDays(5);
		LinkedList<SubstitutionPlanEntry> expectedList = new LinkedList<SubstitutionPlanEntry>();
		expectedList.add(null);

		SubstitutionPlan substitutionPlan = new SubstitutionPlan(expectedList, expectedDate);

		assertEquals(expectedDate, substitutionPlan.getDate());
		assertEquals(expectedList, substitutionPlan.getSubstitutionPlanEntries());
	}

	@Test
	void testIsToday() {
		LocalDate date1 = LocalDate.now().minusDays(5);
		LocalDate date2 = LocalDate.now();

		SubstitutionPlan s1 = new SubstitutionPlan(null, date1);
		SubstitutionPlan s2 = new SubstitutionPlan(null, date2);

		assertFalse(s1.isToday());
		assertTrue(s2.isToday());
	}
}
