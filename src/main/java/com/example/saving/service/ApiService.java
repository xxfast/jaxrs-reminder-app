package com.example.saving.service;

import com.example.saving.Dto.Reminder;
import com.example.saving.entity.ReminderEntity;
import com.example.saving.repository.RemindersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApiService {

    @Autowired
    private RemindersRepository repo;

    public Reminder createReminder(Reminder reminder)  {
        ReminderEntity entity = new ReminderEntity();
        BeanUtils.copyProperties(reminder, entity);
        return Optional.ofNullable(repo.save(entity)).map(a -> Reminder.builder()
                .id(a.getId())
                .name(a.getName())
                .date(a.getDate())
                .isComplete(a.getIsComplete())
                .build())
                .orElse(null);
    }

    public Reminder editReminder(Long id, Reminder reminder) {
        return Optional.ofNullable(id)
                .map(a -> {
                    reminder.setId(a.intValue());
                    return this.createReminder(reminder);
                }).orElse(null);
    }

    public void deleteReminder(Long id) {
        repo.deleteById(id);
    }

    public Reminder findReminder(Long id) {
        return repo.findById(id).map(a -> Reminder.builder()
                .id(a.getId())
                .name(a.getName())
                .date(a.getDate())
                .isComplete(a.getIsComplete())
                .build()).orElse(null);
    }

    public List<Reminder> findAllReminders() {
        return repo.findAll().stream().map(a -> Reminder.builder()
                .id(a.getId())
                .name(a.getName())
                .date(a.getDate())
                .isComplete(a.getIsComplete())
                .build()).collect(Collectors.toList());
    }

    public List<Reminder> findReminderRange(LocalDateTime from, LocalDateTime to) {
        return repo.findAllByDateAfterAndDateBefore(from, to).stream().map(a -> Reminder.builder()
                .id(a.getId())
                .name(a.getName())
                .date(a.getDate())
                .isComplete(a.getIsComplete())
                .build()).collect(Collectors.toList());
    }

    public String countReminder() {
        return String.valueOf(repo.count());
    }
}
