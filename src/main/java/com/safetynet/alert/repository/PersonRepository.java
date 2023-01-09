package com.safetynet.alert.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alert.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>{

}
