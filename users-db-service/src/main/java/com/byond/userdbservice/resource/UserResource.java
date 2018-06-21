package com.byond.userdbservice.resource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.byond.userdbservice.model.User;
import com.byond.userdbservice.repository.UsersRepository;
import java.util.Optional;



@RestController
@RequestMapping("/users")
public class UserResource {

	private static final Logger log = LoggerFactory.getLogger(UserResource.class);
	
	@Autowired
	UsersRepository repository;
	
	@PostMapping	
	public ResponseEntity<Void> create(@RequestBody User user) {
		
		//if ( repository.findById(user.getId()).get().getId()  == user.getId()   ) {			
		if(repository.existsById(user.getId())) {
			log.warn("User already exists in the database  :" + user.toString());
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		else {
			repository.save(user);
			log.debug("New user added :" + user.toString());
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}	
	}
	
	
	@PutMapping("/{id}")
	public void edit(@PathVariable("id") Integer id , @RequestBody User user ) {
		if( id ==  user.getId()) {
			if( repository.findById(id) != null ){
				repository.save(user);
			}			
		}
		else {
			log.warn("Id mismatch between URL #"+id+" and Body #"+user.getId()+" in  the update request");
		}
		
	}
	
	@DeleteMapping("/{id}")
	public void remove( @PathVariable("id") Integer id ) {
		repository.deleteById(id);		
	}
		
	@GetMapping("/{id}")
	public User  find(@PathVariable("id") Integer id) {
		User user =  new User();
		Optional<User> userOpt = Optional.of(user);
		userOpt = repository.findById(id) ;
		
		return userOpt.get() ;
	}

	@GetMapping("/id/{username}")
	public Integer find(@PathVariable("username") String username) {
		 return repository.findByUsername(username).getId();
	 
	}
	
	
}
