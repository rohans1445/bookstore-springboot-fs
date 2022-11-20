package com.example.bookstorespringbootapi.utility;

import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.BookDetail;
import com.github.javafaker.Faker;

import java.io.IOException;


public class BookstoreFaker {

    public static Book bookFake() throws IOException, InterruptedException {
        RandomDataGenerator dataGenerator  = new RandomDataGenerator();
        Faker faker = new Faker();
        Book b = new Book();
        BookDetail bd = new BookDetail();
        bd.setIsbn(faker.number().digits(10));
//        bd.setLongDesc(faker.lorem().paragraph(15));
        bd.setPublisher(faker.book().publisher());
//        bd.setTags(faker.book().genre() + "," + faker.book().genre() + "," + faker.book().genre());
        bd.setLanguage("English");
//        bd.setPageCount(faker.number().numberBetween(100,1000));
//        bd.setItemWeight(Float.parseFloat(faker.commerce().price(0.05,2.00)));


        b.setTitle(faker.book().title());
        b.setAuthor(faker.book().author());
        b.setPrice(Float.parseFloat(faker.commerce().price(5.99, 99.99)));
        b.setImgPath(dataGenerator.generateImage());
        b.setShortDesc(faker.lorem().paragraph(4));
        b.setBookDetail(bd);
//        b.setHidden(false);

        return b;
    }

    public static ApplicationUser userFake(){
        Faker faker = new Faker();
        ApplicationUser u = new ApplicationUser();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String email = firstname + "." + lastname + "@gmail.com";
        String username = firstname + "." + lastname;


        u.setUserName(username.toLowerCase());
        u.setFirstName(firstname);
        u.setLastName(lastname);
        u.setEmail(email.toLowerCase());
        u.setRoles("ROLE_USER");
        u.setPassword("1");
//        u.setCredits(100);

        return u;
    }
}
