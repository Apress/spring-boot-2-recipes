package com.apress.springbootrecipes.library.web;

import com.apress.springbootrecipes.library.Book;
import com.apress.springbootrecipes.library.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/books.html")
	public String all(Model model) {
		model.addAttribute("books", bookService.findAll());
		return "books/list";
	}

	@GetMapping(value = "/books.html", params = "isbn")
	public String get(@RequestParam("isbn") String isbn, Model model) {

		bookService.find(isbn)
						.ifPresent(book -> model.addAttribute("book", book));

		return "books/details";
	}

	@PostMapping("/books")
	public Book create(@ModelAttribute Book book) {
		return bookService.create(book);
	}
}
