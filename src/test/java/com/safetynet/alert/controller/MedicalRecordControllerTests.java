package com.safetynet.alert.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.service.MedicalRecordService;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MedicalRecordService medicalRecordService;

	@Test
	public void testAddMedicalRecord() throws Exception {
		MedicalRecord medicalRecord = new MedicalRecord();

		mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecord))).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void testAddMedicalRecordWithException() throws Exception {
		MedicalRecord medicalRecord = new MedicalRecord();
		when(medicalRecordService.addMedicalRecord(medicalRecord)).thenThrow(NullPointerException.class);

		mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecord))).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testUpdateMedicalRecord() throws Exception {
		MedicalRecord medicalRecord = new MedicalRecord();

		mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecord))).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void testUpdateMedicalRecordWithException() throws Exception {
		MedicalRecord medicalRecord = new MedicalRecord();
		when(medicalRecordService.updateMedicalRecord(medicalRecord)).thenThrow(NullPointerException.class);


		mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalRecord))).andExpect(status().isOk()).andDo(print());
	}
	@Test
	public void testDeleteMedicalRecord() throws Exception {
		mockMvc.perform(delete("/medicalRecord").param("firstName", "firstName").param("lastName", "lastName"))
				.andExpect(status().isOk()).andDo(print());
	}
	@Test
	public void testDeleteMedicalRecordWithException() throws Exception {
		doThrow(NullPointerException.class).when(medicalRecordService).deleteMedicalRecordByFirstNameAndLastName("firstName","lastName");

		mockMvc.perform(delete("/medicalRecord").param("firstName", "firstName").param("lastName", "lastName"))
				.andExpect(status().isOk()).andDo(print());
	}
}
