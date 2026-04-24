package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.model.Equipment;
import com.mns.cda.locmnsback.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentDao extends JpaRepository<Equipment, Integer> {

}
