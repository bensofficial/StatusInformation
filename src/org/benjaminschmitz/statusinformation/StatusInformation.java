package org.benjaminschmitz.statusinformation;

import org.benjaminschmitz.statusinformation.api.API;
import org.benjaminschmitz.statusinformation.senders.Senders;

public class StatusInformation {

	public static void main(String[] args) {
		API api = new API();
		Senders senders = new Senders();

		String message = api.getSubstitutionPlan();

		message = Configuration.HEADER + message + Configuration.FOOTER;

		System.out.println(message);
		senders.sendToAll(message);
	}
}
