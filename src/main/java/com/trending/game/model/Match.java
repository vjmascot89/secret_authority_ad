package com.trending.game.model;

import java.sql.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trending.game.enums.MatchStatus;

@Entity
@Table(name = "Match")
public class Match {

	Date date;
	MatchStatus matchStatus;
	Integer id;
	Satteri satteri;

	Team firstTeam;
	Team secondTeam;
	public Match() {

	}

	public Match(Match currentMatch) {
		this.setDate(currentMatch.getDate());
		this.setMatchStatus(currentMatch.getMatchStatus());
		this.setId(currentMatch.getId());
		this.setFirstTeam(currentMatch.getFirstTeam());
		this.setSecondTeam(currentMatch.getSecondTeam());
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

	@Enumerated(EnumType.STRING)
	public MatchStatus getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(MatchStatus matchStatus) {
		this.matchStatus = matchStatus;
	}

	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL)
	public Satteri getSatteri() {
		return satteri;
	}

	public void setSatteri(Satteri satteri) {
		this.satteri = satteri;
	}
	@Embedded
	public Team getFirstTeam() {
		return firstTeam;
	}

	public void setFirstTeam(Team firstTeam) {
		this.firstTeam = firstTeam;
	}
	
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "teamName", column = @Column(name = "SecondTeamName")),
			@AttributeOverride(name = "status", column = @Column(name = "SecondStatus")),
			@AttributeOverride(name = "ratio", column = @Column(name = "SecondRatio")),
			@AttributeOverride(name = "order", column = @Column(name = "SecondTeam")) })
	public Team getSecondTeam() {
		return secondTeam;
	}

	public void setSecondTeam(Team secondTeam) {
		this.secondTeam = secondTeam;
	}
}