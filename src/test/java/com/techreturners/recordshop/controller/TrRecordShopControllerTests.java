package com.techreturners.recordshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techreturners.recordshop.model.Album;
import com.techreturners.recordshop.model.Genre;
import com.techreturners.recordshop.service.TrRecordShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
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

    //mockBookManagerServiceImpl: A mock implementation of the mockTrRecordShopServiceImpl class.
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
    public void testGetAllAlbumsReturn200() throws Exception {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album(1L, "Kind of Blue", "Miles Davis", 1959, Genre.Jazz, 1L));

        when(mockTrRecordShopServiceImpl.getAllAlbums()).thenReturn(albums);

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/album/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testListAllAlbumsInStock() throws Exception {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album(1L, "Kind of Blue", "Miles Davis", 1959, Genre.Jazz, 3L));
        albums.add(new Album(2L, "Thriller", "Michael Jackson", 1959, Genre.Pop, 1L));
        albums.add(new Album(3L, "Abbey", "Beatles", 1982, Genre.Rock, 0L));

        when(mockTrRecordShopServiceImpl.getAlbumsInStock()).thenReturn(albums);

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/album/instock"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockTrRecordShopServiceImpl, times(1)).getAlbumsInStock();
    }
}
