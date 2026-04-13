package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDao extends JpaRepository<Type, Integer> {

}
