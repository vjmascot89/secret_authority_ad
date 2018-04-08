package com.trending.game.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trending.game.dao.ProfitLossKCalculation;
import com.trending.game.enums.GameResult;
import com.trending.game.enums.MatchStatus;
import com.trending.game.enums.TeamsName;
import com.trending.game.model.Match;
import com.trending.game.model.SattaPlayer;
import com.trending.game.model.Satteri;
import com.trending.game.model.Team;
import com.trending.game.services.AlgoToCalculateProfitLoss;
import com.trending.game.services.MatchAndSatteriServices;

@RestController
public class SattaMatchTeamsController {
	@Autowired
	MatchAndSatteriServices matchAndSatteri;
	@Autowired
	AlgoToCalculateProfitLoss algoToCalculationProfitLoss;

	@RequestMapping(method = RequestMethod.POST, value = "/startmatch")
	@ResponseBody
	public ResponseEntity<List<Satteri>> addMatchAndSatteri(@RequestBody Satteri satteri) {
		for (Team t : satteri.getCurrentMatch().getTeams()) {
			t.setStatus(GameResult.NOT_AVAILABLE);
		}
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
		Match matchLocal = new Match(match);
		matchAndSatteri.addTeams(satteri.getCurrentMatch().getTeams());
		satteri.getCurrentMatch().setTeams(matchAndSatteri.getTeamByMatchId(matchLocal.getId()));
		List<Satteri> arrayList = new ArrayList<Satteri>();
		arrayList.add(satteri);
		return new ResponseEntity<List<Satteri>>(arrayList, HttpStatus.ACCEPTED);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/sattalagao/{satteriId}")
	@ResponseBody
	public ResponseEntity<Satteri> addSattaPlayer(@RequestBody SattaPlayer sattaPlayer,
			@PathVariable Integer satteriId) {
		System.out.println("add Satta Player" + satteriId);
		sattaPlayer.setCurrentPotTeamOne(
				sattaPlayer.getCurrentPotTeamOne() == null ? BigDecimal.ZERO : sattaPlayer.getCurrentPotTeamOne());
		sattaPlayer.setCurrentPotTeamTwo(
				sattaPlayer.getCurrentPotTeamTwo() == null ? BigDecimal.ZERO : sattaPlayer.getCurrentPotTeamTwo());
		Satteri satteri = matchAndSatteri.getSatteri(satteriId);
		synchronized (satteriId.toString()) {
			satteri.getCurrentMatch().setTeams(matchAndSatteri.getTeamByMatchId(satteri.getCurrentMatch().getId()));
			ProfitLossKCalculation profitLossKCalculation = new ProfitLossKCalculation(algoToCalculationProfitLoss);
			BigDecimal teamOneWinAmount = profitLossKCalculation
					.adjustBalanceOnWin(sattaPlayer.getCurrentPotRatioOnTeamOne(), sattaPlayer.getCurrentPotTeamOne());
			BigDecimal teamTwoWinAmount = profitLossKCalculation
					.adjustBalanceOnWin(sattaPlayer.getCurrentPotRatioOnTeamTwo(), sattaPlayer.getCurrentPotTeamTwo());

			BigDecimal teamOneLossAmount = profitLossKCalculation
					.adjustBalanceOnLoss(sattaPlayer.getCurrentPotRatioOnTeamOne(), sattaPlayer.getCurrentPotTeamOne());
			BigDecimal teamTwoLossAmount = profitLossKCalculation
					.adjustBalanceOnLoss(sattaPlayer.getCurrentPotRatioOnTeamTwo(), sattaPlayer.getCurrentPotTeamTwo());
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
		return matchAndSatteri.getActiveMatch();
	}

	@RequestMapping("/match/{matchId}")
	public Match getMatch(@PathVariable Integer matchId) {

		return matchAndSatteri.getMatch(matchId);

	}

	@RequestMapping("/match")
	public List<Match> getMatches() {
		System.out.println("get matches");
		return matchAndSatteri.getMatches();
	}

	@RequestMapping("/team")
	public List<Team> getTeams() {
		System.out.println("get teams");
		return matchAndSatteri.getTeams();
	}

	@RequestMapping("/team/{matchId}")
	public List<Team> getTeam(@PathVariable Integer matchId) {
		System.out.println("get teams");
		return matchAndSatteri.getTeamByMatchId(matchId);
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
		Satteri satteri = matchAndSatteri.getSatteri(satteriId);
		List<Team> teams = matchAndSatteri.getTeamByMatchId(satteri.getCurrentMatch().getId());
		if (satteri.getCurrentMatch().getMatchStatus() == MatchStatus.RUNNING) {
			synchronized (satteriId.toString()) {
				ProfitLossKCalculation profitLossKCalculation = new ProfitLossKCalculation(algoToCalculationProfitLoss);

				satteri.getCurrentMatch().setMatchStatus(MatchStatus.STOPPED);
				int index = 0;
				for (Team t : teams) {
					t.setStatus(GameResult.Lost);
					if (t.getTeamName().equals(TeamsName.valueOf(teamName))) {
						t.setStatus(GameResult.Won);
						index = t.getOrder();
					}
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
					satteri.setTotalBalanceOnTeamOneLoss(BigDecimal.ZERO);
					satteri.setTotalBalanceOnTeamTwoWin(BigDecimal.ZERO);
				} else if (index == 2) {
					satteri.setTotalBalanceOnTeamTwoLoss(BigDecimal.ZERO);
					satteri.setTotalBalanceOnTeamOneWin(BigDecimal.ZERO);
				}
				satteri.setFinalAmount(profitLossKCalculation.calculateTotalAmountWonOrLost(satteri.getBalancePool(),
						satteri.getTotalBalanceOnTeamOneLoss(), satteri.getTotalBalanceOnTeamTwoWin(),
						satteri.getTotalBalanceOnTeamTwoLoss(), satteri.getTotalBalanceOnTeamOneWin()));
				satteri.getCurrentMatch().setTeams(teams);
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
		List<Team> teams = matchAndSatteri.getTeamByMatchId(satteri.getCurrentMatch().getId());
		satteri.getCurrentMatch().setTeams(teams);
		return new ResponseEntity<Satteri>(satteri, HttpStatus.ACCEPTED);
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
