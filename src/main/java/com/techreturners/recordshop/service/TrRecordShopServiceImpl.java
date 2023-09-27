package com.techreturners.recordshop.service;

import com.techreturners.recordshop.model.Album;
import com.techreturners.recordshop.model.Genre;
import com.techreturners.recordshop.repository.TrRecordShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrRecordShopServiceImpl implements TrRecordShopService {

    @Autowired
    TrRecordShopRepository trRecordShopRepository;

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = trRecordShopRepository.findAll();
        System.out.println(albums);
        return albums;
    }

    @Override
    public List<Album> getAlbumsInStock() {
        List<Album> albums = trRecordShopRepository.findByStockGreaterThan(0L);
        System.out.println(albums);
        return albums;
    }

    public List<Album> getAlbumsByArtist(String artist) {
        return trRecordShopRepository.findAllAlbumsByArtistContainingIgnoreCase(artist);
    }

    public List<Album> getAlbumsByReleaseYear(int year) {
        return trRecordShopRepository.findAllAlbumsByReleaseYear(year);
    }

    public List<Album> getAlbumsByGenre(Genre genre) {
        return trRecordShopRepository.findAllAlbumsByGenre(genre);
    }

    public List<Album> getAlbumsByTitle(String title) {
        return trRecordShopRepository.findAllAlbumsByTitleContainingIgnoreCase( title);
    }

}
