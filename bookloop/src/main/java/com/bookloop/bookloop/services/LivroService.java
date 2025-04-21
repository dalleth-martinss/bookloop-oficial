package com.bookloop.bookloop.services;

import com.bookloop.bookloop.controllers.request.LivroRequestDTO;
import com.bookloop.bookloop.controllers.response.LivroResponseDTO;
import com.bookloop.bookloop.entities.Livro;
import com.bookloop.bookloop.enums.condicaoLivroStatus;
import com.bookloop.bookloop.repositories.ILivroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final ILivroRepository livroRepository;

    public LivroService(ILivroRepository livroRepository) {
        this.livroRepository = livroRepository;

    }

    public LivroResponseDTO createLivro(LivroRequestDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setSinopse(dto.getDescricao());
        livro.setPreco(dto.getPreco());
        livro.setCategoria("Geral");
        livro.setCondicaoLivro(condicaoLivroStatus.valueOf(dto.getEstado().toUpperCase()));
        livro.setDisponivelParaVenda(true);
        livro.setDisponivelParaCompra(false);

         Livro saved = livroRepository.save(livro);
        return toDTO(saved);
    }

    public LivroResponseDTO getLivroById(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        return toDTO(livro);
    }

    public List<LivroResponseDTO> getAllLivros() {
        return livroRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public LivroResponseDTO updateLivro(Long id, LivroRequestDTO dto) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setSinopse(dto.getDescricao());
        livro.setPreco(dto.getPreco());
        livro.setCondicaoLivro(condicaoLivroStatus.valueOf(dto.getEstado().toUpperCase()));

        Livro updated = livroRepository.save(livro);
        return toDTO(updated);
    }

    public void deleteLivro(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        livroRepository.delete(livro);
    }

    private LivroResponseDTO toDTO(Livro livro) {
        LivroResponseDTO dto = new LivroResponseDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setAutor(livro.getAutor());
        dto.setDescricao(livro.getSinopse());
        dto.setPreco(livro.getPreco());
        dto.setEstado(livro.getCondicaoLivro().toString());
        dto.setNomeDono(livro.getUsuario() != null ? livro.getUsuario().getNomeCompleto() : null);
        return dto;
    }
}