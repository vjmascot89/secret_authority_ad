package com.trending.game.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="Match")
public class Match {

	Date date;
	List<Team> teams = new ArrayList<Team>();
	Match matchStatus;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@ManyToOne(cascade=CascadeType.ALL)
	public List<Team> getTeams() {
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	@Enumerated(EnumType.STRING)
	public Match getMatchStatus() {
		return matchStatus;
	}
	public void setMatchStatus(Match matchStatus) {
		this.matchStatus = matchStatus;
	}
}
