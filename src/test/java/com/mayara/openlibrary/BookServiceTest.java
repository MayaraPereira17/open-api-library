package com.mayara.openlibrary;

import com.mayara.openlibrary.exception.BookNotFoundException;
import com.mayara.openlibrary.model.Book;
import com.mayara.openlibrary.repository.BookRepository;
import com.mayara.openlibrary.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository repository;
    @InjectMocks
    private BookService service;

    @Test
    void saveBook() {
        Book book = new Book();
        book.setTitle("Romeu e Julieta");
        book.setAuthor("William Shakespeare");
        book.setPublicationYear(2008);
        Book saved = new Book();
        saved.setId(1L);
        saved.setTitle(book.getTitle());
        saved.setAuthor(book.getAuthor());
        saved.setPublicationYear(book.getPublicationYear());
        when(repository.save(book)).thenReturn(saved);
        Book result = service.save(book);
        assertNotNull(result.getId());
        verify(repository).save(book);
    }

    @Test
    void listBooks() {
        when(repository.findAll()).thenReturn(List.of(new Book()));
        assertEquals(1, service.list().size());
    }

    @Test
    void updateBook() {
        Book existing = new Book();
        existing.setId(1L);
        existing.setTitle("Old");
        existing.setAuthor("Old Author");
        existing.setPublicationYear(2004);
        Book update = new Book();
        update.setTitle("New");
        update.setAuthor("New Author");
        update.setPublicationYear(2024);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);
        Book result = service.update(1L, update);
        assertEquals("New", result.getTitle());
        assertEquals("New Author", result.getAuthor());
        assertEquals(2024, result.getPublicationYear());
    }

    @Test
    void updateBookNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class,
                () -> service.update(99L, new Book()));
    }

    @Test
    void deleteBook() {
        Book book = new Book();
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        service.delete(1L);
        verify(repository).delete(book);
    }

    @Test
    void deleteBookNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class,
                () -> service.delete(99L));
    }
}