package com.example.library_api.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;

@Entity
@Table (name = "readers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int phoneNumber;
    private String gender;
    private LocalDate dateOfBirth;
}
