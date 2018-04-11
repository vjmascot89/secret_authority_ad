package com.trending.game.validator;

import org.springframework.http.HttpStatus;

public class FunctionResult implements IFunctionResult{
	
	private String errMsg;
	private HttpStatus status;
	private IFunctionResult functionResult;

	public FunctionResult(String errMsg , HttpStatus status) {
		this.errMsg = errMsg;
		this.status = status;
		// TODO Auto-generated constructor stub
	}
	public FunctionResult(IFunctionResult functionResult) {
		this.functionResult = functionResult;
	}
	@Override
	public String getErrorMsg() {
		// TODO Auto-generated method stub
		return errMsg;
	}

	@Override
	public HttpStatus getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

}
