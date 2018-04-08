package com.trending.game.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class AlgoToCalculateProfitLoss {
	public BigDecimal profitCalculation(String profitLossRatio, BigDecimal potValue) {
		
			if (potValue.compareTo(new BigDecimal(0)) != 0) {
				String[] split = null;
				String lowerProfitLossRatio = profitLossRatio.toLowerCase();
				if (lowerProfitLossRatio.contains("k")) {
					split = lowerProfitLossRatio.split("k");
				} else if (lowerProfitLossRatio.contains("l")) {
					split = lowerProfitLossRatio.split("l");
				}
				System.out.println(split[0]);
				Integer firstValue = Integer.parseInt(split[0]);
				while (firstValue<1000){
					firstValue=firstValue*10;
				}
				return potValue.multiply(new BigDecimal(firstValue))
						.divide(new BigDecimal(10000));
			} else {
				return BigDecimal.ZERO;
			}
	}

}
