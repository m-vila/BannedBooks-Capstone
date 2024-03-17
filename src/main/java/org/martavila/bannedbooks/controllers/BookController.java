package org.martavila.bannedbooks.controllers;

import org.martavila.bannedbooks.controllers.dto.BookDTO;
import org.martavila.bannedbooks.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //Method is used to handle a list of books
    @GetMapping("/books")
    public String books(Model model) {
        List<BookDTO> books = bookService.findAllBooks();

        model.addAttribute("books", books);

        return "books";

    }


}
