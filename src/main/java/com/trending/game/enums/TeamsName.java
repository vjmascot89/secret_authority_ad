package com.trending.game.enums;

public enum TeamsName {
	CSK(1, "Chennai Super Kings"), DD(2, "Delhi DareDevils"), KKR(3, "Kolkata knight Riders"), KXIP(4,
			"Kings XI Punjab"), MI(5, "Mumbai Indians"), RCB(6, "Royal Challenger Bangalore"), RR(7,
					"Rajasthan Royals"), SRH(8,
							"Sunrisers Hyderabad"), TEAM_ONE(9, "Team One"), TEAM_TWO(10, "Team Two");
	private Integer number;
	private String teamName;

	TeamsName(Integer number, String teamName) {
		this.number = number;
		this.teamName = teamName;

	}

	public static TeamsName valueOfDisplayString(String teamName) {
		for (TeamsName t : values()) {
			if (t.teamName.equals(teamName)) {
				return t;
			}

		}
		return null;
	}

	public static TeamsName valueOf(Integer teamName) {
		for (TeamsName t : values()) {
			if (t.number == teamName) {
				return t;
			}

		}
		return null;
	}
}
