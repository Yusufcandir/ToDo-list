package com.backendtest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(generator = "UUID")
    private int id;
    private String name;
    private String description;
    private Date deadline;
    private Date createdAt;

    private int createdBy;

    public Task(String name, String description, Date deadline, Date createdAt, int createdBy) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }


}
