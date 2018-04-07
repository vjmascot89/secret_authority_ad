package com.trending.game.services;

import java.math.BigDecimal;

public class AlgoToCalculateProfitLoss {

	public BigDecimal profitAndLossCalculation(String profitLossRatio , BigDecimal potValue){
		String[] split = profitLossRatio.split("k");
		Integer firstValue  = Integer.parseInt(split[0]);
		Integer secondValue  = Integer.parseInt(split[1]);
//		potValue.divide(firstValue);
		return null;
	}
	
}
