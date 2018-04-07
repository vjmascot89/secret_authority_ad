package com.trending.game.controllers;

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

import com.trending.game.model.Match;
import com.trending.game.model.SattaPlayer;
import com.trending.game.model.Satteri;
import com.trending.game.model.Team;
import com.trending.game.services.MatchAndSatteriServices;

@RestController
public class SattaMatchTeamsController {
	@Autowired
	MatchAndSatteriServices matchAndSatteri;

	@RequestMapping(method = RequestMethod.POST, value = "/startmatch")
	@ResponseBody
	public ResponseEntity<List<Satteri>> addMatchAndSatteri(@RequestBody Satteri satteri) {
		matchAndSatteri.addSatteri(satteri);
		Satteri satteriLocal = new Satteri(satteri);
		Match match = new Match(satteri.getCurrentMatch());
		match.setSatteri(satteriLocal);
		matchAndSatteri.addMatch(match);
		Match matchLocal = new Match(match);
		 for (Team t : satteri.getCurrentMatch().getTeams())
		 t.setMatch(matchLocal);
		matchAndSatteri.addTeams(satteri.getCurrentMatch().getTeams());
		satteri.getCurrentMatch().setTeams(matchAndSatteri.getTeamByMatchId(matchLocal.getId()));
		List<Satteri> arrayList = new ArrayList<Satteri>();
		arrayList.add(satteri);
		return new ResponseEntity<List<Satteri>>(arrayList, HttpStatus.ACCEPTED);
		
	}
	@RequestMapping(method = RequestMethod.POST, value = "/sattalagao/{satteriId}")
	@ResponseBody
	public ResponseEntity<Satteri> addSattaPlayer(@RequestBody SattaPlayer sattaPlayer,@PathVariable Integer satteriId) {
		System.out.println("add Satta Player" + satteriId);

		Satteri satteri = matchAndSatteri.getSatteri(satteriId);
		if(satteri!=null){
			Satteri satteriLocal = new Satteri(satteri);
			sattaPlayer.setSatteri(satteriLocal);
			matchAndSatteri.addSattaPlayer(sattaPlayer);
		}
		satteri.getCurrentMatch().setTeams(matchAndSatteri.getTeamByMatchId(satteri.getCurrentMatch().getId()));
		return new ResponseEntity<Satteri>(satteri, HttpStatus.ACCEPTED);
	}
	@RequestMapping("/satteri/{satteriId}")
	public Satteri getSatteri(@PathVariable Integer satteriId) {
		System.out.println("get satteri" + satteriId);
		return matchAndSatteri.getSatteri(satteriId);
	}

	@RequestMapping("/satteri")
	public List<Satteri> getSatteri() {
		System.out.println("get satteries" );
		return matchAndSatteri.getSatteries();
	}

	@RequestMapping("/match/{matchId}")
	public Match getMatch(@PathVariable Integer matchId) {
		System.out.println("get match" + matchId);
		return matchAndSatteri.getMatch(matchId);
	}

	@RequestMapping("/match")
	public List<Match> getMatches() {
		System.out.println("get matches" );
		return matchAndSatteri.getMatches();
	}

//	@RequestMapping("/team/{teamId}")
//	public Team getTeam(@PathVariable Integer teamId) {
//		System.out.println("get team" + teamId);
//		return matchAndSatteri.getTeam(teamId);
//	}

	@RequestMapping("/team")
	public List<Team> getTeams() {
		System.out.println("get teams" );
		return matchAndSatteri.getTeams();
	}
	
	@RequestMapping("/team/{matchId}")
	public List<Team> getTeam(@PathVariable Integer matchId) {
		System.out.println("get teams" );
		return matchAndSatteri.getTeamByMatchId(matchId);
	}
	
	@RequestMapping("/sattaplayer/{teamId}")
	public SattaPlayer getSattaPlayer(@PathVariable Integer sattaPlayerId) {
		System.out.println("get satta player" + sattaPlayerId);
		return matchAndSatteri.getSattaPlayer(sattaPlayerId);
	}

	@RequestMapping("/sattaplayer")
	public List<SattaPlayer> getSattaPlayers() {
		System.out.println("get satta players" );
		return matchAndSatteri.getSattaPlayers();
	}
}
