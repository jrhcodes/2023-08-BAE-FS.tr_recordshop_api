package com.techreturners.recordshop.repository;

import com.techreturners.recordshop.model.Book;
import com.techreturners.recordshop.model.BookGenre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookManagerRepositoryTests {

    @Autowired
    private BookManagerRepository bookManagerRepository;

    @Test
    public void testFindAllBooksReturnsBooks() {

        Book book = new Book(1L, "Book One", "This is the description for Book One", "Person One", BookGenre.Education);
        bookManagerRepository.save(book);

        Iterable<Book> books = bookManagerRepository.findAll();
        assertThat(books).hasSize(1);

    }

    @Test
    public void testCreatesAndFindBookByIdReturnsBook() {

        Book book = new Book(1L, "Book Two", "This is the description for Book Two", "Person Two", BookGenre.Fantasy);
        bookManagerRepository.save(book);

        var bookById = bookManagerRepository.findById(book.getId());
        assertThat(bookById).isNotNull();

    }

}
