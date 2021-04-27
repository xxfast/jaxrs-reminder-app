package com.byond.resource;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byond.model.Reminder;

@RestController
@RequestMapping("/reminders")
public class ReminderResource {
	
	
	private static final Logger log = LoggerFactory.getLogger(ReminderResource.class);
	

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.byond.persistent.unit");
	EntityManager repository = emf.createEntityManager();
	

	
		
	//@Transactional
	@PostMapping	
	public void create( @RequestBody Reminder reminder) {		
		log.debug(reminder.toString());
		repository.getTransaction().begin();	
		repository.persist(reminder);
		repository.getTransaction().commit();
	}
		
	@PutMapping("/{id}")
	public void edit(@PathVariable("id") ObjectId id , @RequestBody Reminder reminder) {
		if( id ==  reminder.getId()) {
			repository.getTransaction().begin();			
			repository.persist(reminder);
			repository.getTransaction().commit();	
		}
		else {
			log.warn("Id mismatch between URL #"+id+" and Body #"+reminder.getId()+" in  the update request");
		}
		
	}
	
	
	@DeleteMapping("/{id}")
	public void remove( @PathVariable("id") ObjectId id ) {
		Reminder reminder = (Reminder) repository.createNamedQuery("Reminder.findById").setParameter(1, id).getSingleResult();
		repository.getTransaction().begin();	
		repository.remove(reminder);
		repository.getTransaction().commit();	
		
	}
		
	@GetMapping("/{id}")
	public Reminder find(@PathVariable("id") ObjectId id) {
		Reminder reminder = (Reminder) repository.createNamedQuery("Reminder.findById").setParameter(1, id).getSingleResult();
		return reminder;
	}
	
	
	
	@GetMapping	
	public List<Reminder>  getReminders() {
			
			List<Reminder> reminder =  new ArrayList<>();
			
			reminder =  (List<Reminder>) repository.createNamedQuery("Reminder.findAll",Reminder.class).getResultList();
			
			
			return reminder;
			
		}
	


	


}
