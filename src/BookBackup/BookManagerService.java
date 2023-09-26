package com.techreturners.recordshop.service;

import com.techreturners.recordshop.model.Book;

import java.util.List;

public interface BookManagerService {

    List<Book> getAllBooks();
    Book insertBook(Book book);
    Book getBookById(Long id);

    //User Story 4 - Update Book By Id Solution
    void updateBookById(Long id, Book book);

  void deleteBookById(Long id );

  public List<Book> getBooksWithIdGreaterThan10();
}
