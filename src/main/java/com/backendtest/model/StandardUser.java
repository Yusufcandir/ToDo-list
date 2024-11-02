package com.backendtest.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Entity
@Getter
@Setter
public class StandardUser {
    @Id
    @GeneratedValue(generator = "UUID")
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private TaskType taskType;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "createdBy")
    private List<Task> tasks = new ArrayList<>();

    public StandardUser(String name, TaskType taskType) {
        this.name = name;
        this.taskType = taskType;
        this.tasks = new ArrayList<>();
    }


}
