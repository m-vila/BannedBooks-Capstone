package org.martavila.bannedbooks.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name ="book")
public class Book {
    @Id
    String isbn;

    @Column(nullable=false)
    String title;

    @Column(nullable=false)
    String author;

    @Column(nullable=false)
    Integer year;

    @ManyToMany(mappedBy = "books")
    List<User> users = new ArrayList<>();

    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable (name ="book_genre",
            joinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "isbn")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id ", referencedColumnName = "id"
            )})
    public List<Genre> genres = new ArrayList<>();

    public Book(String isbn, String title, String author, Integer year, List<User> users, List<Genre> genres) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.users = users;
        this.genres = genres;
    }
}
