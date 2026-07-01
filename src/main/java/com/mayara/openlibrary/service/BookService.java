package com.mayara.openlibrary.service;


import com.mayara.openlibrary.exception.BookNotFoundException;
import com.mayara.openlibrary.model.Book;
import com.mayara.openlibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }
    public List<Book> list() {
        return repository.findAll();
    }

    public Book save(Book book) {
        return repository.save(book);
    }

    public Book update(long id, Book bookUpdate){
        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
        book.setTitle(bookUpdate.getTitle());
        book.setAuthor(bookUpdate.getAuthor());
        book.setPublicationYear(bookUpdate.getPublicationYear());

        return repository.save(book);
    }


    public void delete(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        repository.delete(book);
    }
}
