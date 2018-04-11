package com.trending.game.validator;

import com.trending.game.model.Match;
import com.trending.game.model.SattaPlayer;
import com.trending.game.model.Satteri;
import com.trending.game.model.Team;


public class ValidatorImpl implements IValidator {


	@Override
	public String isValid(Satteri satteri) {
		String result = "success";
		if(satteri.getName() == null || satteri.getName().isEmpty() )
			result = "Name cannot be empty";
		return result;
	}

	@Override
	public String isValid(Match match) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String isValid(Team team) {
		String result = "success";
		if(team.getRatio()==null || team.getRatio().isEmpty()){
			result = "Ratio cannot be blank";
		}else{
			String[] split = null;
			String lowerProfitLossRatio = team.getRatio().toLowerCase();
			if (lowerProfitLossRatio.contains("k")) {
				split = lowerProfitLossRatio.split("k");
			} else if (lowerProfitLossRatio.contains("l")) {
				split = lowerProfitLossRatio.split("l");
			}else{
				result = "Khaya ya Lagaya? Bhaav galat hai..";
				return result;
			}
			Integer firstValue = Integer.parseInt(split[0]);
			if(firstValue <=0){
				result = "Invalid Ratio";
			}
		}
		return result;
	}

	@Override
	public String isValid(SattaPlayer plyer) {
		String result = "success";
		
			String[] split = null;
			String lowerProfitLossRatio = plyer.getCurrentPotRatioOnTeamOne().toLowerCase();
			if (lowerProfitLossRatio.contains("k")) {
				split = lowerProfitLossRatio.split("k");
			} else if (lowerProfitLossRatio.contains("l")) {
				split = lowerProfitLossRatio.split("l");
			}else{
				result = "Khaya ya Lagaya? Bhaav galat hai..";
				return result;
			}
			Integer firstValue = Integer.parseInt(split[0]);
			if(firstValue <=0){
				result = "Invalid Ratio";
			}
		
		return result;
	}
}
