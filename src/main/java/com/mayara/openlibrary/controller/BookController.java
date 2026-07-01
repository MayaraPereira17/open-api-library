package com.mayara.openlibrary.controller;


import com.mayara.openlibrary.dto.ResponseAPI;
import com.mayara.openlibrary.model.Book;
import com.mayara.openlibrary.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService service;


    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> list() {
        return service.list();
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        return service.save(book);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        return service.update(id, book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseAPI> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ResponseAPI("Book successfully deleted!"));
    }
}
