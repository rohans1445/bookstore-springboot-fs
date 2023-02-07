package com.example.bookstorespringbootapi.controller;

import com.example.bookstorespringbootapi.dto.ExchangeCreateDTO;
import com.example.bookstorespringbootapi.dto.ExchangeRequestDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.BookDetail;
import com.example.bookstorespringbootapi.entity.ExchangeRequest;
import com.example.bookstorespringbootapi.entity.enums.ExchangeStatus;
import com.example.bookstorespringbootapi.repository.ApplicationUserRepository;
import com.example.bookstorespringbootapi.repository.BookRepository;
import com.example.bookstorespringbootapi.repository.ExchangeRepository;
import com.example.bookstorespringbootapi.utility.RandomDataGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hamcrest.MatcherAssert;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    Book book1;
    Book book2;
    Book book3;
    Book book4;
    ApplicationUser user1;
    ApplicationUser user2;
    ApplicationUser user3;
    ExchangeRequest exchange1;


    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        RandomDataGenerator gen = new RandomDataGenerator();

        book1 = new Book();
        book2 = new Book();
        book3 = new Book();
        book4 = new Book();
        user1 = new ApplicationUser();
        user2 = new ApplicationUser();
        user3 = new ApplicationUser();

        book1 = gen.generateBook();
        book2 = gen.generateBook();
        book3 = gen.generateBook();
        book4 = gen.generateBook();


        user1.setUserName("TestUser1");
        user1.setFirstName("TestUserFirstName1");
        user1.setLastName("TestUserLastName1");
        user1.setEmail("testuser1@mail.com");
        user1.setPassword("pass");
        user1.setRoles("ROLE_USER");

        user2.setUserName("TestUser2");
        user2.setFirstName("TestUserFirstName2");
        user2.setLastName("TestUserLastName2");
        user2.setEmail("testuser2@mail.com");
        user2.setPassword("pass");
        user2.setRoles("ROLE_USER");

        user3.setUserName("TestUser3");
        user3.setFirstName("TestUserFirstName3");
        user3.setLastName("TestUserLastName3");
        user3.setEmail("testuser3@mail.com");
        user3.setPassword("pass");
        user3.setRoles("ROLE_USER");

        exchange1 = new ExchangeRequest();
        exchange1.setExchangeStatus(ExchangeStatus.OPEN);
        exchange1.setExchangeOpener(user1);
        exchange1.setExchangeCloser(null);
        exchange1.setOpenerOwnedBook(book4);
        exchange1.setOpenerExchangeBook(book2);
        exchange1.setCreatedAt(LocalDateTime.now());


        bookRepository.saveAll(List.of(book1,book2,book3, book4));

        userRepository.save(user1);
        userRepository.save(user2);

        user1.setUserInventory(new ArrayList<>(Collections.singletonList(book1)));
        user2.setUserInventory(new ArrayList<>(Collections.singletonList(book2)));

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        exchangeRepository.save(exchange1);
    }

    @AfterEach
    void tearDown() {
        user1.setUserInventory(new ArrayList<>());
        user2.setUserInventory(new ArrayList<>());
        userRepository.save(user1);
        userRepository.save(user2);
        exchangeRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void givenUser_whenCreateValidExchangeRequest_thenStatusCreated() throws Exception {
        String token = getToken(user1.getUserName());
        ExchangeCreateDTO exchangeCreateDTO = new ExchangeCreateDTO();
        exchangeCreateDTO.setOpenerOwnedBookId(book1.getId());
        exchangeCreateDTO.setOpenerExchangeBookId(book2.getId());

        mockMvc.perform(post("/api/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exchangeCreateDTO))
                        .header("Authorization", token))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void givenUserOwnsSourceAndTargetBook_whenCreateExchange_thenStatus400() throws Exception {
        String token = getToken(user1.getUserName());
        ExchangeCreateDTO exchangeCreateDTO = new ExchangeCreateDTO();
        exchangeCreateDTO.setOpenerOwnedBookId(book1.getId());
        exchangeCreateDTO.setOpenerExchangeBookId(book1.getId());

        mockMvc.perform(post("/api/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exchangeCreateDTO))
                        .header("Authorization", token))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void givenUserDoesNotOwnSourceBook_whenCreateExchange_thenStatus400() throws Exception {
        String token = getToken(user1.getUserName());
        ExchangeCreateDTO exchangeCreateDTO = new ExchangeCreateDTO();
        exchangeCreateDTO.setOpenerOwnedBookId(book3.getId());
        exchangeCreateDTO.setOpenerExchangeBookId(book2.getId());

        mockMvc.perform(post("/api/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exchangeCreateDTO))
                        .header("Authorization", token))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void givenUserCreatedExchange_whenCancelExchange_thenStatus200() throws Exception {
        String token = getToken(user1.getUserName());

        mockMvc.perform(get("/api/exchange/{id}/cancel", exchange1.getId())
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void givenUserDidNotCreateExchange_whenCancelExchange_thenStatus400() throws Exception {
        String token = getToken(user2.getUserName());

        mockMvc.perform(get("/api/exchange/{id}/cancel", exchange1.getId())
                        .header("Authorization", token))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void givenUserAcceptValidExchange_thenProcessExchangeSuccessfully() throws Exception {
        String token = getToken(user2.getUserName());

        mockMvc.perform(get("/api/exchange/{id}/process", exchange1.getId())
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void givenUserAcceptExchange_whenCloserDoesNotOwnExchangeBook_thenCannotProcessExchange() throws Exception {
        String token = getToken(user3.getUserName());

        mockMvc.perform(get("/api/exchange/{id}/process", exchange1.getId())
                .header("Authorization", token))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    public static String getToken(String username){
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 259200000);
        return "Bearer " + Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "TESTSECRET")
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .compact();
    }
}