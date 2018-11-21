package com.apress.springbootrecipes.library;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Book {

	private final String isbn;
	private final String title;
	private final List<String> authors = new ArrayList<>();

	@JsonCreator
	public Book(
					@JsonProperty("isbn") String isbn,
					@JsonProperty("title") String title,
					@JsonProperty("authors") String... authors) {
		this.isbn = isbn;
		this.title = title;
		this.authors.addAll(Arrays.asList(authors));
	}

	public String getIsbn() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getAuthors() {
		return Collections.unmodifiableList(authors);
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Book book = (Book) o;
		return Objects.equals(isbn, book.isbn);
	}

	@Override
	public int hashCode() {

		return Objects.hash(isbn);
	}

	@Override
	public String toString() {
		return String.format(
				"Book [isbn=%s, title=%s, authors=%s]",
				this.isbn, this.title, this.authors);
	}
}
