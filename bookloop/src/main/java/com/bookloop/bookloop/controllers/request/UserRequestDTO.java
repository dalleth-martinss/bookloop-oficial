package com.bookloop.bookloop.controllers.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserRequestDTO {
    private String cpf;
    private String fullName;
    private LocalDateTime birthdate;
    private String gender;
    private String phoneNumber;
    private String email;
    private String password;
    private String role;
}
