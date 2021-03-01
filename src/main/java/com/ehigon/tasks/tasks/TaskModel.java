package com.ehigon.tasks.tasks;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter
@Setter
public class TaskModel extends RepresentationModel<TaskModel> {
    private String title;
    private String details;
    private Date date;
    private RepeatType repeatType;
}
