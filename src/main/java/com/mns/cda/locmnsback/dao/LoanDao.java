package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanDao extends JpaRepository<Loan, Integer> {

}
