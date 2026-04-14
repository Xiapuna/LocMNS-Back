package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelDao extends JpaRepository<Model, Integer> {

}
