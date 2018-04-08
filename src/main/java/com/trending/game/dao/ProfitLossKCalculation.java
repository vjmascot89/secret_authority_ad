package com.trending.game.dao;

import java.math.BigDecimal;

import com.trending.game.model.SattaPlayer;
import com.trending.game.model.Satteri;
import com.trending.game.services.AlgoToCalculateProfitLoss;

public class ProfitLossKCalculation {

	private AlgoToCalculateProfitLoss algoToCalculationProfitLoss;

	public ProfitLossKCalculation(AlgoToCalculateProfitLoss algoToCalculationProfitLoss) {
		this.algoToCalculationProfitLoss = algoToCalculationProfitLoss;
	}
	public BigDecimal adjustBalanceOnWin(String currentPotRatio, BigDecimal currentPot) {
		if(currentPotRatio.toLowerCase().contains("k")){
		return currentPot;
		}else{
			return algoToCalculationProfitLoss.profitCalculation(currentPotRatio, currentPot);
		}
	}

	public BigDecimal adjustBalanceOnLoss(String currentPotRatio, BigDecimal currentPot) {
		if(currentPotRatio.toLowerCase().contains("k")){
			return (algoToCalculationProfitLoss.profitCalculation( currentPotRatio, currentPot)).multiply(new BigDecimal(-1));
			}else{
				return currentPot.multiply(new BigDecimal(-1));
			}
	}
	public BigDecimal calculateTotalAmountWonOrLost(BigDecimal ... bigDecimal) {
		BigDecimal localSum=BigDecimal.ZERO;
for(BigDecimal currentValue:bigDecimal)	{
	localSum.add(currentValue);
}
return localSum;
	}
}
