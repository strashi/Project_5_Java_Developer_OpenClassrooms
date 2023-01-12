package com.safetynet.alert;

import java.util.List;

import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;

import lombok.Data;

@Data
public class JsonFileReader {
	public List<Person> persons;
	public List<FireStation> firestations;
	public List<MedicalRecord> medicalrecords;
}
