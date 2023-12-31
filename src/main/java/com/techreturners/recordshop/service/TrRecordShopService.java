package com.techreturners.recordshop.service;

import com.techreturners.recordshop.model.Album;

import java.util.List;

public interface TrRecordShopService {
    List<Album> getAllAlbums();

    List<Album> getAlbumsInStock();

    List<Album> getAlbumsByArtist(String Artist);

    List<Album> getAlbumsByReleaseYear(int year);

    List<Album> getAlbumsByGenre(String genre);

    List<Album> getAlbumsByTitle(String title);

    Album insertAlbum(Album album);

    void updateAlbumById(Album album);

    void updateAlbumStockById(Long id, Long stock);

    void deleteAlbumById(Long id);
}
