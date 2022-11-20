package com.example.bookstorespringbootapi.utility;

import com.example.bookstorespringbootapi.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class RandomBookAPI {

    @GetMapping("/rnd-book")
    public Book generateBook() throws IOException, InterruptedException {
        RandomDataGenerator random = new RandomDataGenerator();
        return random.generateBook();
    }

}
