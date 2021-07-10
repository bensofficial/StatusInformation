package org.benjaminschmitz.statusinformation.api;

public class API {
	private final DSBApi dsbAPI;

	public API() {
		dsbAPI = new DSBApi();
	}

	public String getSubstitutionPlan() {
		return dsbAPI.get();
	}
}
