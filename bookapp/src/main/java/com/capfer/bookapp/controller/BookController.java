package com.capfer.bookapp.controller;

import com.capfer.bookapp.dto.BookDTO;
import com.capfer.bookapp.service.IBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/books")
    public ResponseEntity<List<BookDTO>> getAll() {
        List<BookDTO> bookDTOS = bookService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }

    @GetMapping(path = "/books/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable long id) {
        BookDTO bookDTO = bookService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookDTO);
    }

    @PostMapping(value = "/books")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO addedBook = bookService.addBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addedBook);
    }
}
