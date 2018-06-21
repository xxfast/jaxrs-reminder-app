package com.byond.reminderdbservice.resource;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.byond.reminderdbservice.model.Reminder;
import com.byond.reminderdbservice.repository.RemindersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReminderResourceTests {
	
	private MockMvc mockMvc;
	
	@Mock
	RemindersRepository repository;
	
	@InjectMocks
	private ReminderResource reminderResource;
	
	
	@Before
	public void setUp() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(reminderResource)				
				.build();		
	}


	@Test
	public void createReminderTest() throws Exception {		
		Reminder reminder = new Reminder(101,"ReminderTest1",new Date(),false,1);		
		mockMvc.perform(
						 post("/reminders")
						 				   .contentType(MediaType.APPLICATION_JSON)
						 				   .content(asJsonString(reminder)) )
						.andExpect(status().isCreated());
		verify(repository,times(1)).existsById(reminder.getId());		
	}
	
	
	@Test 
	public void editReminderTest() throws Exception{
		Reminder reminder = new Reminder(101,"ReminderTest1",new Date(),false,1);	
		Optional<Reminder> reminderOpt = Optional.of(reminder);		
	    when(repository.findById(reminder.getId())).thenReturn(reminderOpt);
	    
	    mockMvc.perform(
	                     put("/reminders/{id}", reminder.getId())
	                                        .contentType(MediaType.APPLICATION_JSON)
	                                        .content(asJsonString(reminder)))
	                     .andExpect(status().isOk());	    
	    verify(repository, times(1)).save(reminder);
	    verifyNoMoreInteractions(repository);
	}
	
	
	@Test
	public void removeReminderTest() throws Exception {
		Reminder reminder = new Reminder(101,"ReminderTest1",new Date(),false,1);	
		Optional<Reminder> reminderOpt = Optional.of(reminder);		
	    when(repository.findById(reminder.getId())).thenReturn(reminderOpt);
	    
	    mockMvc.perform(
	                       delete("/reminders/{id}", reminder.getId()))
	                       .andExpect(status().isOk());	    
	    verify(repository, times(1)).deleteById(reminder.getId());
	    verifyNoMoreInteractions(repository);
	}
	
	@Test
	public void findTest() throws Exception{
		Reminder reminder = new Reminder(101,"ReminderTest1",new Date(),false,1);	
		Optional<Reminder> reminderOpt = Optional.of(reminder);		
	    when(repository.findById(1)).thenReturn(reminderOpt);
	    
	    mockMvc.perform(get("/reminders/{id}", 1)
	    										.accept(MediaType.APPLICATION_JSON_UTF8))
	            	.andExpect(status().isOk())
	            	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            	.andExpect(jsonPath("$.id", is(101)))
	            	.andExpect(jsonPath("$.name", is("ReminderTest1")));
	    verify(repository, times(1)).findById(1);
	    verifyNoMoreInteractions(repository);
	}
	
	
	
//	@Test
//	public void getAllRemindersTest() throws Exception{
//		mockMvc.perform( 
//				          get("/reminders")
//				          					 .accept(MediaType.APPLICATION_JSON_UTF8))
//						  .andExpect(status().isOk())
//						  .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
//		verify(repository, times(1)).findAll();
//		
//	}
//		
	
	public static String asJsonString(final Object obj) {
	    try {
	    	  final ObjectMapper mapper = new ObjectMapper();
	          final String jsonContent = mapper.writeValueAsString(obj);
	          return jsonContent;
	    } catch (Exception e) {
	          throw new RuntimeException(e);
	    }	    	    
	}  
	
	

}
