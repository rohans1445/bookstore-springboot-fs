package com.example.bookstorespringbootapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties("hibernateLazyInitializer")
@Table(name = "book_detail")
public class BookDetail {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

//    @Column(name = "long_desc")
//    private String longDesc;

    @NotBlank(message = "ISBN cannot be empty")
    @Column(name = "isbn")
    private String isbn;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "language")
    private String language;

//    @Column(name = "page_count")
//    private int pageCount;
//
//    @Column(name = "item_weight")
//    private float itemWeight;
//
//    @Column(name = "tags")
//    private String tags;

}
