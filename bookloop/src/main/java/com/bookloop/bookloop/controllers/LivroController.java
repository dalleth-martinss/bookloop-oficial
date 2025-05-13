package com.bookloop.bookloop.controllers;

import com.bookloop.bookloop.controllers.request.LivroRequestDTO;
import com.bookloop.bookloop.controllers.response.LivroResponseDTO;
import com.bookloop.bookloop.services.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroservice) {
        this.livroService = livroservice;
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> create(@Valid @RequestBody LivroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.createLivro(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.getLivroById(id));
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> getAll() {
        return ResponseEntity.ok(livroService.getAllLivros());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> update(@PathVariable Long id, @Valid @RequestBody LivroRequestDTO dto) {
        return ResponseEntity.ok(livroService.updateLivro(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livroService.deleteLivro(id);
        return ResponseEntity.noContent().build();
    }
}