package com.techreturners.recordshop.repository;

import com.techreturners.recordshop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookManagerRepository extends JpaRepository<Book, Long> {
    public List<Book> findByIdGreaterThan(Long stock);
}
