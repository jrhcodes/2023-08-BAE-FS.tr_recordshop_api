package com.techreturners.recordshop;

import com.techreturners.recordshop.model.Album;
import com.techreturners.recordshop.repository.TrRecordShopRepository;
import com.techreturners.recordshop.service.TrRecordShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class TrRecordShopDatabaseInitializer implements CommandLineRunner {

    private final TrRecordShopRepository trRecordShopRepository;
    private final TrRecordShopServiceImpl trRecordShopServiceImpl;

    @Autowired
    public TrRecordShopDatabaseInitializer(
            TrRecordShopRepository trRecordShopRepository,
            TrRecordShopServiceImpl trRecordShopServiceImpl) {
        this.trRecordShopRepository = trRecordShopRepository;
        this.trRecordShopServiceImpl = trRecordShopServiceImpl;
    }

    @Override
    public void run(String... args) throws Exception {
        populateDatabaseIfEmpty();
    }

    public void populateDatabaseIfEmpty() {
        if (trRecordShopRepository.count() == 0) {
            System.out.println("Album repository is empty. Initialising...");

            try {
                String cwd = System.getProperty("user.dir");
                final String fileName = "./src/main/resources/albumlist.tsv";

                File file = new File(fileName);
                byte[] bytes = new byte[(int) file.length()];

                FileInputStream fis = new FileInputStream(file);
                fis.read(bytes);
                fis.close();

                final String fileContent = new String(bytes, StandardCharsets.UTF_8);

                String[] fileLines = fileContent.split("\n");

                for (String fileLine : fileLines) {
                    String[] csvValues = fileLine.split("\t");
                    int releaseYear = Integer.parseInt(csvValues[1]);
                    String title = csvValues[2];
                    String artist = csvValues[3];
                    String genre = csvValues[4];
                    Long stock = 0L;
                    Album album = new Album(0L, title, artist, releaseYear, genre, stock);
                    trRecordShopServiceImpl.insertAlbum(album);
                }

                System.out.println("... album repository initialised with " + trRecordShopRepository.count() + " records.");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}