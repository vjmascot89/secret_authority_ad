package com.trending.game.model;

public enum Teams {
	CSK(1, "Chennai Super Kings"), DD(2, "Delhi DareDevils"), KKR(3, "Kolkata knight Riders"), KXIP(4,
			"Kings XI Punjab"), MI(5, "Mumbai Indians"), RCB(6, "Royal Challenger Bangalore"), RR(7,
					"Rajasthan Royals"), SRH(8,
							"Sunrisers Hyderabad"), TEAM_ONE(9, "Team One"), TEAM_TWO(10, "Team Two");
	private Integer number;
	private String teamName;

	Teams(Integer number, String teamName) {
		this.number = number;
		this.teamName = teamName;

	}

	public static Teams valueOfDisplayString(String teamName) {
		for (Teams t : values()) {
			if (t.teamName.equals(teamName)) {
				return t;
			}

		}
		return null;
	}

	public static Teams valueOf(Integer teamName) {
		for (Teams t : values()) {
			if (t.number == teamName) {
				return t;
			}

		}
		return null;
	}
}
