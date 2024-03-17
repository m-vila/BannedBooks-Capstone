package org.martavila.bannedbooks.controllers;

import jakarta.validation.Valid;
import org.martavila.bannedbooks.controllers.dto.BookDTO;
import org.martavila.bannedbooks.controllers.dto.UserDTO;
import org.martavila.bannedbooks.models.Book;
import org.martavila.bannedbooks.models.User;
import org.martavila.bannedbooks.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //Method is used to handle the full list of books
    @GetMapping("/books")
    public String books(Model model) {
        List<BookDTO> books = bookService.findAllBooks();

        model.addAttribute("books", books);

        return "books";

    }

    //Method to handle the book registration form request
    @GetMapping("/book-registration")
    public String showBookRegistrationForm(Model model) {

        BookDTO book = new BookDTO();

        model.addAttribute("book", book);

        return "book-registration";
    }

    //Method to handle book registration from submit request
    @PostMapping("/book-registration/save")
    public String bookRegistration(@Valid @ModelAttribute("book") BookDTO bookDTO, BindingResult result,
                               Model model) {

        Book exitingBook = bookService.findBookByTitle(bookDTO.getTitle());

        if (exitingBook != null && exitingBook.getTitle() != null && !exitingBook.getTitle().isEmpty()) {
            result.rejectValue("title", null, "There is already an book registered under the same title");
        }

        if (result.hasErrors()) {
            model.addAttribute("book", bookDTO);

            return "book-registration";
        }

        bookService.saveBook(bookDTO);

        return "redirect:/book-registration?success";

    }


}
