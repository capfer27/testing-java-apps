package com.capfer.bookapp;

import com.capfer.bookapp.dto.BookDTO;
import com.capfer.bookapp.mapper.BooksMapper;
import com.capfer.bookapp.model.Book;
import com.capfer.bookapp.service.IBookService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BookApplicationTestContainersIntegrationTests {

    //@Autowired
    //BookRepository repository;

    @Autowired
    private IBookService bookService;

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    @ServiceConnection
    private final static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:latest")
    );

    @BeforeAll
    public static void beforeAll() {
        postgresContainer.start();
    }

    @AfterAll
    public static void afterAll() {
        postgresContainer.stop();
    }

//	@Test
//	void contextLoads() {
//	}

    @BeforeEach
    void setUp() {

        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:" + port));

        List<Book> books = List.of(
                new Book(1L, "The Turn of the Screw", "Henry James", 1898),
                new Book(2L, "American Gods", "Neil Gaiman", 2001),
                new Book(3L, "Dandelion Wine", "Ray Bradbury", 1957)
        );
        bookService.saveAll(books);

        System.out.println("Books List");
        bookService.findAll().forEach(System.out::println);
    }

    @AfterEach
    void clear() {
        bookService.deleteAll();
    }


    @Test
    void shouldReturnBookById() {
        String title = "The Turn of the Screw";
        ResponseEntity<Book> response = restTemplate.getForEntity("api/books/1", Book.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(title, response.getBody().getTitle());

    }

    @Test
    void shouldFindAllBooks() {
        Book[] books = restTemplate.getForObject("api/books", Book[].class);
        assertEquals(3, books.length);
    }

    @Test
    void shouldCreateBook() {

        Book book = new Book(4L, "The Catcher in the Rye", "J. D. Salinger", 1951);
        BookDTO bookDTO = BooksMapper.toDTO(book);
        ResponseEntity<BookDTO> response = restTemplate.exchange("api/books", HttpMethod.POST, new HttpEntity<>(bookDTO), BookDTO.class);

        //BookDTO book4 = bookService.findById(4L);
        //assertEquals("J. D. Salinger", book4.author());

        int yearExpected = 1951;
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(yearExpected, response.getBody().publicationYear());

    }

}
