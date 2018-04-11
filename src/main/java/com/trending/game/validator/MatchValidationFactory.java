package com.trending.game.validator;

import java.util.Arrays;
import java.util.List;

import com.trending.game.model.Match;

public class MatchValidationFactory implements IValidatorFactory {
	private Match match;

	public MatchValidationFactory(Match match) {
		this.match = match;

	}

	@Override
	public List<IValidation> getValidationList() {
		if (match != null && match.getFirstTeam() != null && match.getSecondTeam() != null) {
			return Arrays.asList(new DateValidation(match.getDate()),
					new TeamsValidation(match.getFirstTeam().getTeamName(), match.getSecondTeam().getTeamName()),new RatioValidation(match.getFirstTeam().getRatio(), match.getSecondTeam().getRatio()));
		} else {
			return Arrays.asList(new GenericErrorValidation());
		}
	}

}
