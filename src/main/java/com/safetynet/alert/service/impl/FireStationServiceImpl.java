package com.safetynet.alert.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.dto.Address;
import com.safetynet.alert.dto.PersonDTO;
import com.safetynet.alert.dto.PersonWithMedicalRecordDTO;
import com.safetynet.alert.dto.ResidentsByStation;
import com.safetynet.alert.dto.ResponseFire;
import com.safetynet.alert.dto.ResponseFlood;
import com.safetynet.alert.dto.ResponsePersonByFireStation;
import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.FireStationRepository;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.repository.PersonRepository;
import com.safetynet.alert.service.FireStationService;
import com.safetynet.alert.service.UtilService;

import lombok.Data;

@Data
@Service
public class FireStationServiceImpl implements FireStationService {

	private static final Logger logger = LoggerFactory.getLogger(FireStationServiceImpl.class);

	@Autowired
	private FireStationRepository fireStationRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UtilService util;

	public FireStation addFireStation(FireStation fireStation) {
		logger.debug("traitement addFireStation en cours chez FireStationServiceImpl");
		try {
			FireStation response = fireStationRepository.save(fireStation);
			logger.info("traitement addFireStation réussi chez FireStationServiceImpl!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	public List<FireStation> updateFireStation(String address, List<Integer> stationList) {
		logger.debug("traitement updateFireStation en cours chez FireStationServiceImpl");
		List<FireStation> updatedFireStationList = new ArrayList<>();
		try {
			List<FireStation> newFireStationList = fireStationRepository.findByAddress(address);
			for (int i = 0; i < newFireStationList.size(); i++) {
				FireStation fireStation = newFireStationList.get(i);
				fireStation.setStation(stationList.get(i));
				updatedFireStationList.add(fireStation);
			}
			List<FireStation> response = new ArrayList<>();

			if (fireStationRepository.saveAll(updatedFireStationList) != null)
				response = fireStationRepository.saveAll(updatedFireStationList);
			else
				response = updatedFireStationList;

			logger.info("traitement updateFireStation réussi chez FireStationServiceImpl!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}

	}

	public void deleteFireStation(FireStation firestation) {
		logger.debug("traitement deleteFireStation en cours chez FireStationServiceImpl");
		List<FireStation> fireStationToDeleteList = new ArrayList<>();
		try {
			if (firestation.getAddress() != "string") {
				fireStationToDeleteList = fireStationRepository.findByAddress(firestation.getAddress());
			}
			if (firestation.getStation() != 0) {
				fireStationToDeleteList = fireStationRepository.findByStation(firestation.getStation());
			}
			fireStationRepository.deleteAll(fireStationToDeleteList);
			logger.info("traitement deleteFireStation réussi chez FireStationServiceImpl!");
		} catch (Exception e) {
			logger.error("marche pas :(", e);

		}

	}

	@Override
	public List<String> phoneAlert(Integer station) {
		logger.debug("traitement phoneAlert en cours chez FireStationServiceImpl");
		try {
			List<String> response = fireStationRepository.phoneAlert(station);
			logger.info("traitement phoneAlert réussi chez FireStationServiceImpl!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	@Override
	public ResponsePersonByFireStation coveredPersonsByFireStationWithChildrenAdultCount(Integer station) {
		logger.debug("traitement coveredPersonsByFireStation en cours chez FireStationServiceImpl");
		try {

			ResponsePersonByFireStation response = new ResponsePersonByFireStation();
			List<FireStation> listOfStations = fireStationRepository.findByStation(station);
			int nbreAdulte = 0;
			int nbreEnfant = 0;
			for (FireStation fireStation : listOfStations) {
				List<Person> personList = personRepository.findByAddress(fireStation.getAddress());
				for (Person person : personList) {
					PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);
					personDTO.setAge(util.getAge(person));
					if (personDTO.getAge() >= 19)
						nbreAdulte++;
					else
						nbreEnfant++;
					response.getPersons().add(personDTO);
				}

			}
			response.setNumberOfAdults(nbreAdulte);
			response.setNumberOfChildren(nbreEnfant);
			logger.info("traitement coveredPersonsByFireStation réussi chez FireStationServiceImpl!");

			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	public ResponseFire fire(String address) {
		logger.debug("traitement fire en cours chez FireStationServiceImpl");
		try {

			ResponseFire response = new ResponseFire();

			List<FireStation> fireStationsList = fireStationRepository.findByAddress(address);
			for (FireStation firestation : fireStationsList) {
				response.getStation().add(firestation.getStation());
			}

			List<Person> residentsList = personRepository.findByAddress(address);
			for (Person person : residentsList) {
				PersonWithMedicalRecordDTO personWithMedicalRecordDTO = modelMapper.map(person,
						PersonWithMedicalRecordDTO.class);
				personWithMedicalRecordDTO.setAge(util.getAge(person));

				List<MedicalRecord> medicalRecordsList = medicalRecordRepository
						.findMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
				for (MedicalRecord medicalRecord : medicalRecordsList) {
					personWithMedicalRecordDTO.getMedications().addAll(medicalRecord.getMedications());
					personWithMedicalRecordDTO.getAllergies().addAll(medicalRecord.getAllergies());
				}

				response.getResidents().add(personWithMedicalRecordDTO);
			}
			logger.info("traitement fire réussi chez FireStationServiceImpl!");

			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);

			return null;
		}
	}

	public ResponseFlood flood(List<Integer> numbersOfStations) {
		logger.debug("traitement flood en cours chez FireStationServiceImpl");
		try {

			ResponseFlood response = new ResponseFlood();

			for (Integer station : numbersOfStations) {
				ResidentsByStation residentsByStation = new ResidentsByStation();
				residentsByStation.setStationNumber("Station " + station);
				List<FireStation> listOfStations = fireStationRepository.findByStation(station);
				for (FireStation fireStation : listOfStations) {
					Address address = new Address();
					address.setAddress(fireStation.getAddress());
					List<Person> residentsList = personRepository.findByAddress(address.getAddress());
					for (Person person : residentsList) {
						PersonWithMedicalRecordDTO personWithMedicalRecordDTO = modelMapper.map(person,
								PersonWithMedicalRecordDTO.class);
						personWithMedicalRecordDTO.setAge(util.getAge(person));

						List<MedicalRecord> medicalRecordsList = medicalRecordRepository
								.findMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
						for (MedicalRecord medicalRecord : medicalRecordsList) {
							personWithMedicalRecordDTO.getMedications().addAll(medicalRecord.getMedications());
							personWithMedicalRecordDTO.getAllergies().addAll(medicalRecord.getAllergies());
						}
						address.getListOfPersonsWithMedicalRecordDTO().add(personWithMedicalRecordDTO);
					}
					residentsByStation.getAddressesServedByFireStation().add(address);
				}
				response.getResidentsByStation().add(residentsByStation);

			}
			logger.info("traitement flood réussi chez FireStationServiceImpl!");

			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);

			return null;
		}
	}

}
