package com.safetynet.alert.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alert.model.FireStation;

@Repository
public interface FireStationRepository extends CrudRepository<FireStation, Long>{

}
