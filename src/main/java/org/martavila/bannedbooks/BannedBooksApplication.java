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
import org.martavila.bannedbooks.exceptions.UnableToLoadInitDataException;
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

    // Method to initialize data when the application starts
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
            // Throw a customized exception if data initialization fails
            throw new UnableToLoadInitDataException("Unable to load the data initialization");
        }
    }

    // Method to initialize roles
    private void initRoles() throws IOException {
        // Check if the admin role exists to avoid duplicates
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        // If admin role does not exist, load roles from JSON file and save to repository
        if (adminRole == null){
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream roleStream = getClass().getResourceAsStream("/roles.json");
            List<Role> roles = objectMapper.readValue(roleStream, new TypeReference<List<Role>>() {});
            roleRepository.saveAll(roles);
        }
    }

    // Method to initialize users
    private void initUsers() throws IOException {
        // Check if admin user exists to avoid duplicates
        User adminUser = userRepository.findByEmail("admin@admin.com");

        // If admin user does not exist, load admin users from JSON file and save to repository
        if (adminUser == null){
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream userStream = getClass().getResourceAsStream("/adminUsers.json");
            List<User> users = objectMapper.readValue(userStream, new TypeReference<List<User>>() {});
            userRepository.saveAll(users);
        }
    }

    // Method to assign admin role to admin user
    private void assignUsersAdminRole() {
        // Find admin user and admin role, then assign admin role to admin user
        User adminUser = userRepository.findByEmail("admin@admin.com");
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        adminUser.setRoles(Arrays.asList(adminRole));
        userRepository.save(adminUser);
    }

    // Method to initialize genres
    private void initGenres() throws IOException {
        // Check if the novel genre exists to avoid duplicates
        Genre genre = genreRepository.findByName("novel");

        // If the novel genre does not exist, load genres from JSON file and save to repository
        if (genre == null){
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream roleStream = getClass().getResourceAsStream("/genres.json");
            List<Genre> genres = objectMapper.readValue(roleStream, new TypeReference<List<Genre>>() {});
            genreRepository.saveAll(genres);
        }
    }

    // Method to initialize books
    private void initBooks() throws IOException {
        // Check if "The Color Purple" book exists to avoid duplicates
        Book book = bookRepository.findByTitle("The Color Purple");

        // If "The Color Purple" book does not exist, load books from JSON file and save to repository
        if (book == null){
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream roleStream = getClass().getResourceAsStream("/books.json");
            List<Book> books = objectMapper.readValue(roleStream, new TypeReference<List<Book>>() {});
            bookRepository.saveAll(books);
        }
    }

    // Method to assign genres to books
    private void assignGenresToBooks() {

        // Create a mapping of genre names to Genre objects
        Map<String, Genre> genreMap = new HashMap<>();
        genreRepository.findAll().forEach(genre -> genreMap.put(genre.getName(), genre));

        // Define a mapping of book titles to their associated genre names
        Map<String, List<String>> bookGenresMapping = new HashMap<>();
        bookGenresMapping.put("Gender Queer: A Memoir", Arrays.asList("graphic novel", "autobiography", "non-fiction"));
        bookGenresMapping.put("The Color Purple", Arrays.asList("novel", "epistolary novel", "domestic fiction"));
        bookGenresMapping.put("To Kill a Mockingbird", Arrays.asList("novel", "domestic fiction", "thriller"));
        bookGenresMapping.put("Brave New World", Arrays.asList("novel", "science fiction"));

        // Assign genres to books based on the mapping
        bookGenresMapping.forEach((title, genreNames) -> {
            Book book = bookRepository.findByTitle(title);
            if (book != null) {
                // Map genre names to Genre objects and filter out any null values
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
