package com.example.library_api.entities;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int yearCreate;
    // Используем @ElementCollection для списка простых типов данных (String)
   @ManyToMany (fetch = FetchType.EAGER)
   @JoinTable(
           name = "books_authors",
           joinColumns = @JoinColumn(name = "book_id"),   // Убедитесь, что имя столбца совпадает с тем, что в таблице
           inverseJoinColumns = @JoinColumn(name = "author_id")
   )
   @JsonBackReference
  // Это нужно для того, чтобы в списке книг отображались авто
    private List<Author> authors;
}
