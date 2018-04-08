package com.trending.game.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trending.game.model.Team;

@Transactional
@Repository
public interface TeamRepositories extends CrudRepository<Team, Integer> {
	@Query(value="select * from team where match_id = 1",nativeQuery=true)
	List<Team> findByMatchId(Integer matchId);

}
