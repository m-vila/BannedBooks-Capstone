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
@Table(name ="genre")
public class Genre {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Column(nullable=false, unique = true)
    String name;

    @ManyToMany(mappedBy = "genres")
    List<Book> books = new ArrayList<>();
}
