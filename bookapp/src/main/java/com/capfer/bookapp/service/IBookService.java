package com.capfer.bookapp.service;

import com.capfer.bookapp.dto.BookDTO;
import com.capfer.bookapp.model.Book;

import java.util.List;

public interface IBookService {

    List<BookDTO> findAll();
    BookDTO findById(long id);
    BookDTO addBook(BookDTO bookDTO);
    void saveAll(List<Book> books);
    void deleteAll();
}
