package com.trending.game.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Team")
public class Team {
private String teamName;
private TeamsStatus status;
private Match match;
private Integer id;
@Id
@GeneratedValue(strategy=GenerationType.SEQUENCE)
public Integer getId(){
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
public TeamsStatus getStatus() {
	return status;
}
public void setStatus(TeamsStatus status) {
	this.status = status;
}
@OneToMany(mappedBy="teams")
public Match getMatch() {
	return match;
}
public void setMatch(Match match) {
	this.match = match;
}
}
