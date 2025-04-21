package com.bookloop.bookloop.controllers;

import com.bookloop.bookloop.controllers.request.OrdemServicoRequestDTO;
import com.bookloop.bookloop.controllers.response.OrdemServiceResponseDTO;
import com.bookloop.bookloop.services.OrdemServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordemservico")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @PostMapping
    public ResponseEntity<OrdemServiceResponseDTO> create(@RequestBody OrdemServicoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemServicoService.createOrder(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServiceResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ordemServicoService.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrdemServiceResponseDTO>> getAll() {
        return ResponseEntity.ok(ordemServicoService.getAllOrders());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ordemServicoService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}