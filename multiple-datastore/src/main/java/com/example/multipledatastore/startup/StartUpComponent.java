package com.example.multipledatastore.startup;

import com.example.multipledatastore.db1.Book;
import com.example.multipledatastore.db1.BookRepository;
import com.example.multipledatastore.db2.Song;
import com.example.multipledatastore.db2.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StartUpComponent implements CommandLineRunner {

  @Autowired
  BookRepository bookRepository;
  @Autowired
  SongRepository songRepository;

  @Override
  public void run(String... args) throws Exception {

    bookRepository.saveAll(Arrays.asList(Book.builder().name("THE JPURNEY").author("AK.Sharma").build(),
       Book.builder().name("LIFE").author("Krishna").build()));

    songRepository.saveAll(Arrays.asList(Song.builder().name("Na na na na na na ").length(120).build(),
       Song.builder().name("Chamk Challo").length(100).build()));
  }
}
