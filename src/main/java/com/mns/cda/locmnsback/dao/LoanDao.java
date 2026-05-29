package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.enums.LoanStatus;
import com.mns.cda.locmnsback.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanDao extends JpaRepository<Loan, Integer> {
    List<Loan> findByEquipmentId(Integer equipmentId);
    List<Loan> findByAppUserId(Integer userId);
    List<Loan> findByLoanStatus(LoanStatus status);

}
