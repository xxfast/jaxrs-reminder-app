package com.example.saving.repository;

import com.example.saving.entity.ReminderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RemindersRepository extends JpaRepository<ReminderEntity, Long> {

    List<ReminderEntity> findAllByDateAfterAndDateBefore(LocalDateTime start, LocalDateTime end);
}
