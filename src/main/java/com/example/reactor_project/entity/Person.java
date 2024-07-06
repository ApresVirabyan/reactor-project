package com.example.reactor_project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "persons")
public class Person {

    @Id
    private int id;

    private Sex sex;
    private String firstName;
    private String lastName;
    private String age;
    private String interests;
    private String email;
}
