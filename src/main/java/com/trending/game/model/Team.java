package com.trending.game.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.trending.game.enums.GameResult;
import com.trending.game.enums.TeamsName;
import com.trending.game.validator.IValidator;
import com.trending.game.validator.Ivalidatable;

@Embeddable
public class Team implements Ivalidatable{
	private TeamsName teamName;
	private GameResult status;
	private String ratio;
	private Integer order;
	public Team() {

	}

	public Team(Team team) {
		this.setTeamName(team.getTeamName());
		this.setStatus(team.getStatus());
//		this.setId(team.getId());
		this.setRatio(team.getRatio());
		this.setOrder(team.getOrder());
	}

	@Column(name="FirstTeamName")
	@Enumerated(EnumType.STRING)
	public TeamsName getTeamName() {
		return teamName;
	}

	public void setTeamName(TeamsName teamName) {
		this.teamName = teamName;
	}
	@Column(name="FirstStatus")
	@Enumerated(EnumType.STRING)
	public GameResult getStatus() {
		return status;
	}

	public void setStatus(GameResult status) {
		this.status = status;
	}
	@Column(name="FirstRatio")
	public String getRatio() {
		return this.ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;

	}
	@Column(name="FirstTeam")
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
	public String validate(IValidator validator) {
		// TODO Auto-generated method stub
		return validator.isValid(this);
	}
}
