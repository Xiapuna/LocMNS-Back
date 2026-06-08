package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.model.LoanHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanHistoryDao extends JpaRepository<LoanHistory, Integer> {

}
