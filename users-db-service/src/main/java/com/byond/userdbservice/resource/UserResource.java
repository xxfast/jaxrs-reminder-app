package com.byond.userdbservice.resource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public void create(@RequestBody User user) {
		
		repository.save(user);
	}
	
	@PutMapping("/{id}")
	public void edit(@PathVariable("id") Integer id , @RequestBody User user ) {
		if( id ==  user.getId()) {
			repository.save(user);
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
	public Optional<User> find(@PathVariable("id") Integer id) {
		return repository.findById(id) ;
	}

	@GetMapping("/id/{username}")
	public Integer find(@PathVariable("username") String username) {
		Integer id = 0;
		 for ( User u :  repository.findAll()  ) {
			 System.out.println(u.toString());
			 if(u.getUsername().equals(username)) {
				 id = u.getId();
			 }

		 }
		 return id;
	}
}
