package com.techreturners.recordshop.repository;

import com.techreturners.recordshop.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrRecordShopRepository extends JpaRepository<Album, Long> {

    public List<Album> findByStockGreaterThan(Long stock);
}
