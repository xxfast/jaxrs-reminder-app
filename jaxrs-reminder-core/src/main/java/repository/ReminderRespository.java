package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.Reminders;

public interface ReminderRespository extends JpaRepository<Reminders,Long> {
    @Override
    List<Reminders> findAll();
    
}
