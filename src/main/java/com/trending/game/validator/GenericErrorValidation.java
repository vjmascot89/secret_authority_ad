package com.trending.game.validator;

import org.springframework.http.HttpStatus;

public class GenericErrorValidation implements IValidation {

	@Override
	public IFunctionResult isValid(IFunctionResult functionResult) {
		// TODO Auto-generated method stub
		return new FunctionResult(functionResult.getErrorMsg().concat("Something is null generic error"), HttpStatus.BAD_REQUEST);
	}

	
	

}