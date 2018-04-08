package com.trending.game.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trending.game.enums.MatchStatus;

@Entity
@Table(name = "Match")
public class Match {

	Date date;
	List<Team> teams = new ArrayList<Team>();
	MatchStatus matchStatus;
	Integer id;
	Satteri satteri;

	public Match() {

	}

	public Match(Match currentMatch) {
		this.setDate(currentMatch.getDate());
		this.setMatchStatus(currentMatch.getMatchStatus());
		this.setId(currentMatch.getId());
	}

	@Id
	@GeneratedValue
	@Column(name = "matchId")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	@Enumerated(EnumType.STRING)
	public MatchStatus getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(MatchStatus matchStatus) {
		this.matchStatus = matchStatus;
	}

	@JsonIgnore
	@OneToOne
	public Satteri getSatteri() {
		return satteri;
	}

	public void setSatteri(Satteri satteri) {
		this.satteri = satteri;
	}
}
