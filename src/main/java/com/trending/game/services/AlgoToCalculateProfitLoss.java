package com.trending.game.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class AlgoToCalculateProfitLoss {
	public BigDecimal profitCalculation(String profitLossRatio, BigDecimal potValue, Integer satteriId) {
		synchronized (satteriId.toString()) {
			if (potValue.compareTo(new BigDecimal(0)) != 0) {
				String[] split = null;
				Integer firstMultiplier = 10000;
				Integer SecondMultiplier = 1000;
				String lowerProfitLossRatio = profitLossRatio.toLowerCase();
				if (lowerProfitLossRatio.contains("k")) {
					split = lowerProfitLossRatio.split("k");
					firstMultiplier = 1000;
					SecondMultiplier = 10000;
				} else if (lowerProfitLossRatio.contains("l")) {
					split = lowerProfitLossRatio.split("l");
				}
				System.out.println(split[0]);
				Integer firstValue = Integer.parseInt(split[0]);
				Integer secondValue = Integer.parseInt(split[1]);
				return potValue.multiply(new BigDecimal(secondValue * SecondMultiplier))
						.divide(new BigDecimal(firstValue * firstMultiplier));
			} else {
				return BigDecimal.ZERO;
			}
		}
	}

	public BigDecimal lossCalculation(BigDecimal potValue, Integer satteriId) {
		synchronized (satteriId.toString()) {
			return potValue;
		}
	}
}
