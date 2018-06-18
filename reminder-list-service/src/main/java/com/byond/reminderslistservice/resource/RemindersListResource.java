package com.byond.reminderslistservice.resource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/rest/reminders")
public class RemindersListResource {

	@Autowired
	RestTemplate restTemplate;
	
	
	@CrossOrigin
	@GetMapping("/username/{username}")
	public List<String> getRemindersList(@PathVariable("username") String username) {
		
    		
	    /**
         *  This is just to show the way services might call each other 
         */
    
		ResponseEntity<Integer> userid = restTemplate.
        		exchange("http://users-db-service/users/id/" + username, /*urls using registry*/
        				HttpMethod.GET, null,Integer.class);

        
		Integer userID= Integer.valueOf(userid.getBody().toString());
		
        ResponseEntity<List<String>> remindersNameList = restTemplate.
        		exchange("http://reminder-db-service/reminders/userid/" + userID, 
        				HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>(){});
        
       
        
        List<String> remindersList = remindersNameList.getBody();
        

        
        return remindersList;
		
		
	}
	
	
	
	
	@GetMapping("/{id}")
	public void getReminder(@PathVariable("id") Integer id) {
		
		//TODO : create method
		
	}
	
}
