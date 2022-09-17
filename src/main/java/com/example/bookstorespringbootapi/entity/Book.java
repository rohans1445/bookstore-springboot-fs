package com.example.bookstorespringbootapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 4, max = 50, message = "Title must be between 4 to 50 characters")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author cannot be empty")
    @Size(max = 45, message = "Author name must be between upto 45 characters")
    @Column(name = "author")
    private String author;

    @Size(max = 512)
    @Column(name = "short_desc")
    private String shortDesc;

    @Column(name = "price")
    private double price;

    @Column(name = "img_path")
    private String imgPath;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_detail_id")
    private BookDetail bookDetail;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<Review> reviews;

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime createdAt;

}