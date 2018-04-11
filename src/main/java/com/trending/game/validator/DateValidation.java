package com.trending.game.validator;

import java.sql.Date;

import org.springframework.http.HttpStatus;

public class DateValidation implements IValidation {

	private Date date;

	public DateValidation(Date date) {
		this.date = date;
	}

	@Override
	public IFunctionResult isValid(IFunctionResult functionResult) {
		return (date != null) ? functionResult
				: (new FunctionResult(functionResult.getErrorMsg().concat("  Please provide date"),
						HttpStatus.BAD_REQUEST));
	}

}
