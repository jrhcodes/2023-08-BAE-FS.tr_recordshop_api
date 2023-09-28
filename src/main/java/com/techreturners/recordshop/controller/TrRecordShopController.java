package com.techreturners.recordshop.controller;


import com.techreturners.recordshop.exceptions.AlbumNotFoundException;
import com.techreturners.recordshop.model.Album;
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

    @GetMapping("/info")
    public ResponseEntity<String> getInfoResponse() {
        return new ResponseEntity<>("Hello and welcome to TR Record Shop!", HttpStatus.OK);
    }

    @GetMapping("/instock")
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
    public ResponseEntity<List<Album>> getAlbumsByGenre(@PathVariable String genre) {
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
        // album.setId(id);
        trRecordShopService.updateAlbumById(id, album);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @PutMapping({"/stock/{id}"})
    public ResponseEntity<String> updateAlbumStockById(@PathVariable("id") Long id, @RequestBody Long stock) {
        trRecordShopService.updateAlbumStockById(id, stock);
        return new ResponseEntity<>(String.valueOf(stock), HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> deleteAlbumById(@PathVariable("id") Long id) {

        try {
            trRecordShopService.deleteAlbumById(id);
        } catch (AlbumNotFoundException e) {
            return new ResponseEntity<>(String.valueOf(id), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(String.valueOf(id), HttpStatus.OK);
    }
}
