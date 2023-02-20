package com.safetynet.alert.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.service.DataInitializeService;

@RestController
public class DataInitializeController {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializeController.class);

	@Autowired
	private DataInitializeService dataInitializeService;

	@PostMapping("/manageData")
	public void loadData() {
		logger.debug("requête loadData envoyée de PersonController");
		try {
			dataInitializeService.readJsonFile();
			logger.info("requête loadData réussie chez PersonController!");
		} catch (Exception e) {
			logger.error("marche pas :(", e);

		}
	}

}
