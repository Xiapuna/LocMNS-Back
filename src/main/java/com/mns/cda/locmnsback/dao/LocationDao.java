package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationDao extends JpaRepository<Location, Integer> {

}
