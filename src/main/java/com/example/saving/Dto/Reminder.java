package com.example.saving.Dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
public class Reminder {
    private Integer id;
    private String name;
    private Date date;
    private Boolean isComplete;
}

