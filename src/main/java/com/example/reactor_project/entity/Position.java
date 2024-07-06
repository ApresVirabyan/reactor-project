package com.example.reactor_project.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@Document(collation = "positions")
public class Position {

    @Id
    private String id;

    @NotBlank
    @Size(max = 150)
    private String positionName;

    private String description;

    @NotNull
    private Date createdAt = new Date();

}
