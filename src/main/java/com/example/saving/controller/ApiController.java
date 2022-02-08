package com.example.saving.controller;

import com.example.saving.Dto.Reminder;
import com.example.saving.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/reminder")
public class ApiController {

    @Autowired
    private ApiService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder) {
        return ResponseEntity.ok(service.createReminder(reminder));
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Reminder> editReminder(@PathVariable("id") Long id, @RequestBody Reminder reminder) {
        return ResponseEntity.ok(service.editReminder(id, reminder));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteReminder(@PathVariable("id") Long id) {
        service.deleteReminder(id);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reminder> findReminder(@PathVariable("id") Long id) {
    	Reminder reminder = service.findReminder(id);
        if (reminder != null) {
    		return ResponseEntity.ok(reminder);
    	}
    	
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reminder>> findAllReminders() {
        return ResponseEntity.ok(service.findAllReminders());
    }

    @GetMapping(path = "/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reminder>> findReminderRange(@PathVariable("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                            @PathVariable("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(service.findReminderRange(from, to));
    }

    @GetMapping(path = "/count", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> countReminder() {
        return ResponseEntity.ok(service.countReminder());
    }

}
