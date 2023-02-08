package com.safetynet.alert.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.service.FireStationService;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private FireStationService fireStationService;

	@Test
	public void testAddFireStation() throws Exception {
		FireStation fireStation = new FireStation();

		mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(fireStation))).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testDeleteFireStaion() throws Exception {

		FireStation fireStation = new FireStation();

		mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(fireStation))).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testUpDateFireStation() throws Exception {

		List<Integer> stations = new ArrayList<>();
		stations.add(1);
		stations.add(2);
		String numberString = stations.stream().map(String::valueOf).collect(Collectors.joining(","));

		mockMvc.perform(put("/firestation").param("address", "address").param("stations", numberString))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testCoveredPersonsByFireStationWithChildrenAdultCount() throws Exception {
		mockMvc.perform(get("/firestation").param("stationNumber", "1")).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void phoneAlert() throws Exception {
		mockMvc.perform(get("/phoneAlert").param("firestation", "1")).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void fire() throws Exception {
		mockMvc.perform(get("/fire").param("address", "address")).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void flood() throws Exception {

		List<Integer> stations = new ArrayList<>();
		stations.add(1);
		stations.add(3);
		String numberString = stations.stream().map(String::valueOf).collect(Collectors.joining(","));

		mockMvc.perform(get("/flood").param("list_of_station_number", numberString)).andExpect(status().isOk())
				.andDo(print());
	}
}
