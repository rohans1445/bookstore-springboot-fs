package com.example.bookstorespringbootapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="bookstore_user")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "User name cannot be empty")
    @Size(min = 1, max = 40, message = "User name must be between 4 to 40 characters")
    @Column(name = "user_name")
    private String userName;

    @Column(name = "roles")
    private String roles;

    @NotNull
    @Size(min = 1, message = "Password must be atleast 8 characters")
    @Column(name = "password")
    private String password;

    @NotNull(message = "First name cannot be empty")
    @Size(min = 1, max = 20, message = "First name must be between 1 to 20 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Last name cannot be empty")
    @Size(min = 1, max = 20, message = "Last name must be between 1 to 20 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Email(message = "Please enter a valid email address")
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Review> reviews;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "cart",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> cart;

    public List<Review> getReviews() {
        return reviews == null ? Collections.emptyList() : Collections.unmodifiableList(reviews);
    }
}
