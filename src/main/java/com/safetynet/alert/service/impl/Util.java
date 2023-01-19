package com.safetynet.alert.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class Util {
	
	String determineAgeCategory(int age) {
		if(age < 19)
			return "enfant";
		else
			return "adulte";
	}

	int calculateAge(Date birthdate) {
		 LocalDate birthdateFormated = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 LocalDate now = LocalDate.now();
		 Period period = Period.between(birthdateFormated, now);
		 
		return period.getYears();
	}

}
