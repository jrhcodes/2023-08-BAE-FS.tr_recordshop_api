package com.techreturners.recordshop.repository;

import com.techreturners.recordshop.model.Album;
import com.techreturners.recordshop.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrRecordShopRepository extends JpaRepository<Album, Long> {

    public List<Album> findByStockGreaterThan(Long stock);
    public List<Album> findAllAlbumsByArtistContainingIgnoreCase(String artist);
    public List<Album> findAllAlbumsByReleaseYear( int year);
    public List<Album> findAllAlbumsByGenre( Genre genre);
}

