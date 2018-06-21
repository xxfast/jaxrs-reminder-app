package com.byond.reminderslistservice.resource;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger log = LoggerFactory.getLogger(RemindersListResource.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	
	@CrossOrigin
	@GetMapping("/{username}")
	public List<String> getRemindersList(@PathVariable("username") String username) {
		
    		
	    /**
         *  This is just to show the way services might call each other 
         *  restTemplate is annotated to use a load balancer client "@LoadBalanced"
         */
    
		ResponseEntity<Integer> userid = restTemplate.
        		exchange("http://USERS-DB-SERVICE/users/id/" + username, 
        				HttpMethod.GET, null,Integer.class);
		
        
		Integer userID= Integer.valueOf(userid.getBody().toString());
		log.debug("List Reminders Service , userid:"+userID);
		
        ResponseEntity<List<String>> remindersNameList = restTemplate.
        		exchange("http://REMINDERS-DB-SERVICE/reminders/userid/" + userID, 
        				HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>(){});
        
       
        
        List<String> remindersList = remindersNameList.getBody();
        
        log.debug("List Reminders Service :");
        for (String s : remindersList) {
        	log.debug(s);	
        }
        
        return remindersList;
		
		
	}
	
	
	
	
//	@GetMapping("/{id}")
//	public void getReminder(@PathVariable("id") Integer id) {
//		
//		//TODO : create method
//		
//	}
	
}
