package com.safetynet.alert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonDTO {
	
	private String firstName;
	
	private String lastName;
	private String address;
	private String city;
	private int zip;
	private String phone;
	private String email;
	private int age;

}
