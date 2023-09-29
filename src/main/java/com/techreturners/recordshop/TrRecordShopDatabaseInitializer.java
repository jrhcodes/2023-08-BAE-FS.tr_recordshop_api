package com.techreturners.recordshop;

import com.techreturners.recordshop.model.Album;
import com.techreturners.recordshop.repository.TrRecordShopRepository;
import com.techreturners.recordshop.service.TrRecordShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
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

    private String[] readBaseAlbumDataFileToStringArray() throws Exception {
        final String baseAlbumDataFileName = "./src/main/resources/albumlist.tsv";
        File file = new File(baseAlbumDataFileName);
        byte[] bytes = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();

        final String fileContent = new String(bytes, StandardCharsets.UTF_8);
        return fileContent.split("\n");
    }

    public int numberOfRecordsInBaseAlbumData() throws Exception {
        return readBaseAlbumDataFileToStringArray().length;
    }

    @Override
    public void run(String... args) throws Exception {
        populateDatabaseIfEmpty();
    }

    public void populateDatabaseIfEmpty() throws Exception {
        if (trRecordShopRepository.count() == 0) {

            System.out.println("Album repository is empty. Initialising...");
            String[] baseAlbumDataFileLines = readBaseAlbumDataFileToStringArray();

            for (String fileLine : baseAlbumDataFileLines) {
                String[] tabDelimitedValues = fileLine.split("\t");
                int releaseYear = Integer.parseInt(tabDelimitedValues[1]);
                String title = tabDelimitedValues[2];
                String artist = tabDelimitedValues[3];
                String genre = tabDelimitedValues[4];
                Long stock = 0L;
                Album album = new Album(0L, title, artist, releaseYear, genre, stock);
                trRecordShopServiceImpl.insertAlbum(album);
            }

            System.out.println("... album repository initialised with " + trRecordShopRepository.count() + " records.");

        }

    }
}