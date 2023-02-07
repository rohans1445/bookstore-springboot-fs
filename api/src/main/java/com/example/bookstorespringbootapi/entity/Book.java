package com.example.bookstorespringbootapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "short_desc", length = 400)
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
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "cart")
    private List<ApplicationUser> cart_user;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "userInventory")
    private List<ApplicationUser> ownedBy;

    @Column(name = "tags")
    private String tags;

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime createdAt;

}