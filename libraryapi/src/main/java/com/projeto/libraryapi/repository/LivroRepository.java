package com.projeto.libraryapi.repository;

import com.projeto.libraryapi.model.Autor;
import com.projeto.libraryapi.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface  LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

//    PODE USAR DESSA FORMA
//    Page<Livro> findByAutor(Autor autor, Pageable pageable);

    //QUERY METHOD - Método de Consulta
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    boolean existsByAutor(Autor autor);

    Optional<Livro> findByIsbn(String isbn);



}
