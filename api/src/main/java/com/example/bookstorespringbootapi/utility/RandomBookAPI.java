package com.example.bookstorespringbootapi.utility;

import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.mapper.BookMapper;
import com.example.bookstorespringbootapi.payload.CurrentUserResponse;
import com.example.bookstorespringbootapi.repository.ApplicationUserRepository;
import com.example.bookstorespringbootapi.service.BookService;
import com.example.bookstorespringbootapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.example.bookstorespringbootapi.utility.BookstoreFaker.bookFake;

@RestController
@RequestMapping("/api")
public class RandomBookAPI {

    @Autowired
    BookService bookService;

    @Autowired
    ApplicationUserRepository userRepository;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/rnd-book")
    public Book generateBook() throws IOException, InterruptedException {
        RandomDataGenerator random = new RandomDataGenerator();
        return random.generateBook();
    }

    @GetMapping("/rnd-user")
    public CurrentUserResponse generateUser() throws IOException, InterruptedException {
        RandomDataGenerator random = new RandomDataGenerator();
        ApplicationUser user = random.generateUser();
        user.setCredits(500);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new CurrentUserResponse(random.generateUser());
    }

    @GetMapping("/fake/book")
    public String fakeBook(@RequestParam(value = "count", defaultValue = "1") int count) throws IOException, InterruptedException {
        for(int i = 0; i < count; i++){
            bookService.saveBook(bookMapper.toBookDTO(bookFake()));
            Thread.sleep(1000);
        }
        return "Generated random book";
    }

    @GetMapping("/fake/user")
    public String fakeUser(@RequestParam(value = "count", defaultValue = "1") int count) throws IOException, InterruptedException {
        RandomDataGenerator dataGenerator = new RandomDataGenerator();
        for(int i = 0; i < count; i++){
            userRepository.save(dataGenerator.generateUser());
            Thread.sleep(1000);
        }
        return "Generated random user";
    }

}
