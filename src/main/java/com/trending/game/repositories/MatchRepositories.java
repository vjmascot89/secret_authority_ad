package com.trending.game.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trending.game.enums.MatchStatus;
import com.trending.game.model.Match;

@Transactional
@Repository
public interface MatchRepositories extends CrudRepository<Match, Integer> {

	List<Match> findByMatchStatus(MatchStatus running);

}
