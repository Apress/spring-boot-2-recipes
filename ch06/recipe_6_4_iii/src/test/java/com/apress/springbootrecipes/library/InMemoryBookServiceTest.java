package com.apress.springbootrecipes.library;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryBookServiceTest {

	private InMemoryBookService service;

	@Before
	public void setup() {
		this.service = new InMemoryBookService();
	}

	@Test
	public void shouldReturnEmptyOptionalWhenNotFound() {
		assertThat(service.find("1234")).isEmpty();
	}

	@Test
	public void shouldFindAfterCreation() {
		assertThat(service.findAll()).isEmpty();

		Book book = new Book("1234", "Spring 5 Recipes", "Marten Deinum", "Josh Long");
		assertThat(service.create(book)).isEqualTo(book);
		assertThat(service.find(book.getIsbn())).contains(book);

	}

	@Test
	public void shouldReturnAllBooksAfterCreation() {
		assertThat(service.findAll()).isEmpty();

		Book book1 = new Book("1234", "Spring 5 Recipes", "Marten Deinum", "Josh Long");
		Book book2 = new Book("4321", "Spring Boot 2 Recipes", "Marten Deinum");
		service.create(book1);
		service.create(book2);

		assertThat(service.findAll()).containsExactly(book1, book2);


	}

}
