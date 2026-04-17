package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDao extends JpaRepository<State, Integer> {

}
