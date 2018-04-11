package com.trending.game.validator;

import com.trending.game.model.Match;
import com.trending.game.model.SattaPlayer;
import com.trending.game.model.Satteri;
import com.trending.game.model.Team;

public interface IValidator {

	public String isValid(Satteri satteri);
	public String isValid(Match match);
	public String isValid(Team team);
	public String isValid(SattaPlayer plyer);
}
