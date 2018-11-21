package com.apress.springbootrecipes.library;

import java.util.Optional;

public interface BookService {

	Iterable<Book> findAll();
	Book create(Book book);
	void remove(Book book);
	Optional<Book> find(String isbn);
}
