package com.techreturners.recordshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techreturners.recordshop.exceptions.AlbumNotFoundException;
import com.techreturners.recordshop.model.Album;
import com.techreturners.recordshop.model.Genre;
import com.techreturners.recordshop.service.TrRecordShopServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

//When you use @AutoConfigureMockMvc, Spring Boot will automatically configure a MockMvc instance for you.
@AutoConfigureMockMvc
// When you use @SpringBootTest, Spring Boot will automatically start up a Spring Boot application context for you.
// This application context will contain all the beans that your application needs, including controllers, services, and repositories.
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TrRecordShopControllerTests {

    //mockTrRecordShopServiceImpl: A mock implementation of the mockTrRecordShopServiceImpl class.
    // This is used to simulate the behavior of the real service in the test.
    @Mock
    private TrRecordShopServiceImpl mockTrRecordShopServiceImpl;

    //The TrRecordShopController bean that is being tested
    @InjectMocks
    private TrRecordShopController trRecordShopController;

    //The MockMvc instance that is used to make requests to the controller.
    @Autowired
    private MockMvc mockMvcController;

    //An ObjectMapper instance that is used to serialize and deserialize JSON objects.
    private ObjectMapper mapper;

//    @Autowired
//    private WebApplicationContext webApplicationContext;
    //the setup() method configures the MockMvc instance and creates an ObjectMapper instance.
    @BeforeEach
    public void setup(){
        mockMvcController = MockMvcBuilders.standaloneSetup(trRecordShopController).build();
        // this.mockMvcController = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void testGetAllAlbums() throws Exception {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album(1L, "Kind of Blue", "Miles Davis", 1959, Genre.Jazz, 1L));

        when(mockTrRecordShopServiceImpl.getAllAlbums()).thenReturn(albums);

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/album/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testListAllAlbumsInStock() throws Exception {
        final List<Album> albums = new ArrayList<>();
        albums.add(new Album(1L, "Kind of Blue", "Miles Davis", 1959, Genre.Jazz, 3L));
        albums.add(new Album(2L, "Thriller", "Michael Jackson", 1959, Genre.Pop, 1L));
        albums.add(new Album(3L, "Abbey", "Beatles", 1982, Genre.Rock, 0L));

        List<Album> albumsInStock = albums.stream().filter(album -> album.getStock()>0).toList();

        when(mockTrRecordShopServiceImpl.getAlbumsInStock()).thenReturn(albumsInStock);

        ResultActions result = this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/album/instock"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(albumsInStock.size())));


        for( int i=0; i < albumsInStock.size(); i++ ) {
                result.andExpect(MockMvcResultMatchers.jsonPath("$["+i+"].id").value(albumsInStock.get(i).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$["+i+"].title").value(albumsInStock.get(i).getTitle()));
        }

        verify(mockTrRecordShopServiceImpl, times(1)).getAlbumsInStock();
    }

    @ParameterizedTest
    @CsvSource( {"Mi", "Davis", "FAIL"} )
    public void testGetAlbumsByArtist(String artist) throws Exception {

        List<Album> albums = new ArrayList<>();
        albums.add(new Album(1L, "Kind of Blue", "Miles Davis", 1959, Genre.Jazz, 3L));
        albums.add(new Album(2L, "Thriller", "Michael Jackson", 1959, Genre.Pop, 1L));
        albums.add(new Album(3L, "Abbey", "Beatles", 1982, Genre.Rock, 0L));

        List<Album> filteredAlbums = albums.stream().filter(album -> album.getArtist().toLowerCase().contains(artist.toLowerCase())).toList();

        when(mockTrRecordShopServiceImpl.getAlbumsByArtist(artist)).thenReturn(filteredAlbums);

        ResultActions result = this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/album/artist/"+artist))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(filteredAlbums.size())))
                .andExpect(MockMvcResultMatchers.status().isOk());

        for( int i=0; i < filteredAlbums.size(); i++ ) {
            result.andExpect(MockMvcResultMatchers.jsonPath("$["+i+"].id").value(filteredAlbums.get(i).getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$["+i+"].title").value(Matchers.equalToIgnoringCase(filteredAlbums.get(i).getTitle())));
        }



        verify(mockTrRecordShopServiceImpl, times(1)).getAlbumsByArtist(artist);
    }

    @ParameterizedTest
    @ValueSource( ints = {1959, 1982, 3000} )
    public void testGetAlbumsByReleaseYear(int year) throws Exception {

        List<Album> albums = new ArrayList<>();
        albums.add(new Album(1L, "Kind of Blue", "Miles Davis", 1959, Genre.Jazz, 3L));
        albums.add(new Album(2L, "Thriller", "Michael Jackson", 1959, Genre.Pop, 1L));
        albums.add(new Album(3L, "Abbey", "Beatles", 1982, Genre.Rock, 0L));

        List<Album> filteredAlbums = albums.stream().filter(album -> album.getReleaseYear() == year).toList();

        when(mockTrRecordShopServiceImpl.getAlbumsByReleaseYear(year)).thenReturn(filteredAlbums);

        ResultActions result = this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/album/year/"+year))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(filteredAlbums.size())));

        for( int i=0; i < filteredAlbums.size(); i++ ) {
            result.andExpect(MockMvcResultMatchers.jsonPath("$["+i+"].id").value(filteredAlbums.get(i).getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$["+i+"].releaseYear").value(year));
        }

        verify(mockTrRecordShopServiceImpl, times(1)).getAlbumsByReleaseYear(year);
    }
    @ParameterizedTest
    @ValueSource( strings = {"Pop", "Jazz", "Rock"} )
    public void testGetAlbumsByGenre(Genre genre) throws Exception {

        List<Album> albums = new ArrayList<>();
        albums.add(new Album(1L, "Kind of Blue", "Miles Davis", 1959, Genre.Jazz, 3L));
        albums.add(new Album(2L, "Thriller", "Michael Jackson", 1959, Genre.Pop, 1L));
        albums.add(new Album(3L, "Abbey", "Beatles", 1982, Genre.Rock, 0L));

        List<Album> filteredAlbums = albums.stream().filter(album -> album.getGenre().equals(genre)).toList();

        when(mockTrRecordShopServiceImpl.getAlbumsByGenre(genre)).thenReturn(filteredAlbums);

        ResultActions result = this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/album/genre/"+genre))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(filteredAlbums.size())))
                .andExpect(MockMvcResultMatchers.status().isOk());

        for( int i=0; i < filteredAlbums.size(); i++ ) {
            result.andExpect(MockMvcResultMatchers.jsonPath("$["+i+"].id").value(filteredAlbums.get(i).getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$["+i+"].genre").value(Matchers.equalToIgnoringCase(filteredAlbums.get(i).getGenre().toString())));
        }

        verify(mockTrRecordShopServiceImpl, times(1)).getAlbumsByGenre(genre);
    }

    @ParameterizedTest
    @CsvSource( {"e", "b", "Thrill", "FAIL"} )
    public void testGetAlbumsByTitle(String title) throws Exception {

        List<Album> albums = new ArrayList<>();
        albums.add(new Album(1L, "Kind of Blue", "Miles Davis", 1959, Genre.Jazz, 3L));
        albums.add(new Album(2L, "Thriller", "Michael Jackson", 1959, Genre.Pop, 1L));
        albums.add(new Album(3L, "Abbey", "Beatles", 1982, Genre.Rock, 0L));

        List<Album> filteredAlbums = albums.stream().filter(album -> album.getTitle().toLowerCase().contains(title.toLowerCase())).toList();

        when(mockTrRecordShopServiceImpl.getAlbumsByTitle(title)).thenReturn(filteredAlbums);

        ResultActions result = this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/album/title/"+title))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(filteredAlbums.size())))
                .andExpect(MockMvcResultMatchers.status().isOk());

        for( int i=0; i < filteredAlbums.size(); i++ ) {
            result.andExpect(MockMvcResultMatchers.jsonPath("$["+i+"].id").value(filteredAlbums.get(i).getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$["+i+"].title").value(Matchers.equalToIgnoringCase(filteredAlbums.get(i).getTitle())));
        }

        verify(mockTrRecordShopServiceImpl, times(1)).getAlbumsByTitle(title);
    }

    @Test
    public void testPostMappingAddAlbum() throws Exception {

        Album album = new Album(4L, "Album Four", "This is the description for Album Four", 1979, Genre.Rock, 0L);

        when(mockTrRecordShopServiceImpl.insertAlbum(album)).thenReturn(album);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/album/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(album)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockTrRecordShopServiceImpl, times(1)).insertAlbum(album);
    }

    @Test
    public void testPutMappingUpdateAlbum() throws Exception {

        Album album = new Album(4L, "Fabulous Four", "This is the description for the Fabulous Four", 1911, Genre.Rock, 0L);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.put("/api/v1/album/" + album.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(album)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockTrRecordShopServiceImpl, times(1)).updateAlbumById(album.getId(), album);
    }

    @Test
    public void testPutMappingUpdateStock() throws Exception {

        Album album = new Album(4L, "Fabulous Four", "This is the description for the Fabulous Four", 1911, Genre.Rock, 20L);

        this.mockMvcController.perform(
            MockMvcRequestBuilders.put("/api/v1/album/stock/" + album.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(album.getStock())))
                    .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockTrRecordShopServiceImpl, times(1)).updateAlbumStockById(album.getId(), album.getStock());
    }

    @Test
    public void testDeleteAlbumById() throws Exception {

        Long id = 10L;

        this.mockMvcController.perform(MockMvcRequestBuilders.delete("/api/v1/album/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockTrRecordShopServiceImpl, times(1)).deleteAlbumById(id);
    }

    @Test
    public void testDeleteAlbumByIdThrowsNotFound() throws Exception {
        Long id = 10L;

        // Configure the mock to throw AlbumNotFoundException
        doThrow(AlbumNotFoundException.class).when(mockTrRecordShopServiceImpl).deleteAlbumById(id);

        mockMvcController.perform(MockMvcRequestBuilders.delete("/api/v1/album/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Verify that the deleteAlbumById method was called with the specified ID
        verify(mockTrRecordShopServiceImpl, times(1)).deleteAlbumById(id);
    }


}
