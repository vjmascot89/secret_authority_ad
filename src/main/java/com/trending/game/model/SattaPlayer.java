package com.trending.game.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="SattaPlayer")
public class SattaPlayer {
private Integer id;
private String sattaPlayerName;
private BigDecimal currentPotTeamOne;
private BigDecimal currentPotTeamTwo;
private BigDecimal currentPotRatioOnTeamOne;
private BigDecimal currentPotRatioOnTeamTwo;
private BigDecimal teamOneWinAmount;
private BigDecimal teamTwoWinAmount;
private Satteri satteri;
@Id
@GeneratedValue(strategy=GenerationType.SEQUENCE)
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getSattaPlayerName() {
	return sattaPlayerName;
}
public void setSattaPlayerName(String name) {
	this.sattaPlayerName = name;
}
public BigDecimal getCurrentPotTeamOne() {
	return currentPotTeamOne;
}
public void setCurrentPotTeamOne(BigDecimal currentPotTeamOne) {
	this.currentPotTeamOne = currentPotTeamOne;
}
public BigDecimal getCurrentPotTeamTwo() {
	return currentPotTeamTwo;
}
public void setCurrentPotTeamTwo(BigDecimal currentPotTeamTwo) {
	this.currentPotTeamTwo = currentPotTeamTwo;
}
public BigDecimal getCurrentPotRatioOnTeamOne() {
	return currentPotRatioOnTeamOne;
}
public void setCurrentPotRatioOnTeamOne(BigDecimal currentPotRatioOnTeamOne) {
	this.currentPotRatioOnTeamOne = currentPotRatioOnTeamOne;
}
public BigDecimal getCurrentPotRatioOnTeamTwo() {
	return currentPotRatioOnTeamTwo;
}
public void setCurrentPotRatioOnTeamTwo(BigDecimal currentPotRatioOnTeamTwo) {
	this.currentPotRatioOnTeamTwo = currentPotRatioOnTeamTwo;
}
public BigDecimal getTeamOneWinAmount() {
	return teamOneWinAmount;
}
public void setTeamOneWinAmount(BigDecimal teamOneWinAmount) {
	this.teamOneWinAmount = teamOneWinAmount;
}
public BigDecimal getTeamTwoWinAmount() {
	return teamTwoWinAmount;
}
public void setTeamTwoWinAmount(BigDecimal teamTwoWinAmount) {
	this.teamTwoWinAmount = teamTwoWinAmount;
}
@ManyToOne(cascade=CascadeType.ALL)
public Satteri getSatteri() {
	return satteri;
}
public void setSatteri(Satteri satteri) {
	this.satteri = satteri;
}

}
