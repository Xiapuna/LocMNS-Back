package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestDao extends JpaRepository<Request, Integer> {

}
