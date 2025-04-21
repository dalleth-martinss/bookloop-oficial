package com.bookloop.bookloop.services;

import com.bookloop.bookloop.controllers.request.OrdemServicoRequestDTO;
import com.bookloop.bookloop.controllers.response.OrdemServiceResponseDTO;
import com.bookloop.bookloop.entities.Livro;
import com.bookloop.bookloop.entities.OrdemServico;
import com.bookloop.bookloop.entities.Usuario;
import com.bookloop.bookloop.repositories.ILivroRepository;
import com.bookloop.bookloop.repositories.IOrdemServicoRepository;
import com.bookloop.bookloop.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdemServicoService {

    private final IOrdemServicoRepository orderRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ILivroRepository livroRepository;

    @Autowired
    public OrdemServicoService( IOrdemServicoRepository ordemServicoRepository
                              , IUsuarioRepository usuarioRepository
                              , ILivroRepository livroRepository) {
        this.orderRepository = ordemServicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public OrdemServiceResponseDTO createOrder(OrdemServicoRequestDTO dto) {
        // Validar usuário
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                                           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND
                                           , "Usuário não encontrado"));

        // Validar livros
        List<Livro> livros = new ArrayList<>();
        for (Long livroId : dto.getLivroId()) {
            Livro livro = livroRepository.findById(livroId)
                                         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                         "Livro não encontrado com ID: " + livroId));
            livros.add(livro);
        }

        // Criar nova entidade Order
        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setUsuario(usuario);
        ordemServico.setMontanteTotal(dto.getMontanteTotal());
        ordemServico.setStatus("PENDING");
        ordemServico.setCreatedAt(LocalDateTime.now());

        // Adicionar livros ao pedido
        for (Livro livro : livros) {
            ordemServico.addLivro(livro);
        }

        // Salvar no repositório
        OrdemServico savedOrdemServico = orderRepository.save(ordemServico);

        // Converter para DTO de resposta
        return convertToResponseDTO(savedOrdemServico);
    }

    @Transactional(readOnly = true)
    public OrdemServiceResponseDTO getOrderById(Long id) {
        OrdemServico ordemServico = orderRepository.findById(id)
                                                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND
                                                   , "Pedido não encontrado"));

        return convertToResponseDTO(ordemServico);
    }

    @Transactional(readOnly = true)
    public List<OrdemServiceResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                              .stream()
                              .map(this::convertToResponseDTO)
                              .collect(Collectors.toList());
    }

    @Transactional
    public OrdemServiceResponseDTO updateOrder(Long id, OrdemServicoRequestDTO dto) {
        // Buscar pedido existente
        OrdemServico ordemServico = orderRepository.findById(id)
                                                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND
                                                   , "Pedido não encontrado"));

        // Validar usuário
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                                           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND
                                           , "Usuário não encontrado"));

        // Validar livros
        List<Livro> livros = new ArrayList<>();
        for (Long livroId : dto.getLivroId()) { Livro livro = livroRepository
                                              .findById(livroId)
                                              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                              "Livro não encontrado com ID: " + livroId));
            livros.add(livro);
        }

        // Atualizar dados do pedido
        ordemServico.setUsuario(usuario);
        ordemServico.setMontanteTotal(dto.getMontanteTotal());

        // Limpar e adicionar novos livros
        ordemServico.getLivros().clear();
        for (Livro livro : livros) {
            ordemServico.addLivro(livro);
        }

        // Salvar alterações
        OrdemServico updatedOrdemServico = orderRepository.save(ordemServico);

        // Converter para DTO de resposta
        return convertToResponseDTO(updatedOrdemServico);
    }

    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado");
        }

        orderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<OrdemServiceResponseDTO> getOrdersByusuarioId(Long usuarioId) {
        // Validar se o usuário existe
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        return orderRepository.findByusuarioId(usuarioId).stream()
                              .map(this::convertToResponseDTO)
                              .collect(Collectors.toList());
    }

    // Método de auxílio para converter entidade para DTO
    private OrdemServiceResponseDTO convertToResponseDTO(OrdemServico ordemServico) {
        OrdemServiceResponseDTO dto = new OrdemServiceResponseDTO();
        dto.setId(ordemServico.getId());
        dto.setStatus(ordemServico.getStatus());
        dto.setMontanteTotal (ordemServico.getMontanteTotal());
        dto.setCreatedAt(ordemServico.getCreatedAt());
        dto.setUsuarioId(ordemServico.getUsuario().getId());

        // Extrair IDs dos livros
        List<Long> livroIds = ordemServico.getLivros().stream()
                                          .map(Livro::getId)
                                          .collect(Collectors.toList());
        dto.setLivroIds(livroIds);

        return dto;
    }
}