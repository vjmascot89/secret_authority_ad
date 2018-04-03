package com.trending.game.enums;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.trending.game.model.Match;
import com.trending.game.model.SattaPlayer;

@Entity
@Table(name = "Satteri")
public class Satteri {
	Integer id;
	BigDecimal balancePool;
	BigDecimal totalBalanceOnTeamOneWin;
	BigDecimal totalBalanceOnTeamTwoWin;
	String name;
	Match currentMatch;
	List<SattaPlayer> sattaPlayer = new ArrayList<SattaPlayer>();

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getBalancePool() {
		return balancePool;
	}

	public void setBalancePool(BigDecimal balancePool) {
		this.balancePool = balancePool;
	}

	public BigDecimal getTotalBalanceOnTeamOneWin() {
		return totalBalanceOnTeamOneWin;
	}

	public void setTotalBalanceOnTeamOneWin(BigDecimal totalBalanceOnTeamOneWin) {
		this.totalBalanceOnTeamOneWin = totalBalanceOnTeamOneWin;
	}

	public BigDecimal getTotalBalanceOnTeamTwoWin() {
		return totalBalanceOnTeamTwoWin;
	}

	public void setTotalBalanceOnTeamTwoWin(BigDecimal totalBalanceOnTeamTwoWin) {
		this.totalBalanceOnTeamTwoWin = totalBalanceOnTeamTwoWin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@OneToOne(mappedBy="satteri")
	public Match getCurrentMatch() {
		return currentMatch;
	}

	public void setCurrentMatch(Match currentMatch) {
		this.currentMatch = currentMatch;
	}
    @OneToMany(mappedBy="satteri", cascade=CascadeType.ALL)
	public List<SattaPlayer> getSattaPlayer() {
		return sattaPlayer;
	}

	public void setSattaPlayer(List<SattaPlayer> sattaPlayer) {
		this.sattaPlayer = sattaPlayer;
	}

}
