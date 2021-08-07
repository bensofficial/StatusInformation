package org.benjaminschmitz.statusinformation.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class APITests {
	@Test
	void testGetSubstitutionPlanApi() {
		API api = new API();
		SubstitutionPlanApi substitutionPlanApi = api.getSubstitutionPlanApi();

		assertNotNull(substitutionPlanApi);
	}
}
