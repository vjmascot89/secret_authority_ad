package com.trending.game.validator;

import org.springframework.http.HttpStatus;

import com.trending.game.enums.TeamsName;

public class TeamsValidation implements IValidation{
	private TeamsName team1;
	private TeamsName team2;
	public TeamsValidation(TeamsName team1,TeamsName team2) {
		this.team1 = team1;
		this.team2 = team2;
		
	}
	@Override
	public IFunctionResult isValid(IFunctionResult functionResult) {
		// TODO Auto-generated method stub
		if(team1==null || team2==null||team1==team2){
			return new FunctionResult(functionResult.getErrorMsg().concat("Team Name is blank or same"), HttpStatus.BAD_REQUEST);
		}
		return functionResult;
	}

}
