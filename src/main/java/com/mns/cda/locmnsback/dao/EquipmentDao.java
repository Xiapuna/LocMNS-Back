package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentDao extends JpaRepository<Equipment, Integer> {

}
