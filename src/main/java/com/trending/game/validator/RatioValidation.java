package com.trending.game.validator;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class RatioValidation implements IValidation {

	private String ratioOne;
	private String ratioTwo;

	public RatioValidation(String ratio, String ratio2) {
		this.ratioOne = ratio;
		this.ratioTwo = ratio2;

	}

	@Override
	public IFunctionResult isValid(IFunctionResult functionResult) {

		if (StringUtils.isEmpty(ratioOne) || StringUtils.isEmpty(ratioTwo) || !ValidRatio(ratioOne)
				|| !ValidRatio(ratioTwo)) {
			return new FunctionResult(
					functionResult.getErrorMsg().concat("Ratio cannot be blank or Incorrect Correct the patter"),
					HttpStatus.BAD_REQUEST);
		} else {
			return functionResult;
		}

	}

	private boolean ValidRatio(String Ratio) {
		boolean isValid = true;
		String[] split = null;
		String lowerProfitLossRatio = ratioOne.toLowerCase();
		if (lowerProfitLossRatio.contains("k")) {
			split = lowerProfitLossRatio.split("k");
		} else if (lowerProfitLossRatio.contains("l")) {
			split = lowerProfitLossRatio.split("l");
		} else {
			isValid = false;
		}
		Integer firstValue = Integer.parseInt(split[0]);
		if (firstValue <= 0) {
			isValid = false;
		}
		return isValid;
	}

}
