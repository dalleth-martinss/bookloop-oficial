package com.bookloop.bookloop.services;

import com.bookloop.bookloop.controllers.request.UserRequestDTO;
import com.bookloop.bookloop.controllers.request.UserUpdateDTO;
import com.bookloop.bookloop.controllers.response.UserResponseDTO;
import com.bookloop.bookloop.entities.User;
import com.bookloop.bookloop.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO dto) {
        // Verificar se já existe usuário com mesmo email ou CPF
        userRepository.findByEmail(dto.getEmail()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já registrado");
        });

        userRepository.findByCpf(dto.getCpf()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já registrado");
        });

        // Criar nova entidade User
        User user = new User();
        user.setCpf(dto.getCpf());
        user.setFullName(dto.getFullName());
        user.setBirthdate(dto.getBirthdate());
        user.setGender(dto.getGender());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // Considere adicionar criptografia de senha


        // Salvar no repositório
        User savedUser = userRepository.save(user);

        // Converter para DTO de resposta
        return convertToResponseDTO(savedUser);
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return convertToResponseDTO(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Verificar se o email já está em uso por outro usuário
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(id)) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já registrado");
                    }
                });

        // Verificar se o CPF já está em uso por outro usuário
        userRepository.findByCpf(dto.getCpf())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(id)) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já registrado");
                    }
                });

        // Atualizar dados
        user.setCpf(dto.getCpf());
        user.setFullName(dto.getFullName());
        user.setBirthdate(dto.getBirthdate());
        user.setGender(dto.getGender());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());


        // Atualizar senha apenas se fornecida
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(dto.getPassword()); // Considere adicionar criptografia de senha
        }

        // Salvar no repositório
        User updatedUser = userRepository.save(user);

        // Converter para DTO de resposta
        return convertToResponseDTO(updatedUser);
    }
    //****************************** Add a new updateUser method for UserUpdateDTO
    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Verificar se o email já está em uso por outro usuário
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(id)) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já registrado");
                    }
                });

        // Atualizar dados (excluindo cpf)
        user.setFullName(dto.getFullName());
        user.setBirthdate(dto.getBirthdate());
        user.setGender(dto.getGender());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());

        // Atualizar senha apenas se fornecida
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(dto.getPassword());
        }

        // Salvar no repositório
        User updatedUser = userRepository.save(user);
        return convertToResponseDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        userRepository.deleteById(id);
    }

    private UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setFullName(user.getFullName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPhoneNumber(user.getPhoneNumber());

        return responseDTO;
    }
}