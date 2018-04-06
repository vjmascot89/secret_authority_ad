package com.trending.game.repositories;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trending.game.model.Team;

@Transactional
@Repository
public interface TeamRepositories extends CrudRepository<Team, Integer> {

}
