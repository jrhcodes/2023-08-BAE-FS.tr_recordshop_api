package com.techreturners.recordshop.controller;


import com.techreturners.recordshop.model.Album;
import com.techreturners.recordshop.service.TrRecordShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/album")
public class TrRecordShopController {

    @Autowired
    TrRecordShopService trRecordShopService;

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albums = trRecordShopService.getAllAlbums();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping({"/instock"})
    public ResponseEntity<List<Album>> getAllAlbumsInStock() {
        List<Album> albums = trRecordShopService.getAlbumsInStock();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping({"/artist/{artist}"})
    public ResponseEntity<List<Album>> getAlbumsByArtist(@PathVariable String artist) {
        List<Album> albums = trRecordShopService.getAlbumsByArtist(artist);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping({"/year/{year}"})
    public ResponseEntity<List<Album>> getAlbumsByReleaseYear(@PathVariable int year) {
        List<Album> albums = trRecordShopService.getAlbumsByReleaseYear(year);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }
}
