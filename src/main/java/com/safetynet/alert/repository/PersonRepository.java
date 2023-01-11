package com.safetynet.alert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alert.model.Person;


public interface PersonRepository extends JpaRepository<Person, Long>{

}
