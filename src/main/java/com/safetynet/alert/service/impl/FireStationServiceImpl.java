package com.safetynet.alert.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.dto.Address;
import com.safetynet.alert.dto.MedicalRecordDTO;
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

import lombok.Data;

@Data
@Service
public class FireStationServiceImpl implements FireStationService {

	@Autowired
	private FireStationRepository fireStationRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Util util;

	public FireStation addFireStation(FireStation fireStation) {
		return fireStationRepository.save(fireStation);
	}

	public FireStation updateFireStation(String address, int station) {
		FireStation newFireStation = fireStationRepository.getFireStationByAddress(address);
		newFireStation.setStation(station);
		return fireStationRepository.save(newFireStation);
	}

	public void deleteFireStationByAddress(String address) {
		FireStation fireStation = fireStationRepository.getFireStationByAddress(address);
		fireStationRepository.delete(fireStation);
	}
	/*
	 * @Override public List<String>
	 * coveredPersonsByFireStationWithChildrenAdultCount(Integer station) {
	 * 
	 * int childrenNumber = 0, adultsNumber = 0; String separation =
	 * "=====================";
	 * 
	 * List<List<Object>> metaAL =
	 * fireStationRepository.getDataFromPersonsAndBirthdate(station);
	 * ArrayList<String> newAL = new ArrayList<>(); for( List<Object> listObject :
	 * metaAL) { for(int i = 0; i<6 ; i++) { String param =
	 * (listObject.get(i)).toString(); newAL.add(param); } newAL.add(separation);
	 * 
	 * Date birthdate = (Date) (listObject.get(6));
	 * 
	 * String ageCategory = util.determineAgeCategory(util.calculateAge(birthdate));
	 * if(ageCategory.equals("enfant")) childrenNumber ++;
	 * if(ageCategory.equals("adulte")) adultsNumber ++; }
	 * 
	 * String counterChildren = "Nombre d'enfants (<= 18 ans) : " + childrenNumber;
	 * newAL.add(counterChildren); String counterAdults =
	 * "Nombre d'adultes             : "+ adultsNumber; newAL.add(counterAdults);
	 * 
	 * return newAL;
	 * 
	 * }
	 */

	@Override
	public List<String> phoneAlert(Integer station) {
		return fireStationRepository.phoneAlert(station);
	}

	@Override
	public ResponsePersonByFireStation coveredPersonsByFireStationWithChildrenAdultCount(Integer station) {
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
		return response;
	}

	public int numeroCaserne(String address) {
		return 0;
	}

	public ResponseFire fire(String address) {
		ResponseFire response = new ResponseFire();

		List<FireStation> fireStationsList = fireStationRepository.findByAddress(address);
		for (FireStation firestation : fireStationsList) {
			response.getStation().add(firestation.getStation());
		}

		// response.setStation(stationNumber);
		/*
		 * List<Integer> stationList = fireStationRepository.findByAddress(address);
		 * //response.setStation(fireStationRepository.findByAddress(address));
		 * for(Integer integer : stationList) { response.getStation().add(integer); }
		 */

		List<Person> residentsList = personRepository.findByAddress(address);
		for (Person person : residentsList) {
			PersonWithMedicalRecordDTO personWithMedicalRecordDTO = modelMapper.map(person,
					PersonWithMedicalRecordDTO.class);
			personWithMedicalRecordDTO.setAge(util.getAge(person));

			List<MedicalRecord> medicalRecordsList = medicalRecordRepository
					.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
			for (MedicalRecord medicalRecord : medicalRecordsList) {
				MedicalRecordDTO medicalRecordDTO = modelMapper.map(medicalRecord, MedicalRecordDTO.class);
				personWithMedicalRecordDTO.setMedications(medicalRecordDTO.getMedications());
				personWithMedicalRecordDTO.setAllergies(medicalRecordDTO.getAllergies());
			}

			response.getResidents().add(personWithMedicalRecordDTO);
		}
		return response;

	}

	public ResponseFlood flood(List<Integer> numbersOfStations) {
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
							.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
					for (MedicalRecord medicalRecord : medicalRecordsList) {
						MedicalRecordDTO medicalRecordDTO = modelMapper.map(medicalRecord, MedicalRecordDTO.class);
						personWithMedicalRecordDTO.setMedications(medicalRecordDTO.getMedications());
						personWithMedicalRecordDTO.setAllergies(medicalRecordDTO.getAllergies());
					}
					address.getListOfPersonsWithMedicalRecordDTO().add(personWithMedicalRecordDTO);

				}
				residentsByStation.getAddressesServedByFireStation().add(address);
			}
			response.getResidentsByStation().add(residentsByStation);

		}
		return response;
	}
	/*
	 * public FireStation getFireStationByAddress(String address) { return
	 * fireStationRepository.getFireStationByAddress(address); }
	 */
	/*
	 * public Iterable<String> createListOfPersonsCoveredByOneFireStation(Integer
	 * station){ return
	 * fireStationRepository.createListOfPersonsCoveredByOneFireStation(station); }
	 *//*
		 * public List<List<Object>> getDataAndBirthdate(Integer station){ return
		 * fireStationRepository.getDataAndBirthdate(station); }
		 */
	/*
	 * public List<FireStation> saveListFireStations(List<FireStation> list) {
	 * return fireStationRepository.saveAll(list); }
	 */
}
