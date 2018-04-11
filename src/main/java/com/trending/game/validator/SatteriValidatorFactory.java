package com.trending.game.validator;

import java.util.Arrays;
import java.util.List;

public class SatteriValidatorFactory implements IValidatorFactory {
	private String name;
	public SatteriValidatorFactory(String name){
		this.name = name;
	}
	@Override
	public List<IValidation> getValidationList() {
		return Arrays.asList(new NameValidation(name));
	}

}
