package com.techreturners.recordshop.controller;


import com.techreturners.recordshop.model.Album;
import com.techreturners.recordshop.model.Genre;
import com.techreturners.recordshop.service.TrRecordShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping({"/genre/{genre}"})
    public ResponseEntity<List<Album>> getAlbumsByGenre(@PathVariable Genre genre) {
        List<Album> albums = trRecordShopService.getAlbumsByGenre(genre);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping({"/title/{title}"})
    public ResponseEntity<List<Album>> getAlbumsByTitle(@PathVariable String title) {
        List<Album> albums = trRecordShopService.getAlbumsByTitle(title);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Album> addAlbum(@RequestBody Album album) {
        Album newAlbum = trRecordShopService.insertAlbum(album);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("album", "/api/v1/album/" + newAlbum.getId().toString());
        return new ResponseEntity<>(newAlbum, httpHeaders, HttpStatus.CREATED);
    }
    @PutMapping({"/{id}"})
    public ResponseEntity<Album> updateAlbumById(@PathVariable("id") Long id, @RequestBody Album album) {
        trRecordShopService.updateAlbumById(id, album);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

}
