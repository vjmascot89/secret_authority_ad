package com.trending.game.validator;

import org.springframework.http.HttpStatus;

public interface IFunctionResult {

	public String getErrorMsg();
	public HttpStatus getStatus();
}
