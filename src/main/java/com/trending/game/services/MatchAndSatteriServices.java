package com.trending.game.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trending.game.enums.MatchStatus;
import com.trending.game.model.Match;
import com.trending.game.model.SattaPlayer;
import com.trending.game.model.Satteri;
import com.trending.game.model.Team;
import com.trending.game.repositories.MatchRepositories;
import com.trending.game.repositories.SattaPlayerRepositories;
import com.trending.game.repositories.SatteriRepositories;
import com.trending.game.repositories.TeamRepositories;

@Service
public class MatchAndSatteriServices {
	@Autowired
	SatteriRepositories satteriRepositories;
	@Autowired
	MatchRepositories matchRepositories;
	@Autowired
	TeamRepositories teamRepositories;
	@Autowired
	SattaPlayerRepositories sattaPlayerRepositories;
	public void addSatteri(Satteri satteri) {
		satteriRepositories.save(satteri);
	}

	public void addMatch(Match match) {
		matchRepositories.save(match);
	}
	public void addTeams(List<Team> teams) {
		teamRepositories.saveAll(teams);
	}
	public Satteri getSatteri(Integer satteriId) {
		return satteriRepositories.findById(satteriId).get();
		
	}
	public  List<Satteri> getSatteries() {
		return (List<Satteri>) satteriRepositories.findAll();
		
	}
	public Match getMatch(Integer matchId) {
		return matchRepositories.findById(matchId).get();
		
	}
	public  List<Match> getMatches() {
		return (List<Match>) matchRepositories.findAll();
		
	}
	public Team getTeam(Integer teamId) {
		return teamRepositories.findById(teamId).get();
		
	}
	
	public List<Team> getTeamByMatchId(Integer teamId) {
		Optional<Match> findById = matchRepositories.findById(teamId);
		findById.get().getTeams().size();
		return teamRepositories.findByMatchId(teamId);
		
	}
	public List<Team> getTeams() {
		return (List<Team>) teamRepositories.findAll();
		
	}
	public void addSattaPlayer(SattaPlayer sattaPlayer) {
		sattaPlayerRepositories.save(sattaPlayer);
	}
	
	public SattaPlayer getSattaPlayer(Integer sattaPlayerId) {
		return sattaPlayerRepositories.findById(sattaPlayerId).get();
		
	}
	public List<SattaPlayer> getSattaPlayers() {
		return (List<SattaPlayer>)sattaPlayerRepositories.findAll();
		
	}

	public void deleteSattaPlayer(Integer id) {
		sattaPlayerRepositories.deleteById(id);
		
	}

	public List<Satteri> getActiveMatch() {
		// TODO Auto-generated method stub
		List<Match> findByMatchStatus = matchRepositories.findByMatchStatus(MatchStatus.RUNNING);
		List<Satteri> satteries = new ArrayList<Satteri>();
		for(Match match:findByMatchStatus){
			match.getSatteri().getCurrentMatch().setTeams(getTeamByMatchId(match.getId()));
			satteries.add(match.getSatteri());
			
		}
		return satteries;
	}
}
