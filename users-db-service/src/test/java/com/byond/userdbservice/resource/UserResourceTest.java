package com.byond.userdbservice.resource;

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
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.byond.userdbservice.model.User;
import com.byond.userdbservice.repository.UsersRepository;
import com.byond.userdbservice.resource.UserResource;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
public class UserResourceTest {

	private MockMvc mockMvc;
	
	@Mock
	UsersRepository repository;
	
	@InjectMocks
	private UserResource userResource;
	
	
	@Before
	public void setUp() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(userResource)				
				.build();		
	}
	
	
	@Test
	public void createUserTest() throws Exception {		
		User user = new User(101,"Sajid","AAABBBCCC",true);
		when(repository.existsById(user.getId())).thenReturn(false); 
	    		
		mockMvc.perform(
						 post("/users")
						 				   .contentType(MediaType.APPLICATION_JSON)
						 				   .content(asJsonString(user)) )
						.andExpect(status().isCreated());
		verify(repository,times(1)).existsById(user.getId());		
	}
	
	
	@Test 
	public void editUserTest() throws Exception{
		User user = new User(101,"Sajid","AAABBBCCC",true);
		Optional<User> userOpt = Optional.of(user);
	    when(repository.findById(user.getId())).thenReturn(userOpt); 
	    		
	    mockMvc.perform(
	                     put("/users/{id}", user.getId())
	                                        .contentType(MediaType.APPLICATION_JSON)
	                                        .content(asJsonString(user)))
	                     .andExpect(status().isOk());	    
	    
	}
	
	
	@Test
	public void removeUserTest() throws Exception {
		User user = new User(101,"Sajid","AAABBBCCC",true);
		Optional<User> reminderOpt = Optional.of(user);		
	    when(repository.findById(user.getId())).thenReturn(reminderOpt);
	    
	    mockMvc.perform(
	                       delete("/users/{id}", user.getId()))
	                       .andExpect(status().isOk());	    
	    verify(repository, times(1)).deleteById(user.getId());
	    verifyNoMoreInteractions(repository);
	}
	
	@Test
	public void findUserTest() throws Exception{
		User user = new User(101,"Sajid","AAABBBCCC",true);
		Optional<User> reminderOpt = Optional.of(user);		
	    when(repository.findById(user.getId())).thenReturn(reminderOpt);
	    
	    mockMvc.perform(get("/users/{id}", user.getId())
	    										.accept(MediaType.APPLICATION_JSON_UTF8))
	            	.andExpect(status().isOk())
	            	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            	.andExpect(jsonPath("$.id", is(101)))
	            	.andExpect(jsonPath("$.username", is("Sajid")));
	    verify(repository, times(1)).findById(user.getId());
	    
	}
	
	
	
	@Test
	public void findUserIdTest() throws Exception{
		User user = new User(101,"Sajid","AAABBBCCC",true);			
	    when(repository.findByUsername(user.getUsername())).thenReturn(user);
	    
	    mockMvc.perform(get("/users/id/{username}", user.getUsername())
	    										.accept(MediaType.APPLICATION_JSON_UTF8))
	            	.andExpect(status().isOk())
	            	.andExpect(content().string("101"));
	            	//.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            	//.andExpect(jsonPath("$.id", is(101)))
	            	//.andExpect(jsonPath("$.username", is("Sajid")));
	    verify(repository, times(1)).findByUsername(user.getUsername());
	    verifyNoMoreInteractions(repository);
	}
	
	
	
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
