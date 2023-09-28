package com.techreturners.recordshop.service;

import com.techreturners.recordshop.model.Book;
import com.techreturners.recordshop.repository.BookManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookManagerServiceImpl implements BookManagerService {

    @Autowired
    BookManagerRepository bookManagerRepository;

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        bookManagerRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public Book insertBook(Book book) {
        return bookManagerRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookManagerRepository.findById(id).get();
    }

    //User Story 4 - Update Book By Id Solution
    @Override
    public void updateBookById(Long id, Book book) {
        Book retrievedBook = bookManagerRepository.findById(id).get();

        retrievedBook.setTitle(book.getTitle());
        retrievedBook.setDescription(book.getDescription());
        retrievedBook.setAuthor(book.getAuthor());
        retrievedBook.setBookGenre(book.getBookGenre());

        bookManagerRepository.save(retrievedBook);
    }

    @Override
    public void deleteBookById(Long id) {
        bookManagerRepository.deleteById(id);
    }

    @Override
    public List<Book> getBooksWithIdGreaterThan10(){
        return bookManagerRepository.findByIdGreaterThan(10L);
    };

}
