package com.example.bookstorespringbootapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private String userImg;
}
