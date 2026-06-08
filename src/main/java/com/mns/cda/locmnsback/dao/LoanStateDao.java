package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.model.LoanState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanStateDao extends JpaRepository<LoanState, Integer> {
    LoanState findByName(String name);
}
