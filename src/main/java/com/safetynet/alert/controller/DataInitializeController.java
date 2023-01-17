package com.safetynet.alert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import com.safetynet.alert.service.impl.DataInitializeServiceImpl;

public class DataInitializeController {

	@Autowired
	private DataInitializeServiceImpl dataInitializeService;
	
	@PostMapping("/manageData")
	public void loadData() {
		dataInitializeService.readJsonFile();
	}
	
}
