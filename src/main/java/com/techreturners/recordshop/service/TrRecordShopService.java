package com.techreturners.recordshop.service;

import com.techreturners.recordshop.model.Album;
import com.techreturners.recordshop.model.Genre;

import java.util.List;

public interface TrRecordShopService {
    List<Album> getAllAlbums();

    List<Album> getAlbumsInStock();

    List<Album> getAlbumsByArtist(String Artist);
    List<Album> getAlbumsByReleaseYear(int year);

    List<Album> getAlbumsByGenre(Genre genre);
}
