package com.backendtest.model;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@Getter
@Entity
@Setter
public class SuperUser {
    @Id
    @GeneratedValue(generator = "UUID")
    private int id;
    private String name;

    public SuperUser(String name) {
        this.name = name;


    }
}
