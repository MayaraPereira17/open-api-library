package com.mayara.openlibrary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayara.openlibrary.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
 class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBook() throws Exception {
        Book book = new Book();
        book.setTitle("Romeu e Julieta");
        book.setAuthor("William Shakespeare");
        book.setPublicationYear(1597);
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Romeu e Julieta"));
    }
    @Test
    void listBooks() throws Exception {
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    void updateBook() throws Exception {
        Book book = new Book();
        book.setTitle("Romeu e Julieta");
        book.setAuthor("William Shakespeare");
        book.setPublicationYear(1597);
        String response = mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andReturn().getResponse().getContentAsString();
        Book saved = objectMapper.readValue(response, Book.class);
        book.setTitle("Hamlet");
        mockMvc.perform(put("/api/v1/books/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Hamlet"));
    }
    @Test
    void deleteBook() throws Exception {
        Book book = new Book();
        book.setTitle("Romeu e Julieta");
        book.setAuthor("William Shakespeare");
        book.setPublicationYear(1597);

        String response = mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andReturn().getResponse().getContentAsString();

        Book saved = objectMapper.readValue(response, Book.class);

        mockMvc.perform(delete("/api/v1/books/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book successfully deleted!"));
    }
    @Test
    void updateBookNotFound() throws Exception {
        Book book = new Book();
        book.setTitle("Test");
        book.setAuthor("Author");
        book.setPublicationYear(2020);
        mockMvc.perform(put("/api/v1/books/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Book not found"));
    }
    @Test
    void deleteBookNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/books/99"))
                .andExpect(status().isNotFound());
    }
}