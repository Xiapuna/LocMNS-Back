package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.model.Documentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentationDao extends JpaRepository<Documentation, Integer> {

}
