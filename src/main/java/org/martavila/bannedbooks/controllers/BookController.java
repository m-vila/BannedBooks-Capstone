package org.martavila.bannedbooks.controllers;

import jakarta.validation.Valid;
import org.martavila.bannedbooks.controllers.dto.BookCreateDTO;
import org.martavila.bannedbooks.controllers.dto.BookDTO;
import org.martavila.bannedbooks.controllers.dto.GenreDTO;
import org.martavila.bannedbooks.models.Genre;
import org.martavila.bannedbooks.services.BookService;
import org.martavila.bannedbooks.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {
    private BookService bookService;
    private GenreService genreService;

    @Autowired
    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    //Method is used to handle the full list of books for users
    @GetMapping("/books-user-list")
    public String showListBooksUser(Model model) {
        List<BookDTO> books = bookService.findAllBooks();
        List<GenreDTO> genres = genreService.findAllGenres();

        model.addAttribute("books", books);
        model.addAttribute("genres", genres);

        return "books-user-list";

    }

    //Method is used to handle the full list of books for admin
    @GetMapping("/books-admin-list")
    public String showListBooksAdmin(Model model) {
        List<BookDTO> books = bookService.findAllBooks();
        List<GenreDTO> genres = genreService.findAllGenres();

        model.addAttribute("books", books);
        model.addAttribute("genres", genres);

        return "books-admin-list";

    }

    //Method to handle the book registration form request
    @GetMapping("/book-registration")
    public String showBookRegistrationForm(Model model) {

        BookCreateDTO createBook = new BookCreateDTO();
        List<GenreDTO> genres = genreService.findAllGenres();

        model.addAttribute("book", createBook);
        model.addAttribute("genres", genres);

        return "book-registration";
    }

    //Method to handle book registration from submit request
    @PostMapping("/book-registration/save")
    public String bookRegistration(@Valid @ModelAttribute("book") BookCreateDTO createBook, @RequestParam(value = "genres", required = false) String[] genreIds, BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book", createBook);
            return "book-registration";
        }

        bookService.saveBook(createBook, genreIds);

        return "redirect:/book-registration?success";
    }

    @PostMapping("/book-delete/{title}")
    public String deleteBook(@PathVariable String title) {
        bookService.deleteBook(title);
        return "redirect:/book-update?successDelete";
    }

    @GetMapping("/book-update")
    public String showBookUpdate(Model model) {
        List<BookDTO> books = bookService.findAllBooks();
        BookCreateDTO book = new BookCreateDTO();
        List<GenreDTO> genres = genreService.findAllGenres();
        GenreDTO genre = new GenreDTO();

        model.addAttribute("book", book);
        model.addAttribute("books", books);
        model.addAttribute("genre", genre);
        model.addAttribute("genres", genres);
        return "book-update";
    }

    @PostMapping("/book-update/save")
    public String bookUpdate(@Valid @ModelAttribute("book") BookCreateDTO bookDTO, @RequestParam(value = "genres", required = false) String[] genreIds, BindingResult result,
                                   Model model) {

        if (result.hasErrors()) {
            model.addAttribute("book", bookDTO);

            return "book-update";
        }

        bookService.saveBook(bookDTO, genreIds);

        return "redirect:/book-update?successUpdate";

    }

}
