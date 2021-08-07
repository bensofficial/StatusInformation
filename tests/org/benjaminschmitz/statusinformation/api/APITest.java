package org.benjaminschmitz.statusinformation.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class APITest {

	@Test
	void testGetSubstitutionPlanApi() {
		API api = new API();
		SubstitutionPlanApi substitutionPlanApi = api.getSubstitutionPlanApi();

		assertNotNull(substitutionPlanApi);
	}

}
