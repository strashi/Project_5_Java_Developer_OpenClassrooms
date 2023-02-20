package com.safetynet.alert.controller;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alert.service.DataInitializeService;

@WebMvcTest(controllers = DataInitializeController.class)
public class DataInitializeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DataInitializeService dataInitializeService;

	@Test
	public void testLoadData() throws Exception {
		mockMvc.perform(post("/manageData")).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void testLoadDataWithException() throws Exception {
		doThrow(NullPointerException.class).when(dataInitializeService).readJsonFile();
		
		mockMvc.perform(post("/manageData")).andExpect(status().isOk()).andDo(print());
	}

}
