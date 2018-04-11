package com.trending.game.validator;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class NameValidation implements IValidation {
	private String name;

	public NameValidation(String name) {
		this.name = name;

	}

	@Override
	public IFunctionResult isValid(IFunctionResult functionResult) {
		return StringUtils.isEmpty(name)
				? new FunctionResult(functionResult.getErrorMsg().concat("SatteriName: Empty or Null"),
						HttpStatus.BAD_REQUEST)
				: functionResult;
	}

}
