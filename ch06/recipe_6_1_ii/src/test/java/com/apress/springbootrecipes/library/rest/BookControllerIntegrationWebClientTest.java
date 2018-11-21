package com.apress.springbootrecipes.library.rest;

import com.apress.springbootrecipes.library.Book;
import com.apress.springbootrecipes.library.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.security.user.password=s3cr3t")
@AutoConfigureWebTestClient
public class BookControllerIntegrationWebClientTest {

  @Autowired
  private WebTestClient webTestClient;

	@MockBean
	private BookService bookService;

	@Test
	public void shouldReturnListOfBooks() throws Exception {

		when(bookService.findAll()).thenReturn(Arrays.asList(
				new Book("123", "Spring 5 Recipes", "Marten Deinum", "Josh Long"),
				new Book("321", "Pro Spring MVC", "Marten Deinum", "Colin Yates")));

		webTestClient
            .get()
              .uri("/books")
              .headers( httpHeaders -> httpHeaders.setBasicAuth("user", "s3cr3t"))
            .exchange()
              .expectStatus().isOk()
              .expectBodyList(Book.class).hasSize(2);

	}


//	@Test
//	public void shouldReturn404WhenBookNotFound() throws Exception {
//
//		when(bookService.find(anyString())).thenReturn(Optional.empty());
//
//		mockMvc.perform(get("/books/123"))
//				.andExpect(status().isNotFound());
//	}
//
//	@Test
//	public void shouldReturnBookWhenFound() throws Exception {
//
//		when(bookService.find(anyString())).thenReturn(
//						Optional.of(new Book("123", "Spring 5 Recipes", "Marten Deinum", "Josh Long")));
//
//		mockMvc.perform(get("/books/123"))
//				.andExpect(status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.isbn", Matchers.equalTo("123")))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.equalTo("Spring 5 Recipes")));
//	}
//
//	@Test
//  public void shouldAddBook() throws Exception {
//
//	  when(bookService.create(any(Book.class))).thenReturn(new Book("123456789", "Test Book Stored", "T. Author"));
//
//	  mockMvc.perform(post("/books")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content("{ \"isbn\" : \"123456789\"}, \"title\" : \"Test Book\", \"authors\" : [\"T. Author\"]")
//            .with(csrf()))
//            .andDo(MockMvcResultHandlers.print())
//            .andExpect(status().isCreated())
//            .andExpect(header().string("Location", "http://localhost/books/123456789"));
//  }

}
