package com.bookloop.bookloop.interfaces;

import com.bookloop.bookloop.request.UserRequestDTO;
import com.bookloop.bookloop.response.UserResponseDTO;

import java.util.List;

public interface IUserService {
    UserResponseDTO createUser(UserRequestDTO dto);
    UserResponseDTO getUserById(Long id);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO updateUser(Long id, UserRequestDTO dto);
    void deleteUser(Long id);
}
