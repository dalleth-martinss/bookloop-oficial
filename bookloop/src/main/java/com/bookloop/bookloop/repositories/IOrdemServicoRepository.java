package com.bookloop.bookloop.repositories;

import com.bookloop.bookloop.entities.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
       List<OrdemServico> findByusuarioId(Long usuarioId);
}