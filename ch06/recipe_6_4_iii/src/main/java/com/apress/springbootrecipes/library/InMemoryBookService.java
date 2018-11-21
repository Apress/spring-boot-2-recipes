package com.apress.springbootrecipes.library;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
class InMemoryBookService implements BookService {

	private final Map<String, Book> books = new ConcurrentHashMap<>();

	@Override
  @PreAuthorize("isAuthenticated()")
	public Iterable<Book> findAll() {
		return books.values();
	}

	@Override
  @PreAuthorize("hasAuthority('USER')")
	public Book create(Book book) {
		books.put(book.getIsbn(), book);
		return book;
	}

  @Override
  @PreAuthorize("hasAuthority('ADMIN') or @accessChecker.hasLocalAccess(authentication)")
  public void remove(Book book) {
    books.remove(book.getIsbn());
  }

  @Override
	public Optional<Book> find(String isbn) {
		return Optional.ofNullable(books.get(isbn));
	}
}
