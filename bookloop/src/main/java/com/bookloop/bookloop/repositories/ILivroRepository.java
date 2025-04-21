package com.bookloop.bookloop.repositories;

import com.bookloop.bookloop.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByusuarioId(Long usuarioId);
    List<Livro> findBytituloContainingIgnoreCase(String titulo);
    List<Livro> findByAutorContainingIgnoreCase(String autor);

}
