package com.mns.cda.locmnsback.dao;

import com.mns.cda.locmnsback.model.Accreditation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccreditationDao extends JpaRepository<Accreditation, Integer> {

}
