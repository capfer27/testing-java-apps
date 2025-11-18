package com.capfer.bookapp.mapper;

import com.capfer.bookapp.dto.BookDTO;
import com.capfer.bookapp.model.Book;

import java.util.UUID;

public final class BooksMapper {

    public static BookDTO toDTO(Book book) {
        BookDTO bookDTO = new BookDTO(
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear()
        );
        return bookDTO;
    }

    public static Book toEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setAuthor(bookDTO.author());
        book.setTitle(bookDTO.title());
        book.setPublicationYear(bookDTO.publicationYear());
        return book;
    }
}
