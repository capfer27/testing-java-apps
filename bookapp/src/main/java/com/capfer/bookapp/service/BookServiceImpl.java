package com.capfer.bookapp.service;

import com.capfer.bookapp.dto.BookDTO;
import com.capfer.bookapp.exception.BookNotFoundException;
import com.capfer.bookapp.mapper.BooksMapper;
import com.capfer.bookapp.model.Book;
import com.capfer.bookapp.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "book-service")
@Transactional
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(BooksMapper::toDTO)
                .toList();
    }

    @Override
    public BookDTO findById(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book not found");
        }
        return BooksMapper.toDTO(optionalBook.get());
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = BooksMapper.toEntity(bookDTO);
        Book saved = bookRepository.save(book);
        return BooksMapper.toDTO(saved);
    }

    @Override
    public void saveAll(List<Book> books) {
        bookRepository.saveAll(books);
    }

    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}
