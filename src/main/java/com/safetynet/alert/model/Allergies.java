package com.safetynet.alert.model;

import java.util.ArrayList;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Allergies {
	
	private ArrayList<String> allergiesList;

}
