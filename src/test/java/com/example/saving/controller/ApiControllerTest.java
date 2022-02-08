package com.example.saving.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.saving.Dto.Reminder;
import com.example.saving.repository.RemindersRepository;
import com.example.saving.service.ApiService;


@RunWith(SpringRunner.class)
@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    ApiService apiService;
    
    @MockBean
    RemindersRepository remindersRepository;


    private Reminder createReminder(Integer id, String name, Date date, Boolean isComplete) {
    	
    	return Reminder.builder()
		        .id(id)
		        .name(name)
		        .date(date)
		        .isComplete(isComplete)
		        .build();

    }
    
    @SuppressWarnings("deprecation")
	@Test
    public void shouldAddReminder() throws Exception {
        String json = "{ \"name\":\"Teste\", \"date\": \"2022-02-01\", \"isComplete\": true }";

        Reminder reminder = createReminder(null, "Teste", new Date(2022, 2, 1), true);

        when(apiService.createReminder(reminder)).thenReturn(reminder);

        mockMvc.perform(post("/reminder").accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnReminderById() throws Exception {
    	 Reminder reminder = createReminder(null, "Teste", new Date(2022, 2, 1), true);

        when(apiService.findReminder(10L)).thenReturn(reminder);

        mockMvc.perform(get("/reminder/10").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test
    public void shouldNotReturnReminderById() throws Exception {
        when(apiService.findReminder(1L)).thenReturn(null);

        mockMvc.perform(get("/reminder/1").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
