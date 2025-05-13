package com.bookloop.bookloop.services;

import com.bookloop.bookloop.controllers.request.UsuarioRequestDTO;
import com.bookloop.bookloop.controllers.request.UsuarioUpdateDTO;
import com.bookloop.bookloop.controllers.response.UsuarioResponseDTO;
import com.bookloop.bookloop.entities.Usuario;
import com.bookloop.bookloop.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponseDTO createUser(UsuarioRequestDTO dto) {
        // Verificar se já existe usuário com mesmo email ou CPF
        usuarioRepository.findByEmail(dto.getEmail()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já registrado");
        });

        usuarioRepository.findByCpf(dto.getCpf()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já registrado");
        });

        // Criar nova entidade User
        Usuario usuario = new Usuario();
        usuario.setCpf(dto.getCpf());
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setGenero(dto.getGenero());
        usuario.setNumeroTelefone(dto.getNumeroTelefone());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha()); // Considere adicionar criptografia de senha


        // Salvar no repositório
        Usuario savedUsuario = usuarioRepository.save(usuario);

        // Converter para DTO de resposta
        return convertToResponseDTO(savedUsuario);
    }

    public UsuarioResponseDTO getUserById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                                           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return convertToResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> getAllUsers() {
        return usuarioRepository.findAll().stream()
                                .map(this::convertToResponseDTO)
                                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO updateUser(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                                           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Verificar se o email já está em uso por outro usuário
        usuarioRepository.findByEmail(dto.getEmail())
                         .ifPresent(existingUser -> {
                            if (!existingUser.getId().equals(id)) {
                                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já registrado");
                            }
                         });

        // Verificar se o CPF já está em uso por outro usuário
        usuarioRepository.findByCpf(dto.getCpf())
                         .ifPresent(existingUser -> {
                            if (!existingUser.getId().equals(id)) {
                                throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já registrado");
                            }
                         });

        // Atualizar dados
        usuario.setCpf(dto.getCpf());
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setGenero(dto.getGenero());
        usuario.setNumeroTelefone(dto.getNumeroTelefone());
        usuario.setEmail(dto.getEmail());


        // Atualizar senha apenas se fornecida
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenha(dto.getSenha()); // Considere adicionar criptografia de senha
        }

        // Salvar no repositório
        Usuario updatedUsuario = usuarioRepository.save(usuario);

        // Converter para DTO de resposta
        return convertToResponseDTO(updatedUsuario);
    }
    //****************************** Add a new updateUser method for UserUpdateDTO
    public UsuarioResponseDTO updateUser(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Verificar se o email já está em uso por outro usuário
        usuarioRepository.findByEmail(dto.getEmail())
                         .ifPresent(existingUser -> {
                            if (!existingUser.getId().equals(id)) {
                                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já registrado");
                            }
                         });

        // Atualizar dados (excluindo cpf)
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setGenero(dto.getGenero());
        usuario.setNumeroTelefone(dto.getNumeroTelefone());
        usuario.setEmail(dto.getEmail());

        // Atualizar senha apenas se fornecida
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenha(dto.getSenha());
        }

        // Salvar no repositório
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return convertToResponseDTO(updatedUsuario);
    }

    public void deleteUser(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO convertToResponseDTO(Usuario usuario) {
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(usuario.getId());
        responseDTO.setNomeCompleto(usuario.getNomeCompleto());
        responseDTO.setEmail(usuario.getEmail());
        responseDTO.setNumeroTelefone(usuario.getNumeroTelefone());

        return responseDTO;
    }
}