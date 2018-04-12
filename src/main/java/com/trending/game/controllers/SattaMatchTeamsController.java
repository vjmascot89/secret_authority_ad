package com.trending.game.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trending.game.enums.GameResult;
import com.trending.game.enums.MatchStatus;
import com.trending.game.enums.TeamsName;
import com.trending.game.logic.AlgoToCalculateProfitLoss;
import com.trending.game.logic.ProfitLossKCalculation;
import com.trending.game.model.Match;
import com.trending.game.model.SattaPlayer;
import com.trending.game.model.Satteri;
import com.trending.game.services.MatchAndSatteriServices;
import com.trending.game.validator.FunctionResult;
import com.trending.game.validator.IValidation;
import com.trending.game.validator.MatchValidationFactory;
import com.trending.game.validator.SatteriValidatorFactory;

@RestController
public class SattaMatchTeamsController {
	@Autowired
	MatchAndSatteriServices matchAndSatteri;
	@Autowired
	AlgoToCalculateProfitLoss algoToCalculationProfitLoss;
	private FunctionResult functionResult;
	

	@RequestMapping(method = RequestMethod.POST, value = "/startmatch")
	@ResponseBody
	public ResponseEntity<List<Satteri>> addMatchAndSatteri(@RequestBody Satteri satteri) {
		System.out.println("Add Match and Satteri");
		try{
		List<IValidation> validationList = new ArrayList<IValidation>(new SatteriValidatorFactory(satteri.getName()).getValidationList());
		validationList.addAll(new MatchValidationFactory(satteri.getCurrentMatch()).getValidationList());
		functionResult = new FunctionResult("",HttpStatus.ACCEPTED);
		for (IValidation iValidation : validationList) {
			iValidation.isValid(functionResult);
		}
		if(functionResult.getStatus()==HttpStatus.BAD_REQUEST){
			LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
			linkedMultiValueMap.add("Bad values error", functionResult.getErrorMsg());
			return new ResponseEntity<List<Satteri>>(linkedMultiValueMap,functionResult.getStatus());
		}
		satteri.getCurrentMatch().getFirstTeam().setStatus(GameResult.NOT_AVAILABLE);
		satteri.getCurrentMatch().getFirstTeam().setOrder(1);
		satteri.getCurrentMatch().getSecondTeam().setStatus(GameResult.NOT_AVAILABLE);
		satteri.getCurrentMatch().getSecondTeam().setOrder(2);
		satteri.setTotalBalanceOnTeamOneWin(BigDecimal.ZERO);
		satteri.setTotalBalanceOnTeamTwoWin(BigDecimal.ZERO);
		satteri.setTotalBalanceOnTeamOneLoss(BigDecimal.ZERO);
		satteri.setTotalBalanceOnTeamTwoLoss(BigDecimal.ZERO);
		if (satteri.getBalancePool() == null) {
			satteri.setBalancePool(BigDecimal.ZERO);
		}
		matchAndSatteri.addSatteri(satteri);
		Satteri satteriLocal = new Satteri(satteri);
		Match match = new Match(satteri.getCurrentMatch());
		match.setSatteri(satteriLocal);
		match.setMatchStatus(MatchStatus.RUNNING);
		matchAndSatteri.addMatch(match);
		List<Satteri> arrayList = new ArrayList<Satteri>();
		satteri.setCurrentMatch(match);
		arrayList.add(satteri);
		return new ResponseEntity<List<Satteri>>(arrayList, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
				return new ResponseEntity<List<Satteri>>(HttpStatus.BAD_REQUEST);
		}

	}





	@RequestMapping(method = RequestMethod.POST, value = "/sattalagao/{satteriId}")
	@ResponseBody
	public ResponseEntity<Satteri> addSattaPlayer(@RequestBody SattaPlayer sattaPlayer,
			@PathVariable Integer satteriId) {
		System.out.println("add Satta Player" + satteriId);
		Satteri satteri = matchAndSatteri.getSatteri(satteriId);
		if (satteri.getCurrentMatch().getMatchStatus() != MatchStatus.STOPPED) {
			sattaPlayer.setCurrentPotTeamOne(
					sattaPlayer.getCurrentPotTeamOne() == null ? BigDecimal.ZERO : sattaPlayer.getCurrentPotTeamOne());
			sattaPlayer.setCurrentPotTeamTwo(
					sattaPlayer.getCurrentPotTeamTwo() == null ? BigDecimal.ZERO : sattaPlayer.getCurrentPotTeamTwo());
			sattaPlayer.setCurrentPotRatioOnTeamOne(sattaPlayer.getCurrentPotRatioOnTeamOne() == null
					? satteri.getCurrentMatch().getFirstTeam().getRatio() : sattaPlayer.getCurrentPotRatioOnTeamOne());
			sattaPlayer.setCurrentPotRatioOnTeamTwo(sattaPlayer.getCurrentPotRatioOnTeamTwo() == null
					? satteri.getCurrentMatch().getSecondTeam().getRatio() : sattaPlayer.getCurrentPotRatioOnTeamTwo());
			synchronized (satteriId.toString()) {
				ProfitLossKCalculation profitLossKCalculation = new ProfitLossKCalculation(algoToCalculationProfitLoss);
				BigDecimal teamOneWinAmount = profitLossKCalculation.adjustBalanceOnWin(
						sattaPlayer.getCurrentPotRatioOnTeamOne(), sattaPlayer.getCurrentPotTeamOne());
				BigDecimal teamTwoWinAmount = profitLossKCalculation.adjustBalanceOnWin(
						sattaPlayer.getCurrentPotRatioOnTeamTwo(), sattaPlayer.getCurrentPotTeamTwo());

				BigDecimal teamOneLossAmount = profitLossKCalculation.adjustBalanceOnLoss(
						sattaPlayer.getCurrentPotRatioOnTeamOne(), sattaPlayer.getCurrentPotTeamOne());
				BigDecimal teamTwoLossAmount = profitLossKCalculation.adjustBalanceOnLoss(
						sattaPlayer.getCurrentPotRatioOnTeamTwo(), sattaPlayer.getCurrentPotTeamTwo());
				sattaPlayer.setTeamOneWinAmount(teamOneWinAmount);
				sattaPlayer.setTeamTwoWinAmount(teamTwoWinAmount);
				sattaPlayer.setTeamOneLossAmount(teamOneLossAmount);
				sattaPlayer.setTeamTwoLossAmount(teamTwoLossAmount);
				satteri.setTotalBalanceOnTeamOneWin(satteri.getTotalBalanceOnTeamOneWin().subtract(teamOneLossAmount));
				satteri.setTotalBalanceOnTeamTwoWin(satteri.getTotalBalanceOnTeamTwoWin().subtract(teamTwoLossAmount));
				satteri.setTotalBalanceOnTeamOneLoss(satteri.getTotalBalanceOnTeamOneLoss().subtract(teamOneWinAmount));
				satteri.setTotalBalanceOnTeamTwoLoss(satteri.getTotalBalanceOnTeamTwoLoss().subtract(teamTwoWinAmount));

				if (satteri != null) {
					Satteri satteriLocal = new Satteri(satteri);
					sattaPlayer.setSatteri(satteriLocal);
					matchAndSatteri.addSattaPlayer(sattaPlayer);
				}
			}
		}
		return new ResponseEntity<Satteri>(satteri, HttpStatus.ACCEPTED);
	}

	@RequestMapping("/satteri/{satteriId}")
	public Satteri getSatteri(@PathVariable Integer satteriId) {
		System.out.println("get satteri" + satteriId);
		return matchAndSatteri.getSatteri(satteriId);
	}

	@RequestMapping("/satteri")
	public List<Satteri> getSatteri() {
		System.out.println("get satteries");
		return matchAndSatteri.getSatteries();
	}

	@RequestMapping("/activematch")
	public List<Satteri> getActiveMatches() {
		System.out.println("Add Active Matches");
		return matchAndSatteri.getActiveMatch();
	}

	@RequestMapping("/passivematch")
	public List<Match> getAllPassiveMatches() {
		System.out.println("Add all Passive Matches");
		return matchAndSatteri.getAllPassiveMatch();
	}

	@RequestMapping("/passivematch/{matchId}")
	public List<Satteri> getPassiveMatches(@PathVariable Integer matchId) {
		System.out.println("Add Passive satteri for " + matchId);
		ArrayList<Satteri> arrayList = new ArrayList<Satteri>();
		arrayList.add(matchAndSatteri.getPassiveMatch(matchId));
		return arrayList;
	}

	@RequestMapping("/match/{matchId}")
	public Match getMatch(@PathVariable Integer matchId) {
		System.out.println("get Match");
		return matchAndSatteri.getMatch(matchId);

	}

	@RequestMapping("/match")
	public List<Match> getMatches() {
		System.out.println("get matches");
		return matchAndSatteri.getMatches();
	}

	@RequestMapping("/sattaplayer/{teamId}")
	public SattaPlayer getSattaPlayer(@PathVariable Integer sattaPlayerId) {
		System.out.println("get satta player" + sattaPlayerId);
		return matchAndSatteri.getSattaPlayer(sattaPlayerId);
	}

	@RequestMapping("/sattaplayer")
	public List<SattaPlayer> getSattaPlayers() {
		System.out.println("get satta players");
		return matchAndSatteri.getSattaPlayers();
	}

	@RequestMapping("/stopmatch/{teamName}/winner/{satteriId}")
	public ResponseEntity<Satteri> stopMatch(@PathVariable Integer satteriId, @PathVariable String teamName) {
		System.out.println("get match");
		Satteri satteri = matchAndSatteri.getSatteri(satteriId);
		if (satteri.getCurrentMatch().getMatchStatus() == MatchStatus.RUNNING) {
			synchronized (satteriId.toString()) {
				ProfitLossKCalculation profitLossKCalculation = new ProfitLossKCalculation(algoToCalculationProfitLoss);

				satteri.getCurrentMatch().setMatchStatus(MatchStatus.STOPPED);
				int index = 0;

				if (satteri.getCurrentMatch().getFirstTeam().getTeamName()
						.equals(TeamsName.valueOf(teamName.toUpperCase()))) {
					index = satteri.getCurrentMatch().getFirstTeam().getOrder();
					satteri.getCurrentMatch().getFirstTeam().setStatus(GameResult.Won);
					satteri.getCurrentMatch().getSecondTeam().setStatus(GameResult.Lost);
				} else if (satteri.getCurrentMatch().getSecondTeam().getTeamName()
						.equals(TeamsName.valueOf(teamName))) {
					index = satteri.getCurrentMatch().getSecondTeam().getOrder();
					satteri.getCurrentMatch().getFirstTeam().setStatus(GameResult.Lost);
					satteri.getCurrentMatch().getSecondTeam().setStatus(GameResult.Won);
				}
				if (index == 0) {
					new ResponseEntity<Satteri>(HttpStatus.EXPECTATION_FAILED);
				}
				for (SattaPlayer sattaPlayer : satteri.getSattaPlayer()) {
					if (index == 1) {
						sattaPlayer.setTeamOneLossAmount(BigDecimal.ZERO);
						sattaPlayer.setTeamTwoWinAmount(BigDecimal.ZERO);
					} else if (index == 2) {
						sattaPlayer.setTeamOneWinAmount(BigDecimal.ZERO);
						sattaPlayer.setTeamTwoLossAmount(BigDecimal.ZERO);
					}
					sattaPlayer.setFinalAmount(profitLossKCalculation.calculateTotalAmountWonOrLost(
							sattaPlayer.getTeamOneLossAmount(), sattaPlayer.getTeamOneWinAmount(),
							sattaPlayer.getTeamTwoLossAmount(), sattaPlayer.getTeamTwoWinAmount()));
				}
				if (index == 1) {
					satteri.setTotalBalanceOnTeamTwoLoss(BigDecimal.ZERO);
					satteri.setTotalBalanceOnTeamOneWin(BigDecimal.ZERO);
				} else if (index == 2) {
					satteri.setTotalBalanceOnTeamOneLoss(BigDecimal.ZERO);
					satteri.setTotalBalanceOnTeamTwoWin(BigDecimal.ZERO);
				}
				satteri.setFinalAmount(profitLossKCalculation.calculateTotalAmountWonOrLost(satteri.getBalancePool(),
						satteri.getTotalBalanceOnTeamOneLoss(), satteri.getTotalBalanceOnTeamTwoWin(),
						satteri.getTotalBalanceOnTeamTwoLoss(), satteri.getTotalBalanceOnTeamOneWin()));
				matchAndSatteri.addSatteri(satteri);
			}
			return new ResponseEntity<Satteri>(satteri, HttpStatus.EXPECTATION_FAILED);
		} else
			return new ResponseEntity<Satteri>(HttpStatus.EXPECTATION_FAILED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/sattalagao/{sattaPlayerId}")
	@ResponseBody
	public ResponseEntity<Satteri> deleteSattaPlayer(@PathVariable Integer sattaPlayerId) {
		System.out.println("delete Satta Player" + sattaPlayerId);

		SattaPlayer sattaPlayer = matchAndSatteri.getSattaPlayer(sattaPlayerId);
		Satteri satteri = sattaPlayer.getSatteri();
		if (!satteri.getCurrentMatch().getMatchStatus().equals(MatchStatus.STOPPED)) {
			synchronized (satteri.getId().toString()) {
				satteri.setTotalBalanceOnTeamOneWin(
						satteri.getTotalBalanceOnTeamOneWin().add(sattaPlayer.getTeamOneLossAmount()));
				satteri.setTotalBalanceOnTeamTwoWin(
						satteri.getTotalBalanceOnTeamTwoWin().add(sattaPlayer.getTeamTwoLossAmount()));
				satteri.setTotalBalanceOnTeamOneLoss(
						satteri.getTotalBalanceOnTeamOneLoss().add(sattaPlayer.getTeamOneWinAmount()));
				satteri.setTotalBalanceOnTeamTwoLoss(
						satteri.getTotalBalanceOnTeamTwoLoss().add(sattaPlayer.getTeamTwoWinAmount()));
				matchAndSatteri.deleteSattaPlayer(sattaPlayer.getId());
				matchAndSatteri.addSatteri(satteri);
			}
		}
		return new ResponseEntity<Satteri>(satteri, HttpStatus.ACCEPTED);
	}

}
