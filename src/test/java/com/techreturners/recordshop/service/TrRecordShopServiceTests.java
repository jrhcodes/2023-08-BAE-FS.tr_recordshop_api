package com.techreturners.recordshop.service;

import com.techreturners.recordshop.exceptions.AlbumNotFoundException;
import com.techreturners.recordshop.model.Album;
import com.techreturners.recordshop.repository.TrRecordShopRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DataJpaTest
public class TrRecordShopServiceTests {

    @Mock
    private TrRecordShopRepository mockTrRecordShopRepository;

    @InjectMocks
    private TrRecordShopServiceImpl TrRecordShopServiceImpl;

    @Test
    public void testGetAllAlbumsReturnsListOfAlbums() {

        List<Album> albums = new ArrayList<>();
        albums.add(new Album(1L, "Album One", "This is the description for Album One", 1901, "Rock", 10L));
        albums.add(new Album(2L, "Album Two", "This is the description for Album Two", 1973, "Pop", 5L));
        albums.add(new Album(3L, "Album Three", "This is the description for Album Three", 2023, "Jazz", 0L));

        when(mockTrRecordShopRepository.findAll()).thenReturn(albums);

        List<Album> actualResult = TrRecordShopServiceImpl.getAllAlbums();

        assertThat(actualResult).hasSize(3);
        assertThat(actualResult).isEqualTo(albums);
    }

    @Test
    public void testAddAlbum() {

        var album = new Album(4L, "Album Four", "This is the description for Album Four", 1263, "Rock", 0L);

        when(mockTrRecordShopRepository.save(new Album(null, album.getTitle(), album.getArtist(), album.getReleaseYear(), album.getGenre(), album.getStock()))).thenReturn(album);

        Album actualResult = TrRecordShopServiceImpl.insertAlbum(album);

        assertThat(actualResult.getTitle()).isEqualTo(album.getTitle());
    }

    @Test
    public void testGetAlbumById() {

        Album[] album = {new Album(5L, "Album Five", "Artist 4", 1263, "Rock", 0L)};

        when(mockTrRecordShopRepository.findAllAlbumsByArtistContainingIgnoreCase(album[0].getArtist())).thenReturn(List.of(album));

        List<Album> actualResult = TrRecordShopServiceImpl.getAlbumsByArtist(album[0].getArtist());

        assertThat(actualResult).isEqualTo(List.of(album));
    }

    @Test
    public void testUpdateAlbumById() {

        Long albumId = 5L;
        var album = new Album(albumId, "Album Five", "This is the description for Album Five", 2001, "Rock", 0L);

        when(mockTrRecordShopRepository.findById(albumId)).thenReturn(Optional.of(album));
        when(mockTrRecordShopRepository.save(album)).thenReturn(album);

        TrRecordShopServiceImpl.updateAlbumById(album);

        verify(mockTrRecordShopRepository, times(1)).save(album);
    }


    @Test
    public void testDeleteAlbumById() {
        Album album = new Album(5L, "Album Five", "This is the description for Album Five", 2001, "Rock", 0L);
        when(mockTrRecordShopRepository.findById(album.getId())).thenReturn(Optional.of(album));
        TrRecordShopServiceImpl.deleteAlbumById(album.getId());
        verify(mockTrRecordShopRepository, times(1)).delete(album);
    }

    @Test
    public void testDeleteAlbumByIdNotFound() {
        Album album = new Album(5L, "Album Five", "This is the description for Album Five", 2001, "Rock", 0L);
        when(mockTrRecordShopRepository.findById(album.getId())).thenReturn(Optional.empty());
        assertThrows(AlbumNotFoundException.class, () -> TrRecordShopServiceImpl.deleteAlbumById(album.getId()));
        verify(mockTrRecordShopRepository, times(0)).delete(album);
    }

}
