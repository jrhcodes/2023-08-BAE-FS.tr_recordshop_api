package com.techreturners.recordshop.application;

import com.techreturners.recordshop.TrRecordShopDatabaseInitializer;
import com.techreturners.recordshop.model.Album;
import com.techreturners.recordshop.repository.TrRecordShopRepository;
import com.techreturners.recordshop.service.TrRecordShopServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
// When you use @SpringBootTest, Spring Boot will automatically start up a Spring Boot application context for you.
// This application context will contain all the beans that your application needs, including controllers, services, and repositories.
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TrRecordShopDatabaseInitializerTest {
    @Mock
    private TrRecordShopRepository trRecordShopRepository;
    @Mock
    private TrRecordShopServiceImpl trRecordShopServiceImpl;
    @InjectMocks
    private TrRecordShopDatabaseInitializer trRecordShopDatabaseInitializer;

    @Test
    public void populateDatabaseIfEmptyTest() {
        when(trRecordShopRepository.count()).thenReturn(0L);
        when(trRecordShopServiceImpl.insertAlbum(Mockito.any(Album.class))).thenAnswer(new Answer<Album>() {
            @Override
            public Album answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArgument(0);
            }
        });
        trRecordShopDatabaseInitializer.populateDatabaseIfEmpty();
        verify(trRecordShopServiceImpl, times(500)).insertAlbum(Mockito.any(Album.class));
    }

    @Test
    public void populateDatabaseIfNotEmptyTest() {
        when(trRecordShopRepository.count()).thenReturn(1L);
        when(trRecordShopServiceImpl.insertAlbum(Mockito.any(Album.class))).thenAnswer(new Answer<Album>() {
            @Override
            public Album answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArgument(0);
            }
        });
        trRecordShopDatabaseInitializer.populateDatabaseIfEmpty();
        verify(trRecordShopServiceImpl, times(0)).insertAlbum(Mockito.any(Album.class));
    }
}
