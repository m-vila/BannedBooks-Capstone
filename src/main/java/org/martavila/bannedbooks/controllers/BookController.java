package org.martavila.bannedbooks.controllers;

import jakarta.validation.Valid;
import org.martavila.bannedbooks.controllers.dto.BookCreateDTO;
import org.martavila.bannedbooks.controllers.dto.BookReadDTO;
import org.martavila.bannedbooks.controllers.dto.GenreDTO;
import org.martavila.bannedbooks.services.BookService;
import org.martavila.bannedbooks.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {
    private BookService bookService;
    private GenreService genreService;

    @Autowired
    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    // Method to handle displaying the full list of books for users
    @GetMapping("/books-user-list")
    public String showListBooksUser(Model model) {
        List<BookReadDTO> books = bookService.findAllBooks();
        List<GenreDTO> genres = genreService.findAllGenres();

        model.addAttribute("books", books);
        model.addAttribute("genres", genres);

        return "books-user-list";

    }

    // Method to handle displaying the full list of books for admin users
    @GetMapping("/books-admin-list")
    public String showListBooksAdmin(Model model) {
        List<BookReadDTO> books = bookService.findAllBooks();
        List<GenreDTO> genres = genreService.findAllGenres();

        model.addAttribute("books", books);
        model.addAttribute("genres", genres);

        return "books-admin-list";

    }

    // Method to handle the book registration form request
    @GetMapping("/book-registration")
    public String showBookRegistrationForm(Model model) {

        BookCreateDTO book = new BookCreateDTO();
        List<GenreDTO> genres = genreService.findAllGenres();

        model.addAttribute("book", book);
        model.addAttribute("genres", genres);

        return "book-registration";
    }

    // Method to handle the book registration form submission
    @PostMapping("/book-registration/save")
    public String bookRegistration(@Valid @ModelAttribute("book") BookCreateDTO book, @RequestParam(value = "genres", required = false) String[] genreIds, BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book", book);
            return "book-registration";
        }

        bookService.saveBook(book, genreIds);

        return "redirect:/book-registration?success";
    }

    // Method to handle deleting a book
    @PostMapping("/book-delete/{isbn}")
    public String deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
        return "redirect:/book-update?successDelete";
    }

    // Method to show the book update form
    @GetMapping("/book-update")
    public String showBookUpdate(Model model) {
        List<BookReadDTO> books = bookService.findAllBooks();
        BookCreateDTO book = new BookCreateDTO();
        List<GenreDTO> genres = genreService.findAllGenres();

        model.addAttribute("book", book);
        model.addAttribute("books", books);
        model.addAttribute("genres", genres);
        return "book-update";
    }

    // Method to handle the book update form submission
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
