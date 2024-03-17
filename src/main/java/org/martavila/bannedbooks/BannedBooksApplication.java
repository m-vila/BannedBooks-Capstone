package org.martavila.bannedbooks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.martavila.bannedbooks.models.Book;
import org.martavila.bannedbooks.models.Genre;
import org.martavila.bannedbooks.models.Role;
import org.martavila.bannedbooks.models.User;
import org.martavila.bannedbooks.repositories.BookRepository;
import org.martavila.bannedbooks.repositories.GenreRepository;
import org.martavila.bannedbooks.repositories.UserRepository;
import org.martavila.bannedbooks.repositories.RoleRepository;
import org.martavila.bannedbooks.exceptions.UnableToLoadAdminUsersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class BannedBooksApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    public static void main(String[] args) {
        SpringApplication.run(BannedBooksApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            initRoles();
            initUsers();
            assignUsersAdminRole();
            initGenres();
            initBooks();
            assignGenresToBooks();
        } catch (Exception e){
            throw new UnableToLoadAdminUsersException("Unable to load the data initialization");
        }
    }

    private void initRoles() throws IOException {

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        if (adminRole == null){
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream roleStream = getClass().getResourceAsStream("/roles.json");
            List<Role> roles = objectMapper.readValue(roleStream, new TypeReference<List<Role>>() {});

            roleRepository.saveAll(roles);
        }
    }

    private void initUsers() throws IOException {
        User adminUser = userRepository.findByEmail("admin@admin.com");
        if (adminUser == null){
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream userStream = getClass().getResourceAsStream("/adminUsers.json");
            List<User> users = objectMapper.readValue(userStream, new TypeReference<List<User>>() {});

            userRepository.saveAll(users);
        }
    }

    private void assignUsersAdminRole() {
        User adminUser = userRepository.findByEmail("admin@admin.com");
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        adminUser.setRoles(Arrays.asList(adminRole));
        userRepository.save(adminUser);
    }

    private void initGenres() throws IOException {

        Genre genre = genreRepository.findByName("novel");

        if (genre == null){
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream roleStream = getClass().getResourceAsStream("/genres.json");
            List<Genre> genres = objectMapper.readValue(roleStream, new TypeReference<List<Genre>>() {});

            genreRepository.saveAll(genres);
        }
    }

    private void initBooks() throws IOException {

        Book book = bookRepository.findByTitle("The Color Purple");
        if (book == null){
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream roleStream = getClass().getResourceAsStream("/books.json");
            List<Book> books = objectMapper.readValue(roleStream, new TypeReference<List<Book>>() {});

            bookRepository.saveAll(books);
        }
    }

    private void assignGenresToBooks() {
        Map<String, Genre> genreMap = new HashMap<>();
        genreRepository.findAll().forEach(genre -> genreMap.put(genre.getName(), genre));

        Map<String, List<String>> bookGenresMapping = new HashMap<>();
        bookGenresMapping.put("Gender Queer: A Memoir", Arrays.asList("graphic novel", "autobiography", "non-fiction"));
        bookGenresMapping.put("The Color Purple", Arrays.asList("novel", "epistolary novel", "domestic fiction"));
        bookGenresMapping.put("To Kill a Mockingbird", Arrays.asList("novel", "domestic fiction", "thriller"));
        bookGenresMapping.put("Brave New World", Arrays.asList("novel", "science fiction"));

        bookGenresMapping.forEach((title, genreNames) -> {
            Book book = bookRepository.findByTitle(title);
            if (book != null) {
                List<Genre> genres = genreNames.stream()
                        .map(genreMap::get)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                book.setGenres(genres);
                bookRepository.save(book);
            } else {
                System.out.println("Book not found: " + title);
            }
        });
    }
}
