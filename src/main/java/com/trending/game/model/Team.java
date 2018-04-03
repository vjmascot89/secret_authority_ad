package com.trending.game.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.trending.game.enums.GameResult;

@Entity
@Table(name = "Team")
public class Team {
	private String teamName;
	private GameResult status;
	private Match match;
	private Integer id;
	private String ratio;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Enumerated(EnumType.STRING)
	public GameResult getStatus() {
		return status;
	}

	public void setStatus(GameResult status) {
		this.status = status;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public String getRatio() {
		return this.ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;

	}
}
