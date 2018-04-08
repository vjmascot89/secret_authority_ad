package com.trending.game.model;

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

@Entity
@Table(name = "Satteri")
public class Satteri {
	private Integer id;
	private BigDecimal balancePool;
	private BigDecimal totalBalanceOnTeamOneWin;
	private BigDecimal totalBalanceOnTeamOneLoss;
	private BigDecimal totalBalanceOnTeamTwoWin;
	private BigDecimal totalBalanceOnTeamTwoLoss;
	private BigDecimal finalAmount;
	private String name;
	private Match currentMatch;
	private List<SattaPlayer> sattaPlayer = new ArrayList<SattaPlayer>();

	public Satteri() {
		this.setBalancePool(BigDecimal.ZERO);
		this.setTotalBalanceOnTeamOneWin(BigDecimal.ZERO);
		this.setTotalBalanceOnTeamTwoWin(BigDecimal.ZERO);
		this.setTotalBalanceOnTeamOneLoss(BigDecimal.ZERO);
		this.setTotalBalanceOnTeamTwoLoss(BigDecimal.ZERO);
		this.setFinalAmount(BigDecimal.ZERO);
	}

	public Satteri(Satteri satteri) {
		this.setId(satteri.getId());
		this.setBalancePool(satteri.getBalancePool());
		this.setTotalBalanceOnTeamOneWin(satteri.getTotalBalanceOnTeamOneWin());
		this.setTotalBalanceOnTeamTwoWin(satteri.getTotalBalanceOnTeamTwoWin());
		this.setTotalBalanceOnTeamOneLoss(satteri.getTotalBalanceOnTeamOneLoss());
		this.setTotalBalanceOnTeamTwoLoss(satteri.getTotalBalanceOnTeamTwoLoss());
		this.setFinalAmount(satteri.getFinalAmount());
		this.setName(satteri.getName());
	}

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

	@OneToOne(mappedBy = "satteri", cascade = CascadeType.ALL)
	public Match getCurrentMatch() {
		return currentMatch;
	}

	public void setCurrentMatch(Match currentMatch) {
		this.currentMatch = currentMatch;
	}

	@OneToMany(mappedBy = "satteri", cascade = CascadeType.MERGE)
	public List<SattaPlayer> getSattaPlayer() {
		return sattaPlayer;
	}

	public void setSattaPlayer(List<SattaPlayer> sattaPlayer) {
		this.sattaPlayer = sattaPlayer;
	}

	public BigDecimal getTotalBalanceOnTeamOneLoss() {
		return totalBalanceOnTeamOneLoss;
	}

	public void setTotalBalanceOnTeamOneLoss(BigDecimal totalBalanceOnTeamOneLoss) {
		this.totalBalanceOnTeamOneLoss = totalBalanceOnTeamOneLoss;
	}

	public BigDecimal getTotalBalanceOnTeamTwoLoss() {
		return totalBalanceOnTeamTwoLoss;
	}

	public void setTotalBalanceOnTeamTwoLoss(BigDecimal totalBalanceOnTeamTwoLoss) {
		this.totalBalanceOnTeamTwoLoss = totalBalanceOnTeamTwoLoss;
	}

	public BigDecimal getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(BigDecimal finalAmount) {
		this.finalAmount = finalAmount;
	}

}
