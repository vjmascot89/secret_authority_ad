package com.trending.game.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trending.game.enums.GameResult;
import com.trending.game.enums.TeamsName;

@Entity
@Table(name = "Team")
public class Team {
	private TeamsName teamName;
	private GameResult status;
	private Integer id;
	private String ratio;
	private Match match;
	private Integer order;
	public Team() {

	}

	public Team(Team team) {
		this.setTeamName(team.getTeamName());
		this.setStatus(team.getStatus());
		this.setId(team.getId());
		this.setRatio(team.getRatio());
		this.setOrder(team.getOrder());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	public TeamsName getTeamName() {
		return teamName;
	}

	public void setTeamName(TeamsName teamName) {
		this.teamName = teamName;
	}

	@Enumerated(EnumType.STRING)
	public GameResult getStatus() {
		return status;
	}

	public void setStatus(GameResult status) {
		this.status = status;
	}

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "matchId")
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
	@Column(name="TeamOrder")
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}
