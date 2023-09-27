package com.techreturners.recordshop.service;

import com.techreturners.recordshop.model.Album;

import java.util.List;

public interface TrRecordShopService {
    List<Album> getAllAlbums();

    List<Album> getAlbumsInStock();

    List<Album> getAlbumsByArtist(String Artist);
    public List<Album> getAlbumsByReleaseYear(int year);
}
