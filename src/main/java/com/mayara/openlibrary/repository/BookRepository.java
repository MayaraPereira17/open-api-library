package com.mayara.openlibrary.repository;

import com.mayara.openlibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<Book, Long> {
}
